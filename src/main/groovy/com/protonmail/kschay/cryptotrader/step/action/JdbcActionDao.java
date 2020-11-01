package com.protonmail.kschay.cryptotrader.step.action;

import com.protonmail.kschay.cryptotrader.domain.action.ActionRepository;
import com.protonmail.kschay.cryptotrader.domain.action.CurrencyAction;
import com.protonmail.kschay.cryptotrader.domain.currency.Currency;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcActionDao implements ActionRepository {

    private static final String INSERT_CURRENCY_ACTION =
            "insert into action " +
            "(currency, value, date) " +
            "values (:currency, :action, :date) ";

    private static final String CURRENCY_ACTION_EXISTS =
            "select count(date) from action " +
            "where date(date) = date(:date) " +
            "and currency = :currency ";

    private static final String UPDATE_CURRENCY_ACTION =
            "update action set currency = :currency, value = :action, " +
            "date = :date, updateTime = current_timestamp " +
            "where date(date) = date(:date) " +
            "and currency = :currency " +
            "order by insertTime desc " +
            "limit 1 ";

    private static final String GET_CURRENCY_ACTION =
            "select currency, value action, date from action " +
            "where currency = :currency " +
            "order by date desc " +
            "limit 1 ";


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcActionDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean insert(CurrencyAction currencyAction) {
        if(exists(currencyAction)) {
            return update(currencyAction);
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyAction.getCurrency().toString());
        mapSqlParameterSource.addValue("action", currencyAction.getAction().toString());
        mapSqlParameterSource.addValue("date", currencyAction.getDate());
        return namedParameterJdbcTemplate.update(INSERT_CURRENCY_ACTION, mapSqlParameterSource) > 0;
    }

    @Override
    public boolean exists(CurrencyAction currencyAction) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyAction.getCurrency().toString());
        mapSqlParameterSource.addValue("date", currencyAction.getDate());
        Integer found = namedParameterJdbcTemplate.queryForObject(CURRENCY_ACTION_EXISTS, mapSqlParameterSource, Integer.class);
        return found != null && found > 0;
    }

    @Override
    public boolean update(CurrencyAction currencyAction) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currencyAction.getCurrency().toString());
        mapSqlParameterSource.addValue("action", currencyAction.getAction().toString());
        mapSqlParameterSource.addValue("date", currencyAction.getDate());
        return namedParameterJdbcTemplate.update(UPDATE_CURRENCY_ACTION, mapSqlParameterSource) > 0;
    }

    @Override
    public CurrencyAction getCurrencyAction(Currency currency) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("currency", currency.toString());
        return namedParameterJdbcTemplate.queryForObject(GET_CURRENCY_ACTION, mapSqlParameterSource, new BeanPropertyRowMapper<>(CurrencyAction.class));
    }
}
