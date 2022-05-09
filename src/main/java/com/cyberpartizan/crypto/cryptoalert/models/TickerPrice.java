package com.cyberpartizan.crypto.cryptoalert.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TickerPrice extends TickerBase{
    private String price;
}
