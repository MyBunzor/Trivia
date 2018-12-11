package com.example.wvand.trivia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/*
    Objects of this class can do POST requests with parameters.
*/
public class PostRequest extends StringRequest {

    // Constructor
    public PostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);

    }


    // Method to supply parameters to the request
    @Override
    protected Map<String, String> getParams() {

        Map<String, String> params = new HashMap<>();
        params.put("studentcount", "300");
        return params;
    }
}