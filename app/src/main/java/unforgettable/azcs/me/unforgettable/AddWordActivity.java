package unforgettable.azcs.me.unforgettable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

public class AddWordActivity extends AppCompatActivity {
    EditText word, meaning, sentence;
    RecyclerView sentencelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        sentence = findViewById(R.id.sentence);
        sentencelist = findViewById(R.id.sentencesList);
    }
}
