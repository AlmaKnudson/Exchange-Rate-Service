package com.example.demo.domain;

public class TransferwiseLink {
    private String interactiveLink;

    public TransferwiseLink(String originalCurrency, String destinationCurrency) {
        String baseUrl = "https://transferwise.com/gb/currency-converter/%1$s-to-%2$s-rate";
        this.interactiveLink = String.format(baseUrl, originalCurrency, destinationCurrency);
    }

    public String getInteractiveLink() {
        return interactiveLink;
    }

    public void setInteractiveLink(String interactiveLink) {
        this.interactiveLink = interactiveLink;
    }
}
