package com.protonmail.kschay.cryptotrader.domain.execute

interface ExecuteService {
    ExecuteOrder prepareToExecute(ExecuteOrder executeOrder);
}