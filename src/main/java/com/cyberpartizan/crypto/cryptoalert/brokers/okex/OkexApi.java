package com.cyberpartizan.crypto.cryptoalert.brokers.okex;

import com.cyberpartizan.crypto.cryptoalert.brokers.BrokerApi;
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

import static com.cyberpartizan.crypto.cryptoalert.brokers.okex.OkexUtils.URL;
import static com.cyberpartizan.crypto.cryptoalert.models.BrokerEnum.OKEX;

@Component
public class OkexApi implements BrokerApi {

    protected ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Override
    public List<TickerBase> getTickers() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL + "public/instruments?instType=SPOT")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        String data = objectMapper.readTree(Objects.requireNonNull(response.body()).string()).get("data").toString();
        List<TickerBase> tickerList = objectMapper.readValue(data, new TypeReference<>() {});
        tickerList.forEach(e -> e.setBroker(OKEX));

        return tickerList;
    }
}
