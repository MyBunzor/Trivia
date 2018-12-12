package com.example.wvand.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

public class BooleanQuestionActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    Question question;
    String difficulty;
    String points = new String();
    int highscore;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean_question);

        // Get intent with data
        Intent questionInfo = getIntent();
        question = (Question) questionInfo.getSerializableExtra("clickedQuestion");
        difficulty = question.getDifficulty();

        // Get access to layouts
        TextView theQuestion = findViewById(R.id.Question);
        CheckBox answerone = findViewById(R.id.answerone);
        CheckBox answertwo = findViewById(R.id.answertwo);

        // Get specific data to set layouts
        String mainQuestion = question.getQuestion();
        String answertrue = question.getCorrect_answer();
        String answerfalse = question.getIncorrect_answers().replace("[", "");
        String False = answerfalse.replace("]", "");

        // Set layouts
        theQuestion.setText(mainQuestion);
        answerone.setText(answertrue);
        answertwo.setText(False);

    }

    public void submitAnswer(View view) {

        // Get intent with highscore and counter
        Intent count = getIntent();
        highscore = count.getIntExtra("hogescore", 0);
        counter = count.getIntExtra("teller", 0);

        counter += 1;

        CheckBox answerTrue = findViewById(R.id.answerone);

        // Check if right checkbox is checked, if yes, allocate points
        if (answerTrue.isChecked()) {
            if (this.difficulty == "easy") {
                highscore = highscore + 10;
            } else if (this.difficulty == "medium") {
                highscore = highscore + 20;
            } else {
                highscore = highscore + 30;
            }
        }

        // If user submits answer, redirection with highscore and counter to main app takes place
        Intent backtoMainTrivia = new Intent(BooleanQuestionActivity.this, TriviaActivity.class);
        backtoMainTrivia.putExtra("answer", highscore);
        backtoMainTrivia.putExtra("countor", counter);
        startActivity(backtoMainTrivia);

        // Post score if six questions are reached
        if (counter == 7) {

            // Cast the int highscore to a string
            points = String.valueOf(highscore);

            String url = "https://ide50-wpvandijk.cs50.io:8080/trivial";
            RequestQueue queue = Volley.newRequestQueue(this);
            PostRequest request = new PostRequest(Request.Method.POST, url, this, this, points);
            queue.add(request);

            Intent toHighscore = new Intent(BooleanQuestionActivity.this, HighscoreActivity.class);
            toHighscore.putExtra("highestscore", points);
            startActivity(toHighscore);
        }
    }

    // Method for first checkbox, to see if checked
    public void answeroneCheck(View view) {

        CheckBox answerTrue = findViewById(R.id.answerone);
        CheckBox answerFalse = findViewById(R.id.answertwo);

        // If true checkbox is checked, disable other checkbox
        if (answerTrue.isChecked()) {
            answerFalse.setChecked(false);
        }
    }

    // Method for second checkbox, to see if checked
    public void answertwoCheck(View view) {
            CheckBox answerTrue = findViewById(R.id.answerone);
            CheckBox answerFalse = findViewById(R.id.answertwo);

            // If false checkbox is checked, disable other checkbox
            if (answerFalse.isChecked()) {
                answerTrue.setChecked(false);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
