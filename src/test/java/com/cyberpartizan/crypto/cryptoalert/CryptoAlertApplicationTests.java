package com.cyberpartizan.crypto.cryptoalert;

import com.cyberpartizan.crypto.cryptoalert.brokers.bybit.BybitApi;
import com.cyberpartizan.crypto.cryptoalert.brokers.poloniex.Poloniex;
import com.cyberpartizan.crypto.cryptoalert.models.TickerBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
class CryptoAlertApplicationTests {

    @Autowired
    Poloniex binanceApi;

    @Test
    void contextLoads() throws IOException {
        List<TickerBase> tickers = binanceApi.getTickers();
        System.out.println();
    }

}
