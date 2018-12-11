package com.example.wvand.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class BooleanQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boolean_question);

        // Get intent with data
        Intent questionInfo = getIntent();
        Question question = (Question) questionInfo.getSerializableExtra("clickedQuestion");

        // Get access to layouts
        TextView theQuestion = findViewById(R.id.Question);
        CheckBox answerone = findViewById(R.id.answerone);
        CheckBox answertwo = findViewById(R.id.answertwo);

        // Get specific data to set layouts
        String mainQuestion = question.getQuestion();
        String answertrue = question.getCorrect_answer();
        String answerfalse = question.getIncorrect_answers();

        // Set layouts
        theQuestion.setText(mainQuestion);
        answerone.setText(answertrue);
        answertwo.setText(answerfalse);

    }

    public void submitAnswer(View view) {
        System.out.println("trueclicked");
    }

    // Method for first checkbox, to see if checked
    public void answeroneCheck(View view) {

    }

    // Method for second checkbox, to see if checked
    public void answertwoCheck(View view) {

    }
}
