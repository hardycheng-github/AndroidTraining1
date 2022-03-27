package com.example.androidtraining1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Adapter;
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
    ListView list_exchange;
    ListAdapter list_exchange_adapter;
    List<Map<String, Object>> items_exchange = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initView();
        initUtil();
        initExchangeList();
    }

    private void initView(){
        currency_type = findViewById(R.id.currency_type);
        currency_value = findViewById(R.id.currency_value);
        list_exchange = findViewById(R.id.list_exchange);
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
        list_exchange_adapter = new SimpleAdapter(mContext, items_exchange, R.layout.item_exchange,
                new String[]{"item_exchange_type", "item_exchange_value"},
                new int[]{R.id.item_exchange_type, R.id.item_exchange_value});
        list_exchange.setAdapter(list_exchange_adapter);
    }
}