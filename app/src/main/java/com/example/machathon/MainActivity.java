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
import android.content.SharedPreferences;
import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ShoppingListItem> shoppingListItems = new ArrayList<>();
    private ShoppingListAdapter mAdapter;
    private EditText enterItem;
    SharedPreferences sharedpreferences;
    private static final int RESULT_SPEECH = 123;
    public static final String Name = "nameKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    EditText a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        ImageView voiceButton = findViewById(R.id.voiceButton);
        enterItem = findViewById(R.id.enter_item_edit_text);

        mAdapter = new ShoppingListAdapter(shoppingListItems);
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

                    a=findViewById(R.id.enter_item_edit_text);


                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String n  = a.getText().toString();
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Name, n);
                    editor.commit();

                }
                return false;
            }
        });
    }

    private void addAddedItemToList(String addedItem)
    {

        ShoppingListItem shoppingListItem = new ShoppingListItem(addedItem);
        shoppingListItems.add(shoppingListItem);

        mAdapter.notifyDataSetChanged();

    }


    private void listen()
    {
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