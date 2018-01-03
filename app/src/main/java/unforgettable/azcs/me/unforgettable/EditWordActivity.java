package unforgettable.azcs.me.unforgettable;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

public class EditWordActivity extends AppCompatActivity {
    TextView word, meaning;
    RecyclerView sentenceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        word = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        sentenceList = findViewById(R.id.sentencesList);
    }
}
