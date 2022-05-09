package com.cyberpartizan.crypto.cryptoalert.brokers.binance;

import com.cyberpartizan.crypto.cryptoalert.brokers.BrokerApi;
import com.cyberpartizan.crypto.cryptoalert.brokers.BrokerBase;
import com.cyberpartizan.crypto.cryptoalert.models.TickerBase;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.cyberpartizan.crypto.cryptoalert.brokers.binance.BinanceUtils.URL;
import static com.cyberpartizan.crypto.cryptoalert.models.BrokerEnum.BINANCE;

@Component
public class BinanceApi implements BrokerApi {

    protected ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public List<TickerBase> getTickers() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL + "ticker/price")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        List<TickerBase> tickerList = objectMapper.readValue(Objects.requireNonNull(response.body()).string(), new TypeReference<>() {});
        tickerList.forEach(e -> e.setBroker(BINANCE));

        return tickerList;
    }
}
