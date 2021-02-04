package com.example.demo.domain;

public class CurrencyAccess {
    String currency;
    double requestedCount;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getRequestedCount() {
        return requestedCount;
    }

    public void setRequestedCount(double requestedCount) {
        this.requestedCount = requestedCount;
    }
}
