package com.example.yang.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import okhttp3.OkHttpClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    View btn1,btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"btn1 clicked", Toast.LENGTH_SHORT).show();
                request1();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"btn2 clicked", Toast.LENGTH_SHORT).show();

                Easy8583Ans mypack = new Easy8583Ans();
                mypack.frame8583QD(mypack.fieldsSend,mypack.pack);
                Log.d("8583：",mypack.pack.toString());
                Log.d("fields：",mypack.getFields(mypack.fieldsSend));

                String str ="0057600087000061310031110808000020000000C0001650001536313030303030313839383633303134313131313038350011000000000030002553657175656E6365204E6F31323330363036313030303030310003303031";
                byte[] bt = Easy8583Ans.hexStringToBytes(str);
                mypack.ans8583Fields(bt,bt.length,mypack.fieldsRecv);
                Log.d("fields recv：",mypack.getFields(mypack.fieldsRecv));
            }
        });


    }
    public void request() {

        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance(MainActivity.this);
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl("http://fy.iciba.com")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiManager apiService = retrofit.create(ApiManager.class);

        Call<LoginResult> call = apiService.getCall();
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    // do SomeThing
                    Log.d("AA","成功");
                    response.body().show();
                    response.toString();
                } else {
                    //直接操作UI 返回的respone被直接解析成你指定的modle
                    response.body().show();
                    response.toString();

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

                // do onFailure代码
                Log.d("AA","失败");
            }
        });
    }

    public void request1() {

        OkHttpClient client = OkHttp3Utils.getOkHttpSingletonInstance(MainActivity.this);
        String url = "https://140.207.168.62:30000/";
        //String url = "http://3s.dkys.org:16932";
        Retrofit retrofit = new Retrofit.Builder().client(client).baseUrl(url)
                .addConverterFactory(MyConverterFactory.create()).build();
        ApiManager apiService = retrofit.create(ApiManager.class);

        DataBean data = new DataBean();
        Call<LoginResult> call = apiService.postData(data.bodyhex);
        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    // do SomeThing
                    Log.d("AA","成功");

                    Log.d("AA",response.raw().toString());
                        //byte[] arr = response.raw().toString();
                        //arr[0] = 0;
                    //response.body().show();
                } else {
                    //直接操作UI 返回的respone被直接解析成你指定的modle
                    Log.d("AA","失败");
                    //response.body().show();

                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

                // do onFailure代码
                Log.d("AA","失败", t);
            }
        });
    }
}
