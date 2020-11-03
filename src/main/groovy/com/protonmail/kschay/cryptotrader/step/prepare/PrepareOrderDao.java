package com.protonmail.kschay.cryptotrader.step.prepare;

import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrder;
import com.protonmail.kschay.cryptotrader.domain.prepare.PrepareOrderRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrepareOrderDao implements PrepareOrderRepository {

    private static final String INSERT_PREPARE_ORDER =
            "insert into prepare " +
            "(symbol, side, date) " +
            "values (:symbol, :side, :date) ";

    private static final String PREPARE_ORDER_EXISTS =
            "select count(date) from prepare " +
            "where date(date) = date(:date) " +
            "and symbol = :symbol ";

    private static final String UPDATE_PREPARE_ORDER =
            "update prepare set symbol = :symbol, side = :side, " +
            "date = :date, updateTime = current_timestamp " +
            "where date(date) = date(:date) " +
            "and symbol = :symbol " +
            "order by insertTime desc " +
            "limit 1 ";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PrepareOrderDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public boolean insert(PrepareOrder prepareOrder) {
        if(prepareOrderExists(prepareOrder)) {
            return updatePrepareOrder(prepareOrder);
        }
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", prepareOrder.getSymbol().toString());
        mapSqlParameterSource.addValue("side", prepareOrder.getSide().toString());
        mapSqlParameterSource.addValue("date", prepareOrder.getDate());
        return namedParameterJdbcTemplate.update(INSERT_PREPARE_ORDER, mapSqlParameterSource) > 0;
    }

    @Override
    public List<PrepareOrder> getPrepareOrders() {
        return null;
    }

    private boolean updatePrepareOrder(PrepareOrder prepareOrder) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", prepareOrder.getSymbol().toString());
        mapSqlParameterSource.addValue("side", prepareOrder.getSide().toString());
        mapSqlParameterSource.addValue("date", prepareOrder.getDate());
        return namedParameterJdbcTemplate.update(UPDATE_PREPARE_ORDER, mapSqlParameterSource) > 0;
    }

    private boolean prepareOrderExists(PrepareOrder prepareOrder) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("symbol", prepareOrder.getSymbol().toString());
        mapSqlParameterSource.addValue("side", prepareOrder.getDate());
        mapSqlParameterSource.addValue("date", prepareOrder.getDate());
        Integer found = namedParameterJdbcTemplate.queryForObject(PREPARE_ORDER_EXISTS, mapSqlParameterSource, Integer.class);
        return found != null && found > 0;
    }
}
