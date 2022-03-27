package com.example.androidtraining1.util;

public interface Currency {
    double exchangeRateFromUSD();
    double exchangeTo(double amount, Currency to);
    String getCurrencyCode();
}
