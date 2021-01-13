package com.protonmail.kschay.cryptodatacollector.domain;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface EmaRepository extends CrudRepository<Ema, Long> {

    Optional<Ema> findFirstBySymbolAndDateOrderByInsertTimeDesc(Symbol symbol, LocalDate date);

}
