package com.example.androidtraining1.util;

import com.example.androidtraining1.util.Currency;

import java.util.ArrayList;
import java.util.List;

public class CurrencyUtil {

    public Currency cUSD = new Currency() {

        @Override
        public double exchangeRateFromUSD() {
            return getExchangeRateFromUSD(this);
        }

        @Override
        public double exchangeTo(double amount, Currency to) {
            return exchange(amount, this, to);
        }

        @Override
        public String getCurrencyCode() {
            return "USD";
        }
    };

    public Currency cTWD = new Currency() {

        @Override
        public double exchangeRateFromUSD() {
            return getExchangeRateFromUSD(this);
        }

        @Override
        public double exchangeTo(double amount, Currency to) {
            return exchange(amount, this, to);
        }

        @Override
        public String getCurrencyCode() {
            return "TWD";
        }
    };

    public Currency cJPY = new Currency() {

        @Override
        public double exchangeRateFromUSD() {
            return getExchangeRateFromUSD(this);
        }

        @Override
        public double exchangeTo(double amount, Currency to) {
            return exchange(amount, this, to);
        }

        @Override
        public String getCurrencyCode() {
            return "JPY";
        }
    };

    public List<Currency> getCurrencyList(){
        List<Currency> list = new ArrayList<>();
        list.add(cUSD);
        list.add(cTWD);
        list.add(cJPY);
        return list;
    }

    private double exchange(double amount, Currency from, Currency to){
        return amount * getExchangeRate(from, to);
    }

    private double getExchangeRate(Currency from, Currency to){
        double fromRate = getExchangeRateFromUSD(from);
        double toRate = getExchangeRateFromUSD(to);
        return toRate / fromRate;
    }

    private double getExchangeRateFromUSD(Currency to){
        double rate = 1.0;
        switch (to.getCurrencyCode()){
            case "USD":
                rate = 1.0;
                break;
            case "JPY":
                rate = 122.0;
                break;
            case "TWD":
                rate = 28.5;
                break;
            default:
                new Exception("not supported currency");
        }
        return rate;
    }
}
