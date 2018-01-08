package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final String TAG = getClass().getSimpleName();

    TextView emptyList;
    RecyclerView WordRecyclerView;
    FloatingActionButton addFloatingActionButton;
    DatabaseReference wordDatabase;
    FirebaseUser user;
    List<Word> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WordRecyclerView = findViewById(R.id.word_recycler_view);
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton);
        emptyList = findViewById(R.id.emptyText);
        wordList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        wordDatabase = FirebaseDatabase.getInstance().getReference(user.getUid());
        WordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WordRecyclerView.setAdapter(new WordsAdapter(null,this));

        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddWordActivity.class));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        wordDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                wordList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Word word = snapshot.getValue(Word.class);
                    wordList.add(word);
                }

                Log.d("Done", wordList.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Error", "Error");
            }
        });
    }
}
