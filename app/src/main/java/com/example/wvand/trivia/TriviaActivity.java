package com.example.wvand.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.security.auth.callback.Callback;

public class TriviaActivity extends AppCompatActivity implements TriviaRequest.Callback{

    ArrayList<Question> Trivia = new ArrayList<Question>();
    int highscore;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        // Get counter from startactivity
        Intent count = getIntent();
        counter = count.getIntExtra("counter", 0);

        // Get counter from questionactivity
        Intent updatecount = getIntent();
        counter = updatecount.getIntExtra("countor", 0);

        System.out.println("COUNTER: " + counter);

        // Get 0 highscore from startactivity
        Intent score = getIntent();
        highscore = score.getIntExtra("highscore", 0);

        // Get highscore from questionactivity
        Intent updatehighscore = getIntent();
        highscore = updatehighscore.getIntExtra("answer", 0);

        System.out.println("HIGHSCORE: " + highscore);

        // Make a new API request to get new questions and answers
        TriviaRequest request = new TriviaRequest(this);
        request.getTrivia(this);

        // Set the listener on the listview
        ListView ListViewQuestions = findViewById(R.id.ListViewQuestions);
        ListViewQuestions.setOnItemClickListener(new ListViewListener());
    }

    @Override
    public void gotTrivia(ArrayList<Question> trivia) {

        this.Trivia = trivia;

        QuestionAdapter categoryAdapter = new QuestionAdapter(this, R.layout.questioncategory_row, Trivia);

        ListView ListViewQuestions = findViewById(R.id.ListViewQuestions);

        ListViewQuestions.setAdapter(categoryAdapter);
        System.out.println("Adapter set");
    }

    @Override
    public void gotTriviaError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private class ListViewListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get questioncategory that user clicked on
            Question clickedQuestion = (Question) parent.getItemAtPosition(position);

            try {

                // Get type of question
                String type = clickedQuestion.getType();

                // Different activity for multiple and boolean
                switch (type) {
                    case "multiple":

                        // Open question activity with multiple answers, pass int highscore along
                        Intent toQuestion = new Intent(TriviaActivity.this, QuestionActivity.class);
                        toQuestion.putExtra("clickedQuestion", clickedQuestion);
                        toQuestion.putExtra("hogescore", highscore);
                        toQuestion.putExtra("teller", counter);
                        startActivity(toQuestion);
                        break;

                    case "boolean":

                        // Open question activity with boolean answers
                        Intent toBoolean = new Intent(TriviaActivity.this, BooleanQuestionActivity.class);
                        toBoolean.putExtra("clickedQuestion", clickedQuestion);
                        toBoolean.putExtra("hogescore", highscore);
                        toBoolean.putExtra("teller", counter);
                        startActivity(toBoolean);
                        break;
                }
            }
            catch(Exception e){
                    e.printStackTrace();
                    System.out.println("TIME");
            }
        }
    }


}
