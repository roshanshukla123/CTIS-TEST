package com.example.ctistest.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class Response  {



   public class Venues
    {
       public String name;
    }

    @SerializedName("venues")
   public ArrayList<Venues> venuesArrayList;
}
