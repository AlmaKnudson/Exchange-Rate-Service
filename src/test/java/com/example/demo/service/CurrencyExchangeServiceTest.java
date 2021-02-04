package com.example.demo.service;

import com.example.demo.xml.Parser;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CurrencyExchangeServiceTest {

    @Autowired
    CurrencyExchangeService service;

    @Mock
    Parser mockParser;

    /**
     * I want to retrieve the ECB reference rate for a currency pair, e.g. USD/EUR or
     * HUF/EUR.
     */
    @Test
    void retrieveEurToUsd() {
        //Arrange
        service.parser = mockParser;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double retrievedRate = service.getCurrencyPairs("EUR", "USD");
        //Assert
        assertThat(retrievedRate).isEqualTo(actualRate);
    }

    @Test
    void retrieveUsdToEur() {
        //Arrange
        service.parser = mockParser;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double retrievedRate = service.getCurrencyPairs("USD", "EUR");
        //Assert
        assertThat(retrievedRate).isEqualTo(1.0/actualRate);
    }

    /**
     * I want to retrieve an exchange rate for other pairs, e.g. HUF/USD.
     */
    @Test
    void retrieveArbitraryCurrencyPairs() {
        //Arrange
        service.parser = mockParser;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double retrievedRate = service.getCurrencyPairs("USD", "JPY");
        //Assert
        assertThat(retrievedRate).isEqualTo(1.0);
    }

    @Test
    void retrieveArbitraryCurrencyPairs_inverted() {
        //Arrange
        service.parser = mockParser;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double retrievedRate = service.getCurrencyPairs("JPY", "USD");
        //Assert
        assertThat(retrievedRate).isEqualTo(1.0);
    }

    /**
     * I want to retrieve a list of supported currencies and see how many times they were
     * requested.
     */
    @Test
    void retrieveSupportedCurrencies() {
        //Arrange
        service.parser = mockParser;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        Map<String, AtomicLong> currencyRequestMap = service.getSupportedCurrencies();
        //Assert
        assertThat(currencyRequestMap.keySet()).containsAll(mockMap.keySet());
        assertThat(currencyRequestMap.keySet().size()).isEqualTo(mockMap.keySet().size());
    }

    /**
     * I want to convert an amount in a given currency to another, e.g. 15 EUR = ??? GBP
     */
    @Test
    void convertCurrency() {
        //Arrange
        service.parser = mockParser;
        double amount = 100;
        double actualRate = 1.201212;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double convertedAmount = service.calculateCurrencyConversion("JPY", "USD", amount);
        //Assert
        assertThat(convertedAmount).isEqualTo(amount);
    }

    @Test
    void convertJpyToEur() {
        //Arrange
        service.parser = mockParser;
        double amount = 100;
        double actualRate = 1.201;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double convertedAmount = service.calculateCurrencyConversion("JPY", "EUR", amount);
        //Assert
        assertThat(convertedAmount).isEqualTo(amount/actualRate);
    }

    @Test
    void convertEurToJpy() {
        //Arrange
        service.parser = mockParser;
        double amount = 100;
        double actualRate = 1.201;
        Map<String, Double> mockMap = new HashMap<>();
        mockMap.put("USD", actualRate);
        mockMap.put("JPY", actualRate);
        Mockito.when(mockParser.getEuroCurrencyMap()).thenReturn(mockMap);
        //Act
        double convertedAmount = service.calculateCurrencyConversion("EUR", "JPY", amount);
        //Assert
        assertThat(convertedAmount).isEqualTo(amount*actualRate);
    }

}
