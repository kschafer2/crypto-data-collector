package com.protonmail.kschay.cryptodatacollector.step.ema;

import com.protonmail.kschay.cryptodatacollector.domain.Ema;
import com.protonmail.kschay.cryptodatacollector.domain.EmaRepository;
import com.protonmail.kschay.cryptodatacollector.domain.Logging;
import com.protonmail.kschay.cryptodatacollector.domain.Symbol;
import com.protonmail.kschay.cryptodatacollector.job.JobProperties;
import com.protonmail.kschay.cryptodatacollector.util.DateAndTime;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class EmaDao extends Logging implements EmaRepository {

    private static final String EMA_VALUE =
            "select value from ema " +
            "where symbol like :symbol " +
            "order by date desc " +
            "limit 1 ";

    private static final String PREVIOUS_EMA_VALUE =
            "select value from ema " +
            "where symbol like :symbol " +
            "and date(date) = date(:date) " +
            "order by date desc " +
            "limit 1 ";

    public static final String EMA_DATE_EXISTS =
            "select count(date) from ema " +
            "where date(date) = date(:date) " +
            "and symbol like :symbol ";

    private static final String INSERT_EMA =
            "insert into ema " +
            "(symbol, period, value, date) " +
            "values (:symbol, :period, :value, :date) ";

    private static final String UPDATE_EMA =
            "update ema set symbol = :symbol, period = :period, " +
            "value = :value, date = :date, updateTime = current_timestamp " +
            "where date(date) = date(:date) " +
            "and symbol like :symbol " +
            "order by insertTime desc " +
            "limit 1 ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JobProperties jobProperties;

    public EmaDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                  JobProperties jobProperties) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jobProperties = jobProperties;
    }

    @Override
    public Double getEmaValue(final Symbol symbol) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        return namedParameterJdbcTemplate.queryForObject(EMA_VALUE, mapSqlParameterSource, Double.class);
    }

    @Override
    public Double getPreviousEmaValue(final Symbol symbol) {
        Timestamp yesterday = DateAndTime.yesterdayCloseDate();
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        mapSqlParameterSource.addValue("date", yesterday);

        try {
            return namedParameterJdbcTemplate.queryForObject(PREVIOUS_EMA_VALUE, mapSqlParameterSource, Double.class);
        }
        catch(EmptyResultDataAccessException e) {
            log.info("Couldn't find yesterday's EMA in the database...");
            if(jobProperties.getSymbolEmas().containsKey(symbol) &&
                    jobProperties.getSymbolEmas().get(symbol).getDate()
                            .equals(mapSqlParameterSource.getValue("date"))) {
                Double emaValue = jobProperties.getSymbolEmas().get(symbol).getValue();
                log.info("Found yesterday's EMA in properties: " + symbol + ", " + emaValue);
                return emaValue;
            }
            throw new RuntimeException("Failed to retrieve yesterday's ema value", e);
        }
    }

    @Override
    public boolean emaExists(final Symbol symbol, final Timestamp date) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        mapSqlParameterSource.addValue("date", date.toString());
        Integer found = namedParameterJdbcTemplate.queryForObject(EMA_DATE_EXISTS, mapSqlParameterSource, Integer.class);
        return found != null && found > 0;
    }

    @Override
    public boolean insert(final Ema ema) {
        if(emaExists(ema.getSymbol(), ema.getDate())) {
            return update(ema);
        }
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", ema.getSymbol().toString());
        mapSqlParameterSource.addValue("period", ema.getPeriod());
        mapSqlParameterSource.addValue("value", ema.getValue());
        mapSqlParameterSource.addValue("date", ema.getDate());
        int updatedRows = namedParameterJdbcTemplate.update(INSERT_EMA, mapSqlParameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean update(final Ema ema) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", ema.getSymbol().toString());
        mapSqlParameterSource.addValue("period", ema.getPeriod());
        mapSqlParameterSource.addValue("value", ema.getValue());
        mapSqlParameterSource.addValue("date", ema.getDate());
        int updatedRows = namedParameterJdbcTemplate.update(UPDATE_EMA, mapSqlParameterSource);
        return updatedRows > 0;
    }
}
