package com.example.wvand.trivia;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class QuestionAdapter extends ArrayAdapter<Question> {

    Context context;

    // Declaring questions as an arraylist, so we can fill it with objects in constructor below
    ArrayList<Question> Questions;

    public QuestionAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Question> objects) {
        super(context, resource, objects);

        this.context = context;

        this.Questions = objects;
    }

    // Fills row for each question with questioncategory
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.questioncategory_row, parent, false);
        }

        // Find textview that must be set
        TextView categoryrow = convertView.findViewById(R.id.questionCategory);

        // Get data that textview will be set with
        String category = Questions.get(position).getCategory();

        categoryrow.setText(category);

        return convertView;
    }
}
