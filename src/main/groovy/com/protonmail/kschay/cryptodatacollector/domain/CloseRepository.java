package com.protonmail.kschay.cryptodatacollector.domain;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CloseRepository extends CrudRepository<Close, Long> {

    Optional<Close> findFirstBySymbolAndDateOrderByInsertTimeDesc(Symbol symbol, LocalDate date);

}

