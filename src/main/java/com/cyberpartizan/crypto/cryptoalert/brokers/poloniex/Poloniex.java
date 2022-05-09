package com.cyberpartizan.crypto.cryptoalert.brokers.poloniex;

import com.cyberpartizan.crypto.cryptoalert.brokers.BrokerApi;
import com.cyberpartizan.crypto.cryptoalert.models.TickerBase;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static com.cyberpartizan.crypto.cryptoalert.brokers.poloniex.PoloniexUtils.URL;
import static com.cyberpartizan.crypto.cryptoalert.models.BrokerEnum.POLONIX;

@Component
public class Poloniex implements BrokerApi {
    protected ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Override
    public List<TickerBase> getTickers() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL + "?command=returnTicker")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        Iterator<String> fieldNames = objectMapper.readTree(Objects.requireNonNull(response.body()).string()).fieldNames();
        List<TickerBase> tickerList = new ArrayList<>();
        fieldNames.forEachRemaining(e-> tickerList.add(new TickerBase(e,POLONIX)));

        return tickerList;
    }
}
