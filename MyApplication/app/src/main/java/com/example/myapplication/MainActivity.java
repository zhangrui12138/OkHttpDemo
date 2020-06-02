package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private OkHttpClient okHttpClient;
    private Request request;
    private Call call;
    private Response response;
    private String url = "https://www.baidu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //getSys();
                postReSys();
                //getReSys();
            }
        }).start();
    }

    private void getReSys(){
        if(okHttpClient == null){
            okHttpClient = new OkHttpClient();
        }
        if(request == null){
            request = new Request.Builder().url(url).get().build();
        }
        if(call == null){
            call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                      Log.d("zhangrui",""+response.isSuccessful());
                      Log.d("zhangrui",""+call.request().toString());
                }
            });
        }
    }

   private FormBody formBody;
    private List<String> encodedNames = new ArrayList<>();
    private List<String> encodedValues = new ArrayList<>();
    private void postReSys(){
        try {
            if(okHttpClient == null){
                okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Loggerintercepter()).build();
            }
            if(formBody == null){
                formBody = new FormBody(encodedNames,encodedValues);
            }
            if(request == null){
               request = new Request.Builder().url(url).post(formBody).build();
            }
            if(call == null){
              call = okHttpClient.newCall(request);
              call.enqueue(new Callback() {
                  @Override
                  public void onFailure(@NotNull Call call, @NotNull IOException e) {

                  }

                  @Override
                  public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                     Log.d("zhangrui","response.toString="+response.toString());
                     InputStream inputStream = response.body().byteStream();
                     int i=0;
                     byte[] bytes = new byte[1024];
                     while ((i = inputStream.read(bytes)) != -1){
                         String str = new String(bytes);
                         Log.d("zhangrui","str="+str);
                     }
                     Log.d("zhangrui","response.toString="+response.body().charStream().toString());
                     Log.d("zhangrui","response.toString="+call.request().toString());
                  }
              });
            }

        } catch (Exception e) {
            Log.d("zhangrui","e.getMessage"+e.getMessage());
        }
    }

    private void getSys() {Log.d("zhangrui","kfdghjfhgjf");
        try {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient();
            }
            if (request == null) {
                request = new Request.Builder().url(url).get().build();
            }
            if (call == null) {
                call = okHttpClient.newCall(request);
            }
            if (response == null) {

                response = call.execute();Log.d("zhangrui","response.isSuccessful="+response.isSuccessful());
                if (response.isSuccessful()) {
                    Log.d("zhangrui", "response.message=" + response.message());
                    Log.d("zhangrui", "response.toString=" + response.toString());
                    Log.d("zhangrui", "request.toString=" + request.toString());
                }

            }
        } catch (IOException e) {
           Log.d("zhangrui","e.getMessage="+e.getMessage());
        }

    }
}
