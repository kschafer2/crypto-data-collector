package com.protonmail.kschay.cryptotrader.step.strength;

import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import com.protonmail.kschay.cryptotrader.domain.strength.CurrencyStrength;
import com.protonmail.kschay.cryptotrader.domain.strength.Strength;
import com.protonmail.kschay.cryptotrader.domain.strength.StrengthRepository;
import com.protonmail.kschay.cryptotrader.util.DateAndTime;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class StrengthDao implements StrengthRepository {

    private static final String INSERT_CURRENCY_STRENGTH =
            "insert into strength " +
            "(currency, value, date) " +
            "values (:currency, :strength, :date) ";

    private static final String CURRENCY_STRENGTH_EXISTS =
            "select count(date) from strength " +
            "where date(date) = date(:date) " +
            "and currency = :currency ";

    private static final String UPDATE_CURRENCY_STRENGTH =
            "update strength set currency = :currency, value = :strength, " +
            "date = :date, updateTime = current_timestamp " +
            "where date(date) = date(:date) " +
            "and currency = :currency " +
            "order by insertTime desc " +
            "limit 1 ";

    private static final String GET_CURRENCY_STRENGTH =
            "select currency, value strength, date from strength " +
            "where currency = :currency " +
            "order by date desc " +
            "limit 1 ";

    private static final String GET_STRENGTH =
            "select value from strength " +
            "where currency = :currency " +
            "order by date desc " +
            "limit 1 ";

    private static final String GET_PREVIOUS_STRENGTH =
            "select value from strength " +
            "where currency = :currency " +
            "and date(date) not like date(:date) " +
            "order by date desc " +
            "limit 1 ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public StrengthDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean insert(CurrencyStrength currencyStrength) {
        if(exists(currencyStrength)) {
            return update(currencyStrength);
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyStrength.getCurrency().toString());
        mapSqlParameterSource.addValue("strength", currencyStrength.getStrength().toString());
        mapSqlParameterSource.addValue("date", currencyStrength.getDate());
        return namedParameterJdbcTemplate.update(INSERT_CURRENCY_STRENGTH, mapSqlParameterSource) > 0;
    }

    @Override
    public boolean exists(CurrencyStrength currencyStrength) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyStrength.getCurrency().toString());
        mapSqlParameterSource.addValue("date", currencyStrength.getDate());
        Integer found = namedParameterJdbcTemplate.queryForObject(CURRENCY_STRENGTH_EXISTS, mapSqlParameterSource, Integer.class);
        return found != null && found > 0;
    }

    @Override
    public boolean update(CurrencyStrength currencyStrength) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyStrength.getCurrency().toString());
        mapSqlParameterSource.addValue("strength", currencyStrength.getStrength().toString());
        mapSqlParameterSource.addValue("date", currencyStrength.getDate());
        return namedParameterJdbcTemplate.update(UPDATE_CURRENCY_STRENGTH, mapSqlParameterSource) > 0;
    }

    @Override
    public CurrencyStrength getCurrencyStrength(Currency currency) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currency.toString());
        return namedParameterJdbcTemplate.queryForObject(
                GET_CURRENCY_STRENGTH,
                mapSqlParameterSource,
                new BeanPropertyRowMapper<>(CurrencyStrength.class));
    }

    @Override
    public Strength getStrengthValue(Currency currency) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currency.toString());
        return namedParameterJdbcTemplate.queryForObject(GET_STRENGTH, mapSqlParameterSource, Strength.class);
    }

    @Override
    public Strength getPreviousStrengthValue(Currency currency) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currency.toString());
        mapSqlParameterSource.addValue("date", DateAndTime.localCloseDate());
        return namedParameterJdbcTemplate.queryForObject(GET_PREVIOUS_STRENGTH, mapSqlParameterSource, Strength.class);
    }
}
