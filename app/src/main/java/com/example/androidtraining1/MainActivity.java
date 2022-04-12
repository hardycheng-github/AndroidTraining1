package com.example.androidtraining1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext = this;
    private Spinner currency_type;
    private EditText currency_value;
    private Button btn_exchange;
    private RecyclerView list_exchange;
    private ExchangeAdapter list_exchange_adapter;

    private CurrencyService mService;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = ((CurrencyService.MyBinder)iBinder).getService();
            initExchangeList();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    };

    public MainActivity() {
        super();
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            Log.d(TAG, "onStateChanged: " + event.name());
            if(event.equals(Lifecycle.Event.ON_CREATE)){
                setContentView(R.layout.activity_main);
                initView();
                initListener();
                bindCurrencyService();
            } else if(event.equals(Lifecycle.Event.ON_DESTROY)){
                unbindService(mConnection);
            }
        });
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
            String currency = "USD";
            switch (currency_type.getSelectedItemPosition()){
                case 0:
                    currency = "USD";
                    break;
                case 1:
                    currency = "TWD";
                    break;
                case 2:
                    currency = "JPY";
                    break;
            }
            double amount = 0.0;
            try {
                amount = Double.valueOf(currency_value.getText().toString());
            } catch (Exception e) {
                currency_value.setText("");
            }
            list_exchange_adapter.updateBaseCurrencyAndAmount(currency, amount);
        });
    }

    private void bindCurrencyService(){
        Intent intent = new Intent(MainActivity.this, CurrencyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    private void initExchangeList(){
        //create ListView Adapter to control list items
        list_exchange_adapter = new ExchangeAdapter(LayoutInflater.from(mContext), mService);
        list_exchange_adapter.updateBaseCurrencyAndAmount("USD", 1.0);
        list_exchange.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        list_exchange.setAdapter(list_exchange_adapter);
    }
}