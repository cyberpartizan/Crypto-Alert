package com.cyberpartizan.crypto.cryptoalert.brokers;

import com.cyberpartizan.crypto.cryptoalert.models.TickerBase;

import java.io.IOException;
import java.util.List;

public interface BrokerApi {

    List<TickerBase> getTickers() throws IOException;
}
