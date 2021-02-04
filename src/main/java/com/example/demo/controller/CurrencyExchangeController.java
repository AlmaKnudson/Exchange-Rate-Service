package com.example.demo.controller;

import com.example.demo.domain.CurrencyAmount;
import com.example.demo.domain.SupportedCurrencies;
import com.example.demo.domain.TransferwiseLink;
import com.example.demo.service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController()
@RequestMapping(value = "/api")
public class CurrencyExchangeController {

    @Autowired
    CurrencyExchangeService currencyExchangeService;

    @GetMapping(value = "/exchange-rate/{originalCurrency}/{destinationCurrency}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public double getEuroCurrencyPairs(@PathVariable("originalCurrency") String originalCurrency, @PathVariable("destinationCurrency") String destinationCurrency) throws IOException {

        return currencyExchangeService.getCurrencyPairs(originalCurrency, destinationCurrency);
    }

    @PostMapping(
            value = "/convert/{originalCurrency}/{destinationCurrency}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ResponseBody
    public double calculateCurrencyConversion(
            @PathVariable("originalCurrency") String originalCurrency,
            @PathVariable("destinationCurrency") String destinationCurrency,
            @RequestBody CurrencyAmount currencyAmount) {
        return currencyExchangeService.calculateCurrencyConversion(originalCurrency, destinationCurrency, currencyAmount.getAmount());
    }

    @GetMapping(value = "/supported-currencies")
    @ResponseBody
    public SupportedCurrencies getSupportedCurrencies() {
        return new SupportedCurrencies(currencyExchangeService.getSupportedCurrencies());
    }

    @GetMapping(value = "/interactive-link/{originalCurrency}/{destinationCurrency}",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public TransferwiseLink getTransferwiseUrl(@PathVariable("originalCurrency") String originalCurrency,
                                               @PathVariable("destinationCurrency") String destinationCurrency){
        return new TransferwiseLink(originalCurrency, destinationCurrency);
    }
}
