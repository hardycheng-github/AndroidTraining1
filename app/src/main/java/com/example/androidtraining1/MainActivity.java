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
    private static final String TAG = MainActivity.class.getSimpleName();
    Context mContext;
    CurrencyUtil mCurrencyUtil;
    Spinner currency_type;
    EditText currency_value;
    Button btn_exchange;
    ListView list_exchange;
    ExchangeAdapter list_exchange_adapter;
    List<Map<String, Object>> items_exchange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initListener();
        initUtil();
        initExchangeList();
    }

    private void initView(){
        currency_type = findViewById(R.id.currency_type);
        currency_value = findViewById(R.id.currency_value);
        list_exchange = findViewById(R.id.list_exchange);
        btn_exchange = findViewById(R.id.btn_exchange);
    }

    private void initListener(){
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
                currency_value.setText("0");
            }
            list_exchange_adapter.updateBaseCurrencyAndAmount(currency, amount);
        });
    }

    private void initUtil(){
        mCurrencyUtil = new CurrencyUtil();
    }

    private void initExchangeList(){
        Map<String, Object> exchange_map;
        exchange_map = new HashMap<>();
        exchange_map.put("item_exchange_type", mCurrencyUtil.cUSD.getCurrencyCode());
        exchange_map.put("item_exchange_value", 0);
        items_exchange.add(exchange_map);
        exchange_map = new HashMap<>();
        exchange_map.put("item_exchange_type", mCurrencyUtil.cTWD.getCurrencyCode());
        exchange_map.put("item_exchange_value", 0);
        items_exchange.add(exchange_map);
        exchange_map = new HashMap<>();
        exchange_map.put("item_exchange_type", mCurrencyUtil.cJPY.getCurrencyCode());
        exchange_map.put("item_exchange_value", 0);
        items_exchange.add(exchange_map);
        list_exchange_adapter = new ExchangeAdapter(mContext, mCurrencyUtil.getCurrencyList());
        list_exchange.setAdapter(list_exchange_adapter);
        list_exchange_adapter.updateBaseCurrencyAndAmount(mCurrencyUtil.cUSD, 1.0);
    }
}