package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static unforgettable.azcs.me.unforgettable.Utils.WORD;

public class ShowWordActivity extends AppCompatActivity {
    TextView mWord, mMeaning;
    RecyclerView mSentenceList;
    SentenceAdapter adapter;
    Word word = null;
    private DatabaseReference mDatabase;
    private FirebaseUser user;

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
            adapter = new SentenceAdapter(this,word.getPractice());
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
                return true;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
