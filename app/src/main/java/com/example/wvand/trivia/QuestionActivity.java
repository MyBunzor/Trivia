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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public class QuestionActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<String> {

    Question question;
    String difficulty;
    String points = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Get intent with data
        Intent questionInfo = getIntent();
        question = (Question) questionInfo.getSerializableExtra("clickedQuestion");
        difficulty = question.getDifficulty();

        // Get access to layouts to set them
        TextView theQuestion = findViewById(R.id.Question);
        CheckBox answer1 = findViewById(R.id.answer1);
        CheckBox answer2 = findViewById(R.id.answer2);
        CheckBox answer3 = findViewById(R.id.answer3);
        CheckBox answer4 = findViewById(R.id.answer4);

        // Get question from data
        String mainQuestion = question.getQuestion();
        String correctAnswer = question.getCorrect_answer();
        String[] incorrectAnswers = question.getIncorrect_answers().split(",");

        // Get the three incorrect answers and put them in different variables
        String incorrect1 = incorrectAnswers[0].replace("[", " ");
        String incorrect2 = incorrectAnswers[1];
        String incorrect3 = incorrectAnswers[2].replace("]", " ");

        // Set the question with data from intent
        theQuestion.setText(mainQuestion);
        answer1.setText(correctAnswer);
        answer2.setText(String.valueOf(incorrect1));
        answer3.setText(String.valueOf(incorrect2));
        answer4.setText(String.valueOf(incorrect3));
    }

    // Method for first checkbox (with correct answer), to see if checked
    public void answer1Check(View view) {

        CheckBox correct = findViewById(R.id.answer1);
        CheckBox firstincorrect = findViewById(R.id.answer2);
        CheckBox secondincorrect = findViewById(R.id.answer3);
        CheckBox thirdincorrect = findViewById(R.id.answer4);

        // See if checkbox is checked, if so, allocate points depending on difficulty
        if (correct.isChecked()) {

            // Set other checkboxes off
            firstincorrect.setChecked(false);
            secondincorrect.setChecked(false);
            thirdincorrect.setChecked(false);
        }
    }

    // Method for second checkbox, to see if checked
    public void answer2Check(View view) {
        CheckBox correct = findViewById(R.id.answer1);
        CheckBox firstincorrect = findViewById(R.id.answer2);
        CheckBox secondincorrect = findViewById(R.id.answer3);
        CheckBox thirdincorrect = findViewById(R.id.answer4);

        if (firstincorrect.isChecked()) {

            // Set other checkboxes off
            correct.setChecked(false);
            secondincorrect.setChecked(false);
            thirdincorrect.setChecked(false);
        }
    }

    // Method for third checkbox, to see if checked
    public void answer3Check(View view) {

        CheckBox correct = findViewById(R.id.answer1);
        CheckBox firstincorrect = findViewById(R.id.answer2);
        CheckBox secondincorrect = findViewById(R.id.answer3);
        CheckBox thirdincorrect = findViewById(R.id.answer4);

        if (secondincorrect.isChecked()) {

            // Set other checkboxes off
            correct.setChecked(false);
            firstincorrect.setChecked(false);
            thirdincorrect.setChecked(false);
        }
    }

    // Method for fourth checkbox, to see if checked
    public void answer4Check(View view) {

        CheckBox correct = findViewById(R.id.answer1);
        CheckBox firstincorrect = findViewById(R.id.answer2);
        CheckBox secondincorrect = findViewById(R.id.answer3);
        CheckBox thirdincorrect = findViewById(R.id.answer4);

        if (thirdincorrect.isChecked()) {

            // Set other checkboxes off
            correct.setChecked(false);
            firstincorrect.setChecked(false);
            secondincorrect.setChecked(false);
        }
    }

    public void submitAnswer(View view) {


        CheckBox correct = findViewById(R.id.answer1);

        // See if checkbox is checked, if so, allocate points depending on difficulty
        if (correct.isChecked()) {
            if (this.difficulty == "easy") {
                points += 10;
            } else if (this.difficulty == "medium") {
                points += 20;
            } else {
                points += 30;
            }
        }

        // If user submits answer, redirection with points to main app takes place
        Intent backtoMainTrivia = new Intent(QuestionActivity.this, TriviaActivity.class);
        backtoMainTrivia.putExtra("answer", points);
        startActivity(backtoMainTrivia);


        // TEST: post score of single question in a web server
        String url = "https://ide50-wpvandijk.cs50.io:8080/trivia";
        RequestQueue queue = Volley.newRequestQueue(this);
        PostRequest request = new PostRequest(Request.Method.POST, url, this, this, points);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        System.out.println("Response: " + response);

    }
}
