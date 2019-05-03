package com.example.machathon;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
//import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private ImageView voiceButton;
    private String addedItem;
    private EditText enterItem;

    private static final int RESULT_SPEECH = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view);
        voiceButton = findViewById(R.id.voiceButton);
        enterItem = findViewById(R.id.enter_item_edit_text);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        voiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listen();
            }
        });

        enterItem.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if ((i == EditorInfo.IME_ACTION_DONE) || ((keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (keyEvent.getAction() == KeyEvent.ACTION_DOWN))) {
                    addAddedItemToList(enterItem.getText().toString());
                    enterItem.setText("");
                }
                return false;
            }
        });

        //prepareMovieData();
    }

//    private void prepareMovieData() {
//        Movie movie = new Movie("Mad Max: Fury Road", "Action & Adventure", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("Inside Out", "Animation, Kids & Family", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("Shaun the Sheep", "Animation", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("The Martian", "Science Fiction & Fantasy", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("Mission: Impossible Rogue Nation", "Action", "2015");
//        movieList.add(movie);
//
//        movie = new Movie("Up", "Animation", "2009");
//        movieList.add(movie);
//
//        movie = new Movie("Star Trek", "Science Fiction", "2009");
//        movieList.add(movie);
//
//        movie = new Movie("The LEGO Movie", "Animation", "2014");
//        movieList.add(movie);
//
//        movie = new Movie("Iron Man", "Action & Adventure", "2008");
//        movieList.add(movie);
//
//        movie = new Movie("Aliens", "Science Fiction", "1986");
//        movieList.add(movie);
//
//        movie = new Movie("Chicken Run", "Animation", "2000");
//        movieList.add(movie);
//
//        movie = new Movie("Back to the Future", "Science Fiction", "1985");
//        movieList.add(movie);
//
//        movie = new Movie("Raiders of the Lost Ark", "Action & Adventure", "1981");
//        movieList.add(movie);
//
//        movie = new Movie("Goldfinger", "Action & Adventure", "1965");
//        movieList.add(movie);
//
//        movie = new Movie("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
//        movieList.add(movie);
//
//        mAdapter.notifyDataSetChanged();
//    }

    private void addAddedItemToList(String addedItem) {

        Movie movie = new Movie(addedItem);
        movieList.add(movie);

        mAdapter.notifyDataSetChanged();
    }


    private void listen() {
        if (SpeechRecognizer.isRecognitionAvailable(getApplicationContext())) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            startActivityForResult(intent, RESULT_SPEECH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_SPEECH) {
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> spokenText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                addAddedItemToList(spokenText.get(0));
            }
        }
    }
}