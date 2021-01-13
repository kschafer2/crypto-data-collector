package com.protonmail.kschay.cryptodatacollector.job;

import com.protonmail.kschay.cryptodatacollector.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.protonmail.kschay.cryptodatacollector.job.DateTimeService.localCloseDate;
import static com.protonmail.kschay.cryptodatacollector.job.DateTimeService.yesterdayCloseDate;
import static java.time.LocalDateTime.now;

@Service
public class DataService {

    private final CloseRepository closeRepository;
    private final EmaRepository emaRepository;
    private final JobProperties jobProperties;

    public DataService(CloseRepository closeRepository,
                       EmaRepository emaRepository,
                       JobProperties jobProperties) {
        this.closeRepository = closeRepository;
        this.emaRepository = emaRepository;
        this.jobProperties = jobProperties;
    }

    public Optional<Close> getLatestClose(Symbol symbol) {
        return closeRepository.findFirstBySymbolAndDateOrderByInsertTimeDesc(symbol, localCloseDate());
    }

    public Optional<Ema> getLatestEma(Symbol symbol) {
        return emaRepository.findFirstBySymbolAndDateOrderByInsertTimeDesc(symbol, localCloseDate());
    }

    public Ema getPreviousEma(Symbol symbol) {
        return emaRepository.findFirstBySymbolAndDateOrderByInsertTimeDesc(symbol, yesterdayCloseDate())
                .orElseGet(() -> getPreviousEmaFromProperties(symbol));
    }

    private Ema getPreviousEmaFromProperties(Symbol symbol) {
        Ema foundEma = jobProperties.getSymbolEmas().get(symbol);

        if(foundEma != null && foundEma.getDate().equals(yesterdayCloseDate())) {
            Ema ema = jobProperties.getSymbolEmas().get(symbol);
            ema.setSymbol(symbol);
            ema.setInsertTime(now());
            ema.setPeriod(ema.getSymbol().getPeriod());
            return ema;
        }
        throw new RuntimeException("Failed to retrieve previous ema value for " + symbol);
    }
}
