package com.example.ctistest.ui;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ctistest.R;
import com.example.ctistest.adapter.RecyclerViewAdapter;
import com.example.ctistest.db.AppDatabase;
import com.example.ctistest.db.DatabaseClient;
import com.example.ctistest.db.entity.CompanyName;
import com.example.ctistest.model.MainResponse;
import com.example.ctistest.model.Response;
import com.example.ctistest.service.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ctistest.service.APIService.BASE_URL;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private ProgressDialog progressDialog;
    private RecyclerView rvItem;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvItem = findViewById(R.id.rvItem);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        getData();

    }

    void getData() {
        APIService apiService = retrofit
                .create(APIService.class);
        Observable<MainResponse> cryptoObservable = apiService.getData();
        cryptoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<MainResponse, Object>() {
                    @Override
                    public Object apply(MainResponse result) throws Exception {
                        return result.response;
                    }
                })
                .subscribe(this::handleResults, this::handleError);
    }

    private void handleResults(Object o) {
        if (o != null) {

            Response response = (Response) o;
            if (response.venuesArrayList != null && response.venuesArrayList.size() > 0) {

                saveDataOnDb(response.venuesArrayList);

            }
        }
    }

    private void handleError(Throwable t) {
        progressDialog.dismiss();
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        System.out.println("db<><> "+t.getMessage());

    }

    void saveDataOnDb(ArrayList<Response.Venues> venuesArrayList) {
        for (Response.Venues venues : venuesArrayList) {
            CompanyName companyName = new CompanyName();
            companyName.name = venues.name;
            DatabaseClient.getInstance(MainActivity.this).getAppDatabase().taskDao().insert(companyName);
        }
        progressDialog.dismiss();
        getDataFromRoomDb();
    }

    void getDataFromRoomDb() {
        AppDatabase appDatabase = DatabaseClient.getInstance(this).getAppDatabase();
        ArrayList<CompanyName> companyNameArrayList = (ArrayList<CompanyName>) appDatabase.taskDao().getAll();
        if (companyNameArrayList != null && companyNameArrayList.size() > 0) {
            recyclerViewAdapter = new RecyclerViewAdapter(companyNameArrayList);
            rvItem.setLayoutManager(new LinearLayoutManager(this));
            rvItem.setAdapter(recyclerViewAdapter);
            System.out.println("data<><>" + new Gson().toJson(companyNameArrayList));
        }

    }
}