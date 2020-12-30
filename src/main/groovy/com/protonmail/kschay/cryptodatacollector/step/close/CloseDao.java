package com.protonmail.kschay.cryptodatacollector.step.close;

import com.protonmail.kschay.cryptodatacollector.domain.Close;
import com.protonmail.kschay.cryptodatacollector.domain.CloseRepository;
import com.protonmail.kschay.cryptodatacollector.domain.Symbol;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class CloseDao implements CloseRepository {

    private static final String INSERT_CLOSE =
            "insert into close " +
            "(symbol, price, hour, date) " +
            "values (:symbol, :price, :hour, :date) ";

    public static final String CLOSE_EXISTS =
            "select count(date) from close " +
            "where date(date) = date(:date) " +
            "and symbol like :symbol ";

    private static final String UPDATE_CLOSE =
            "update close set symbol = :symbol, price = :price, hour = :hour, " +
            "date = :date, updateTime = current_timestamp " +
            "where date(date) = date(:date) " +
            "and symbol like :symbol " +
            "order by insertTime desc " +
            "limit 1 ";

    private static final String GET_CLOSE =
            "select symbol, price, hour, date from close " +
            "where symbol like :symbol " +
            "order by date desc " +
            "limit 1 ";

    private static final String GET_PRICE =
            "select price " +
            "from close " +
            "where symbol like :symbol " +
            "order by date desc " +
            "limit 1 ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public CloseDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean insert(final Close close) {
        if(closeExists(close.getSymbol(), close.getDate())) {
            return update(close);
        }
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", close.getSymbol().toString());
        mapSqlParameterSource.addValue("price", close.getPrice());
        mapSqlParameterSource.addValue("hour", close.getHour());
        mapSqlParameterSource.addValue("date", close.getDate());
        int updatedRows = namedParameterJdbcTemplate.update(INSERT_CLOSE, mapSqlParameterSource);
        return updatedRows > 0;
    }

    @Override
    public boolean closeExists(final Symbol symbol, final Timestamp date) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        mapSqlParameterSource.addValue("date", date);
        Integer found = namedParameterJdbcTemplate.queryForObject(CLOSE_EXISTS, mapSqlParameterSource, Integer.class);
        return found != null && found > 0;
    }

    @Override
    public boolean update(final Close close) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", close.getSymbol().toString());
        mapSqlParameterSource.addValue("price", close.getPrice());
        mapSqlParameterSource.addValue("hour", close.getHour());
        mapSqlParameterSource.addValue("date", close.getDate());
        int updatedRows = namedParameterJdbcTemplate.update(UPDATE_CLOSE, mapSqlParameterSource);
        return updatedRows > 0;
    }

    @Override
    public Close getClose(final Symbol symbol) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        return namedParameterJdbcTemplate.queryForObject(GET_CLOSE, mapSqlParameterSource, new BeanPropertyRowMapper<>(Close.class));
    }

    @Override
    public Double getClosePrice(final Symbol symbol) {
        final MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", symbol.toString());
        return namedParameterJdbcTemplate.queryForObject(GET_PRICE, mapSqlParameterSource, Double.class);
    }
}
