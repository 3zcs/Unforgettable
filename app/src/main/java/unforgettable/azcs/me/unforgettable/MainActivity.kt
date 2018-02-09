package unforgettable.azcs.me.unforgettable

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import unforgettable.azcs.me.unforgettable.data.WordsViewModel

class MainActivity : AppCompatActivity() {
    internal val TAG = javaClass.getSimpleName()

    lateinit var emptyList: TextView
    lateinit var WordRecyclerView: RecyclerView
    lateinit var addFloatingActionButton: FloatingActionButton
    lateinit var wordDatabase: DatabaseReference
    private var user: FirebaseUser? = null
    lateinit var wordList: MutableList<Word>
    lateinit var adapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        WordRecyclerView = findViewById(R.id.word_recycler_view)
        addFloatingActionButton = findViewById(R.id.addFloatingActionButton)
        emptyList = findViewById(R.id.emptyText)
        user = FirebaseAuth.getInstance().currentUser
        wordDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)
        val viewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)
        viewModel.setDataSnapShot(wordDatabase)
        val liveData = viewModel.getDataSnapShot()

        wordList = ArrayList()
        adapter = WordsAdapter(this, wordList)
        WordRecyclerView.layoutManager = LinearLayoutManager(this)
        WordRecyclerView.adapter = adapter
        addFloatingActionButton.setOnClickListener { startActivity(Intent(this@MainActivity, AddWordActivity::class.java)) }

        liveData.observe(this, Observer<DataSnapshot> {
            wordList.clear()
            if (it != null) {
                for (snapshot in it.children) {
                    val word = snapshot.getValue<Word>(Word::class.java)
                    if (word != null)
                        wordList.add(word)
                }
                Log.d("Done", wordList.toString())
                adapter.notifyDataSetChanged()
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
