package com.example.demo.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class SupportedCurrencies {

    List<CurrencyAccess> supportedCurrencies;

    public SupportedCurrencies(Map<String, AtomicLong> supportedCurrencies){
        this.supportedCurrencies = new ArrayList<>();
        supportedCurrencies.keySet().forEach(supportedCurrency->{
            CurrencyAccess currencyAccess = new CurrencyAccess();
            currencyAccess.setCurrency(supportedCurrency);
            currencyAccess.setRequestedCount(supportedCurrencies.get(supportedCurrency).get());
            this.supportedCurrencies.add(currencyAccess);
        });
    }

    public List<CurrencyAccess> getSupportedCurrencies() {
        return supportedCurrencies;
    }

}
