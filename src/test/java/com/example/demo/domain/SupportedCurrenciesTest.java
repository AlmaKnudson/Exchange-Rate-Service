package com.example.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SupportedCurrenciesTest {

    @Test
    void emptySupportedCurrencies(){
        //Arrange
        HashMap currencyMap = new HashMap<>();
        //Act
        SupportedCurrencies supportedCurrencies = new SupportedCurrencies(currencyMap);
        //Assert
        assertThat(supportedCurrencies.getSupportedCurrencies()).isEmpty();
    }

    @Test
    void nonEmptySupportedCurrencies(){
        //Arrange
        HashMap currencyMap = new HashMap<>();
        //Act
        SupportedCurrencies supportedCurrencies = new SupportedCurrencies(currencyMap);
        //Assert
        assertThat(supportedCurrencies.getSupportedCurrencies()).isEmpty();
    }
}
