package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EditWordActivity extends AppCompatActivity implements SentenceAdapter.IShowEditSentenceDialogListiner,
        EditSentenceDialogFragment.onEditSentenceClickListener {
    TextView mWord, meaning;
    RecyclerView sentenceList;
    DatabaseReference wordDatabase;
    List<Sentence> sentences;
    FirebaseUser user;
    Word word;
    SentenceAdapter adapter;
    EditSentenceDialogFragment editSentenceDialogFragment;
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
        sentences = word.getPractice();
        adapter = new SentenceAdapter(this, sentences, this);
        sentenceList.setAdapter(adapter);
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
        editSentenceDialogFragment = EditSentenceDialogFragment.newInstance(sentence);
        editSentenceDialogFragment.show(fm, "");
    }

    @Override
    public void onSaveClickListener(Sentence sentence) {
        int index = -1;
        for (int i = 0; i < word.getPractice().size(); i++) {

            if (word.getPractice().get(i).getKey().equals(sentence.getKey())) {
                index = i;
                break;
            }
        }
        word.getPractice().remove(index);
        word.getPractice().add(sentence);
        wordDatabase.child(word.getId()).setValue(word);

        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment.dismiss();
    }

    @Override
    public void onDeleteClickListener(Sentence sentence) {
        word.getPractice().remove(sentence);
        wordDatabase.child(word.getId()).setValue(word);
        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment.dismiss();
    }

    @Override
    public void onCancelClickListener() {
        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment.dismiss();
    }


    @Override
    protected void onStart() {
        super.onStart();

        wordDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sentences.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(Word.class).getId().equals(word.getId())) {
                        word = snapshot.getValue(Word.class);
                        break;
                    }
                }
                sentences.addAll(word.getPractice());
                Log.d("Done", sentences.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", "Error");
            }
        });
    }


}
