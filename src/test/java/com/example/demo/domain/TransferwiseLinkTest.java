package com.example.demo.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransferwiseLinkTest {

    /**
     * I want to retrieve a link to a public website showing an interactive chart for a given
     * currency pair.
     */
    @Test
    void retrieveCurrencyPairLink() {
        //Arrange
        String expectedLink = "https://transferwise.com/gb/currency-converter/USD-to-EUR-rate";
        //Act
        TransferwiseLink transferwiseLink = new TransferwiseLink("USD", "EUR");
        //Assert
        assertThat(transferwiseLink.getInteractiveLink()).isEqualTo(expectedLink);
    }
}
