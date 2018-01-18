package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditWordActivity extends AppCompatActivity implements SentenceAdapter.IShowEditSentenceDialogListiner {
    TextView mWord, meaning;
    RecyclerView sentenceList;
    DatabaseReference wordDatabase;
    FirebaseUser user;
    Word word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        mWord = findViewById(R.id.word);
        meaning = findViewById(R.id.meaning);
        sentenceList = findViewById(R.id.sentencesList);
        word = getIntent().getParcelableExtra("Word");
        user = FirebaseAuth.getInstance().getCurrentUser();
        wordDatabase = FirebaseDatabase.getInstance().getReference(user.getUid());

        mWord.setText(word.getWord());
        meaning.setText(word.getMeaning());
        sentenceList.setLayoutManager(new LinearLayoutManager(this));
        sentenceList.setAdapter(new SentenceAdapter(this, word.getPractice(), this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.word_edit_menu, menu);
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_word:
                Word newWord = new Word(word.getId(), mWord.getText().toString(),
                        meaning.getText().toString(),
                        word.getPractice());
                wordDatabase.child(word.getId()).setValue(newWord);
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEditSentenceDialogListener(Sentence sentence) {
        FragmentManager fm = getSupportFragmentManager();
        EditSentenceDialogFragment editSentenceDialogFragment = EditSentenceDialogFragment.newInstance(sentence);
        editSentenceDialogFragment.show(fm, "");
    }
}
