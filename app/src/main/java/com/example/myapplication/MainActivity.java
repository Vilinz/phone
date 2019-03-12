package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button login;
    private Button clear;
    private TextView n;
    private TextView p;
    private TextView ph;
    private Myserver myserver;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
    private String name;
    private String password;
    private String phone;
    private TextView login_t;
    private TextView register_t;
    private TextView textView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.login);
        n = findViewById(R.id.user);
        p = findViewById(R.id.password);
        clear = findViewById(R.id.clear);
        ph = findViewById(R.id.phone);
        login_t = findViewById(R.id.login_t);
        register_t = findViewById(R.id.register_t);
        textView3 = findViewById(R.id.textView3);

        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3, TimeUnit.SECONDS);//连接超时时间
        builder.writeTimeout(3,TimeUnit.SECONDS);//写操作 超时时间
        builder.readTimeout(3,TimeUnit.SECONDS);//读操作超时时间

        myserver = (Myserver) new Retrofit.Builder().baseUrl("http://134.209.111.41:3000")
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(Myserver.class);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = n.getText().toString();
                password = p.getText().toString();
                phone = ph.getText().toString();
                String temp = login.getText().toString();

                switch (temp) {
                    case "登录":
                        DisposableObserver<String> disposableObserver_login = new DisposableObserver<String>() {
                            @Override
                            public void onNext(String r) {
                                if(r.equals("password_error")) {
                                    Toast.makeText(MainActivity.this, R.string.password_error, Toast.LENGTH_LONG).show();
                                } else if(r.equals("user_not_exist")) {
                                    Toast.makeText(MainActivity.this, R.string.user_not_exist, Toast.LENGTH_LONG).show();
                                } else if(r.equals("unknow_error")) {
                                    Toast.makeText(MainActivity.this, R.string.unknow_error, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.successfully, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    startActivity(intent);
                                }
                            }
                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                //Toast.makeText(GithubApi.this, R.string.network_error, Toast.LENGTH_LONG).show();
                            }
                        };
                        myserver.loginUser(name, password).subscribeOn(Schedulers.newThread()).
                                observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver_login);
                        mCompositeDisposable.add(disposableObserver_login);
                        break;
                    case "注册":
                        DisposableObserver<String> disposableObserver_register = new DisposableObserver<String>() {
                            @Override
                            public void onNext(String r) {
                                if(r.equals("username_used")) {
                                    Toast.makeText(MainActivity.this, R.string.username_used, Toast.LENGTH_LONG).show();
                                } else if(r.equals("register_successfully")) {
                                    Toast.makeText(MainActivity.this, R.string.register_successfully, Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, Login.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, R.string.unknow_error, Toast.LENGTH_LONG).show();
                                }

                            }
                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
                                e.printStackTrace();
                            }

                            @Override
                            public void onComplete() {
                                //Toast.makeText(GithubApi.this, R.string.network_error, Toast.LENGTH_LONG).show();
                            }
                        };
                        myserver.registerUser(name, password, phone).subscribeOn(Schedulers.newThread()).
                                observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver_register);
                        mCompositeDisposable.add(disposableObserver_register);
                        break;
                    default:
                        break;
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.setText("");
                p.setText("");
                ph.setText("");
            }
        });

        login_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.setVisibility(View.VISIBLE);
                ph.setVisibility(View.GONE);
                p.setVisibility(View.VISIBLE);
                login.setText("登录");
                register_t.setVisibility(View.VISIBLE);
                login_t.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
            }
        });

        register_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n.setVisibility(View.VISIBLE);
                ph.setVisibility(View.VISIBLE);
                p.setVisibility(View.VISIBLE);
                login.setText("注册");
                register_t.setVisibility(View.GONE);
                login_t.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
            }
        });
    }
}
