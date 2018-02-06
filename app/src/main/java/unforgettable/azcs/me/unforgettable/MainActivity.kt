package unforgettable.azcs.me.unforgettable

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*

class MainActivity : AppCompatActivity() {
    internal val TAG = javaClass.getSimpleName()

    internal var emptyList: TextView
    internal var WordRecyclerView: RecyclerView
    internal var addFloatingActionButton: FloatingActionButton
    internal var wordDatabase: DatabaseReference
    internal var user: FirebaseUser? = null
    internal var wordList: MutableList<Word>
    internal var adapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WordRecyclerView = findViewById(R.id.word_recycler_view)
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton)
        emptyList = findViewById(R.id.emptyText)
        wordList = ArrayList()
        adapter = WordsAdapter(this, wordList)
        user = FirebaseAuth.getInstance().currentUser
        wordDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)
        WordRecyclerView.layoutManager = LinearLayoutManager(this)
        WordRecyclerView.adapter = adapter

        addFloatingActionButton.setOnClickListener { startActivity(Intent(this@MainActivity, AddWordActivity::class.java)) }


    }

    override fun onStart() {
        super.onStart()

        wordDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                wordList.clear()
                for (snapshot in dataSnapshot.children) {
                    val word = snapshot.getValue<Word>(Word::class.java)
                    wordList.add(word)
                }

                Log.d("Done", wordList.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Error", "Error")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.common_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
