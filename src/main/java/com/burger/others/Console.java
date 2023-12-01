package com.burger.others;

import com.google.gson.Gson;

public class Console {
    public static  void Log(Object data) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(data);
        System.out.println(jsonString);
    }
}
