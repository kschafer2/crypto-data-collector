package com.protonmail.kschay.cryptotrader.domain.balance

interface BalanceRepository {
     boolean insertBalance(Balance balance)

     boolean updateBalance(Balance balance)
}
