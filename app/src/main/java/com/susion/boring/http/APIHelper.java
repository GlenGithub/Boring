package com.susion.boring.http;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by susion on 17/1/20.
 */
public class APIHelper {

    public static final int REQUEST_SUCCESSS = 200;
    private static Retrofit mRetrofit;
    private HashMap<Class, Object> mServicesMap = new HashMap<>();
    private static APIHelper sInstance;

    private APIHelper(){

    }

    private synchronized static APIHelper getInstance(){
        if (sInstance == null) {
            sInstance = new APIHelper();
            initRetrofit();
        }
        return sInstance;
    }

    private static void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(createClient())
                .baseUrl(BaseURL.MUSIC)
                .build();
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.writeTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        return  builder.build();
    }


    public static <T> void subscribeSimpleRequest(Observable<T> observable, Observer<T> observer){
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


    private synchronized <T> T getService(Class<T> c){
        if (!mServicesMap.containsKey(c)) {
            mServicesMap.put(c, mRetrofit.create(c));
        }
        return (T) mServicesMap.get(c);
    }

    public static MusicServices getMusicServices(){
        return getInstance().getService(MusicServices.class);
    }



}
