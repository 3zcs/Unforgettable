package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddWordActivity extends AppCompatActivity {
    EditText word, meaning, sentence;
    RecyclerView sentencelist;
    Button addButton;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference wordIdRef;
    DatabaseReference sentenceIdRef;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        sentence = findViewById(R.id.sentence);
        sentencelist = findViewById(R.id.sentencesList);
        addButton = findViewById(R.id.addbtn);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isValid(word) && Utils.isValid(meaning)) {
                    myRef = database.getReference(user.getUid());
                    wordIdRef = myRef.push();
                    myRef.child(wordIdRef.getKey()).setValue(getWord());
                    startActivity(new Intent(AddWordActivity.this, MainActivity.class));
                }
            }
        });


    }

    private Word getWord() {

        if (TextUtils.isEmpty(sentence.getText().toString()))
            return new Word(wordIdRef.getKey(), word.getText().toString(), meaning.getText().toString(), null);

        sentenceIdRef = wordIdRef.push();
        final Sentence s = new Sentence(sentence.getText().toString(), sentenceIdRef.getKey());
        return new Word(wordIdRef.getKey(), word.getText().toString(), meaning.getText().toString(), new ArrayList<Sentence>() {
            {
                add(s);
            }
        });
    }
}
