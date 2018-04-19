package unforgettable.azcs.me.unforgettable.feature.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.adapters.WordsAdapter
import unforgettable.azcs.me.unforgettable.data.WordsViewModel
import unforgettable.azcs.me.unforgettable.data.model.Word
import unforgettable.azcs.me.unforgettable.feature.add_word.AddWordActivity

class MainActivity : AppCompatActivity() {

    lateinit var wordDatabase: DatabaseReference
    private var user: FirebaseUser? = null
    lateinit var wordList: ArrayList<Word>
    lateinit var adapter: WordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user = FirebaseAuth.getInstance().currentUser
        wordDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)
        val viewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)
        viewModel.setDataSnapShot(wordDatabase)
        val liveData = viewModel.getDataSnapShot()

        wordList = ArrayList()
        adapter = WordsAdapter(this, wordList)
        rvWords.layoutManager = LinearLayoutManager(this)
        rvWords.adapter = adapter
        fabAdd.setOnClickListener { startActivity(Intent(this@MainActivity, AddWordActivity::class.java)) }

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
                return Utils.logout(this)

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
