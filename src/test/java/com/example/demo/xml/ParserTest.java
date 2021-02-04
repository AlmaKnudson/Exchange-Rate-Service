package com.example.demo.xml;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ParserTest {

    @Autowired
    private Parser parser;

    @Test
    void loadsXmlFile() {
        //Arrange
        //Data is available online
        //Act
        Map<String, Double> euroCurrencyMap = parser.getEuroCurrencyMap();
        //Assert
        assertThat(euroCurrencyMap.keySet()).isNotEmpty();
    }
}
