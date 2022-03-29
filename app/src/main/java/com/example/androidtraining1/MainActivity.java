package com.example.androidtraining1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import com.example.androidtraining1.util.Currency;
import com.example.androidtraining1.util.CurrencyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Context mContext;
    CurrencyUtil mCurrencyUtil;
    Spinner currency_type;
    EditText currency_value;
    Button btn_exchange;
    ListView list_exchange;
    ExchangeAdapter list_exchange_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mCurrencyUtil = new CurrencyUtil();
        initView();
        initListener();
        initExchangeList();
    }

    private void initView(){
        //get the view component after Activity created
        currency_type = findViewById(R.id.currency_type);
        currency_value = findViewById(R.id.currency_value);
        list_exchange = findViewById(R.id.list_exchange);
        btn_exchange = findViewById(R.id.btn_exchange);
    }

    private void initListener(){
        //update exchange result when exchange button be clicked
        btn_exchange.setOnClickListener(v->{
            Currency currency = mCurrencyUtil.cUSD;
            switch (currency_type.getSelectedItemPosition()){
                case 0:
                    currency = mCurrencyUtil.cUSD;
                    break;
                case 1:
                    currency = mCurrencyUtil.cTWD;
                    break;
                case 2:
                    currency = mCurrencyUtil.cJPY;
                    break;
            }
            double amount = 0.0;
            try {
                amount = Integer.valueOf(currency_value.getText().toString());
            } catch (Exception e) {
                currency_value.setText("");
            }
            list_exchange_adapter.updateBaseCurrencyAndAmount(currency, amount);
        });
    }

    private void initExchangeList(){
        //create ListView Adapter to control list items
        list_exchange_adapter = new ExchangeAdapter(mContext, mCurrencyUtil.getCurrencyList());
        list_exchange.setAdapter(list_exchange_adapter);
        list_exchange_adapter.updateBaseCurrencyAndAmount(mCurrencyUtil.cUSD, 1.0);
    }
}