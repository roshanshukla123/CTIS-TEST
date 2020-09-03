package com.example.ctistest.service;

import com.example.ctistest.model.MainResponse;
import com.example.ctistest.model.Response;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface APIService {
    String BASE_URL = "https://api.foursquare.com/";
    @GET("v2/venues/search?query=ctel&ll=17.3850,78.4867&oauth_token=QMPYUTVROORNKAQNETOBMAEKLPXET0U5LGTZCGAKLZFHLGC2&v=20191120")
    Observable<MainResponse> getData();
}
