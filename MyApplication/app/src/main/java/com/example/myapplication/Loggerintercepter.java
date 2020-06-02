package com.example.myapplication;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Loggerintercepter implements Interceptor {
    private Request request;
    private Response response;
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.i("zhangrui", String.format("666Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.i("zhangrui", String.format("666Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        //toulianghuanzu start
        response.close();
        Request touRequest = new Request.Builder().url("https://www.taobao.com").build();
        response = chain.proceed(touRequest);
        //toulianghuanzu end
        return response;
    }
}
