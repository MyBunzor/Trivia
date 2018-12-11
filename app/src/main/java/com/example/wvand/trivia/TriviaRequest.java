package com.example.wvand.trivia;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    // Constructor that accepts a Context and stores it in context instance
    public TriviaRequest(Context context) {

        this.context = context;
    }
    Context context;
    ArrayList trivia = new ArrayList();
    Callback activity;
    String error;
    String question;

    @Override
    public void onErrorResponse(VolleyError error) {

        // Get error message, put it in string initialized above
        this.error = error.getMessage();

        // Passing description of error back to the activity
        activity.gotTriviaError(this.error);
    }

    public interface Callback {
        void gotTrivia(ArrayList<Question> trivia);
        void gotTriviaError(String message);
    }


    @Override
    public void onResponse(JSONObject response) {

        ArrayList<Question> trivia = new ArrayList<Question>();

        // Create JSONArray to put data in
        JSONArray array = new JSONArray();

        try {
            array = response.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Loop through data, getting categories
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject eachQuestion = array.getJSONObject(i);
                String category = eachQuestion.getString("category");
                String type = eachQuestion.getString("type");
                String difficulty = eachQuestion.getString("difficulty");
                String question = eachQuestion.getString("question");
                String correct_answer = eachQuestion.getString("correct_answer");
                String incorrect_answers = eachQuestion.getString("incorrect_answers");
                Question vraag = new Question(category, type, difficulty, question, correct_answer, incorrect_answers);
                System.out.println("question: " + vraag);
                trivia.add(vraag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        activity.gotTrivia(trivia);
    }
    }
        // Retrieve questions and answers from the API
        public void getTrivia (Callback activity){

            this.activity = activity;

            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest("https://opentdb.com/api.php?amount=10",
                            null, this, this);

            queue.add(jsonObjectRequest);
        }
}
