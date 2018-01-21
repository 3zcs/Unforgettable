package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static unforgettable.azcs.me.unforgettable.Utils.WORD;

public class ShowWordActivity extends AppCompatActivity implements onAddSentenceClickListener, SentenceAdapter.IShowEditSentenceDialogListiner {
    TextView mWord, mMeaning;
    RecyclerView mSentenceList;
    SentenceAdapter adapter;
    Word word = null;
    AddSentenceDialogFragment addSentenceDialogFragment;
    List<Sentence> sentenceList;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private DatabaseReference sentanceRefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_word);
        mWord = findViewById(R.id.word);
        mMeaning = findViewById(R.id.meaning);
        mSentenceList = findViewById(R.id.sentencesList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference(user.getUid());
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(WORD)){
            word = getIntent().getParcelableExtra(WORD);
            mWord.setText(word.getWord());
            mMeaning.setText(word.getMeaning());
            mSentenceList.setLayoutManager(new LinearLayoutManager(this));
            sentenceList = word.getPractice();
            adapter = new SentenceAdapter(this, sentenceList, this);
            mSentenceList.setAdapter(adapter);
        }else {
            Toast.makeText(this,"Error Happen", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.word_option_menu,menu);
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_word:
                mDatabase = FirebaseDatabase.getInstance().getReference(user.getUid()).child(word.getId());
                mDatabase.removeValue();
                Toast.makeText(this,"Your Data has been deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,MainActivity.class));
                return true;
            case R.id.edit_word:
                Intent intent = new Intent(this, EditWordActivity.class);
                intent.putExtra("Word", word);
                startActivity(intent);
                return true;
            case R.id.add_sentence:
                FragmentManager fm = getSupportFragmentManager();
                if (fm.findFragmentByTag("add") != null)
                    addSentenceDialogFragment = (AddSentenceDialogFragment) fm.findFragmentByTag("add");
                else
                    addSentenceDialogFragment = AddSentenceDialogFragment.newInstance();

                addSentenceDialogFragment.show(fm, "add");
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveClickListener(String sentence) {
        sentanceRefrence = mDatabase.child(word.getId()).push();
        Sentence s = new Sentence(sentence, sentanceRefrence.getKey());
        word.getPractice().add(s);
        mDatabase.child(word.getId()).setValue(word);
        if (addSentenceDialogFragment != null)
            addSentenceDialogFragment.dismiss();
    }

    @Override
    public void onCancelClickListener() {
        if (addSentenceDialogFragment != null)
            addSentenceDialogFragment.dismiss();
    }

    @Override
    public void showEditSentenceDialogListener(Sentence sentence) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sentenceList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getValue(Word.class).getId().equals(word.getId())) {
                        word = snapshot.getValue(Word.class);
                        break;
                    }
                }
                sentenceList.addAll(word.getPractice());
                Log.d("Done", sentenceList.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", "Error");
            }
        });
    }
}
