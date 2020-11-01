package com.protonmail.kschay.cryptotrader.step.ema;

import com.protonmail.kschay.cryptotrader.domain.ema.Ema;
import com.protonmail.kschay.cryptotrader.domain.ema.EmaRepository;
import com.protonmail.kschay.cryptotrader.domain.symbol.Symbol;
import com.protonmail.kschay.cryptotrader.util.DateAndTime;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class EmaDao implements EmaRepository {

    private static final String EMA_VALUE =
            "select value from ema " +
            "where symbol like :symbol " +
            "order by date desc " +
            "limit 1 ";

    private static final String PREVIOUS_EMA_VALUE =
            "select value from ema " +
            "where symbol like :symbol " +
            "and date(date) not like date(:date) " +
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

    public EmaDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Double getEmaValue(final Symbol symbol) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        return namedParameterJdbcTemplate.queryForObject(EMA_VALUE, mapSqlParameterSource, Double.class);
    }

    @Override
    public Double getPreviousEmaValue(final Symbol symbol) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        mapSqlParameterSource.addValue("date", DateAndTime.localCloseDate());
        return namedParameterJdbcTemplate.queryForObject(PREVIOUS_EMA_VALUE, mapSqlParameterSource, Double.class);
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
