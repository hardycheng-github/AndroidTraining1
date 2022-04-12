package com.example.androidtraining1;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrencyService extends LifecycleService implements Runnable {
    private static final String TAG = CurrencyService.class.getSimpleName();
    private static final int SYNC_INTERVAL = 10000;
    private Thread mTask = new Thread(this);
    private Map<String, Double> mCurrenciesRateMap = new HashMap<>();

    public class MyBinder extends Binder {
        public CurrencyService getService(){
            return CurrencyService.this;
        }
    }

    public CurrencyService() {
        super();
        getLifecycle().addObserver((LifecycleEventObserver) (source, event) -> {
            Log.d(TAG, "onStateChanged: " + event.name());
            if(event.equals(Lifecycle.Event.ON_START)){
                mTask.start();
            } else if(event.equals(Lifecycle.Event.ON_STOP)){
                mTask.interrupt();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return new MyBinder();
    }

    @Override
    public void run() {
        Log.d(TAG, "+++ runnable start +++");
        while(getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            try {
                updateCurrenciesRate();
                mTask.sleep(SYNC_INTERVAL);
            } catch (InterruptedException e) {
                Log.d(TAG, "runnable interrupted!");
            }
        }
        Log.d(TAG, "--- runnable stop ---");
    }

    private void updateCurrenciesRate(){
        Log.d(TAG, "updateCurrenciesRate!");
        // TODO realtime currency update
        mCurrenciesRateMap.put("USD", fakeRateRandomFixed(1.0));
        mCurrenciesRateMap.put("JPY", fakeRateRandomFixed(122.0));
        mCurrenciesRateMap.put("TWD", fakeRateRandomFixed(28.5));
    }

    //random rate with +-10%
    private double fakeRateRandomFixed(double originRate){
        return originRate*(1.0+(Math.random()-0.5)/10);
    }

    public List<String> getCurrencyList(){
        return Arrays.asList(new String[]{"USD", "TWD", "JPY"});
    }

    public double exchange(double amount, String from, String to){
        return amount * getExchangeRate(from, to);
    }

    private double getExchangeRate(String from, String to){
        double fromRate = getExchangeRateFromUSD(from);
        double toRate = getExchangeRateFromUSD(to);
        return toRate / fromRate;
    }

    private double getExchangeRateFromUSD(String to){
        return mCurrenciesRateMap.getOrDefault(to, 0.0);
    }
}