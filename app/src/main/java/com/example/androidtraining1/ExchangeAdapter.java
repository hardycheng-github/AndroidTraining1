package com.example.androidtraining1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.androidtraining1.util.Currency;

import java.util.ArrayList;
import java.util.List;

public class ExchangeAdapter extends BaseAdapter {

    private List<Currency> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Currency baseCurrency;
    private double baseAmount;

    private class ViewHolder {
        TextView item_exchange_type;
        TextView item_exchange_value;
    }

    public ExchangeAdapter(Context context, List<Currency> list){
        mInflater = LayoutInflater.from(context);
        mList.clear();
        mList.addAll(list);
    }

    public void updateBaseCurrencyAndAmount(Currency base, double amount){
        baseCurrency = base;
        baseAmount = amount;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            // item not init yet
            view = mInflater.inflate(R.layout.item_exchange, viewGroup, false);
            holder = new ViewHolder();
            holder.item_exchange_type = view.findViewById(R.id.item_exchange_type);
            holder.item_exchange_value = view.findViewById(R.id.item_exchange_value);
            view.setTag(holder);
        } else {
            // item already init
            holder = (ViewHolder) view.getTag();
        }
        Currency targetCurrency = mList.get(i);
        holder.item_exchange_type.setText(targetCurrency.getCurrencyCode());
        String value = "";
        if(baseCurrency != null){
            value = String.format("%.2f", baseCurrency.exchangeTo(baseAmount, targetCurrency));
        }
        holder.item_exchange_value.setText(value);
        return view;
    }
}
