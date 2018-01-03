package unforgettable.azcs.me.unforgettable;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    final String TAG = getClass().getSimpleName();

    TextView emptyList;
    RecyclerView WordRecyclerView;
    FloatingActionButton addFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WordRecyclerView = findViewById(R.id.word_recycler_view);
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton);
        emptyList = findViewById(R.id.emptyText);

        WordRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        WordRecyclerView.setAdapter(new WordsAdapter(null,this));

        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AddWordActivity.class));
            }
        });


    }

}
