package com.example.demo.service;

import com.example.demo.xml.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CurrencyExchangeService {

    private final Map<String, AtomicLong> currencyRequestMap = new HashMap<>();

    @Autowired
    Parser parser;

    public double getCurrencyPairs(String originalCurrency, String destinationCurrency) {

        updateCurrencyRequestMap(originalCurrency);
        updateCurrencyRequestMap(destinationCurrency);
        //Question do we update EUR when destination currency is not EUR?
        return getCurrencyConversionRate(originalCurrency, destinationCurrency);
    }

    public double calculateCurrencyConversion(String originalCurrency, String destinationCurrency, double amount) {
        double conversionRate = getCurrencyConversionRate(originalCurrency, destinationCurrency);
        return conversionRate * amount;
    }

    public Map<String, AtomicLong> getSupportedCurrencies() {
        Map<String, Double> euroCurrencyMap = parser.getEuroCurrencyMap();
        euroCurrencyMap.keySet().forEach(currency -> {
            if (!currencyRequestMap.containsKey(currency)) {
                currencyRequestMap.put(currency, new AtomicLong());
            }
        });
        return currencyRequestMap;
    }

    private void updateCurrencyRequestMap(String originalCurrency) {
        if (currencyRequestMap.containsKey(originalCurrency)) {
            currencyRequestMap.get(originalCurrency).getAndIncrement();
        } else {
            currencyRequestMap.put(originalCurrency, new AtomicLong(1));
        }
    }

    // EUR/EUR           USD/EUR            EUR/USD     JPY/USD   USD/JPR
    //
    private double getCurrencyConversionRate(String originalCurrency, String destinationCurrency) {
        Map<String, Double> euroCurrencyMap = parser.getEuroCurrencyMap();
        if(originalCurrency.equals(destinationCurrency)) {
            return 1.0;
        }
        if (originalCurrency.equals("EUR")) {
            return euroCurrencyMap.get(destinationCurrency);
        } else if (destinationCurrency.equals("EUR")) {
            return 1.0 / euroCurrencyMap.get(originalCurrency);
        } else {
            double originalCurrencyToEuroConversion = 1.0 / euroCurrencyMap.get(originalCurrency);
            double destinationCurrencyToEuroConversion = 1.0 / euroCurrencyMap.get(destinationCurrency);
            return originalCurrencyToEuroConversion / destinationCurrencyToEuroConversion;
        }
    }
}
