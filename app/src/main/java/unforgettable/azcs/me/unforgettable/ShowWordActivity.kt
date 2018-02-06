package unforgettable.azcs.me.unforgettable

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import unforgettable.azcs.me.unforgettable.Utils.WORD

class ShowWordActivity : AppCompatActivity(), onAddSentenceClickListener, SentenceAdapter.IShowEditSentenceDialogListiner {
    internal var mWord: TextView
    internal var mMeaning: TextView
    internal var mSentenceList: RecyclerView
    internal var adapter: SentenceAdapter
    internal var word: Word? = null
    internal var addSentenceDialogFragment: AddSentenceDialogFragment? = null
    internal var sentenceList: MutableList<Sentence>
    private var mDatabase: DatabaseReference? = null
    private var user: FirebaseUser? = null
    private var sentanceRefrence: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_word)
        mWord = findViewById(R.id.word)
        mMeaning = findViewById(R.id.meaning)
        mSentenceList = findViewById(R.id.sentencesList)
        user = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)
        if (intent.extras != null && intent.extras!!.containsKey(WORD)) {
            word = intent.getParcelableExtra(WORD)
            mWord.text = word!!.word
            mMeaning.text = word!!.meaning
            mSentenceList.layoutManager = LinearLayoutManager(this)
            sentenceList = word!!.practice
            adapter = SentenceAdapter(this, sentenceList, this)
            mSentenceList.adapter = adapter
        } else {
            Toast.makeText(this, "Error Happen", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.word_option_menu, menu)
        menuInflater.inflate(R.menu.common_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_word -> {
                mDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid).child(word!!.id!!)
                mDatabase!!.removeValue()
                Toast.makeText(this, "Your Data has been deleted", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.edit_word -> {
                val intent = Intent(this, EditWordActivity::class.java)
                intent.putExtra("Word", word)
                startActivity(intent)
                return true
            }
            R.id.add_sentence -> {
                val fm = supportFragmentManager
                if (fm.findFragmentByTag("add") != null)
                    addSentenceDialogFragment = fm.findFragmentByTag("add") as AddSentenceDialogFragment
                else
                    addSentenceDialogFragment = AddSentenceDialogFragment.newInstance()

                addSentenceDialogFragment!!.show(fm, "add")
                return true
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveClickListener(sentence: String) {
        sentanceRefrence = mDatabase!!.child(word!!.id!!).push()
        val s = Sentence(sentence, sentanceRefrence!!.key)
        word!!.practice.add(s)
        mDatabase!!.child(word!!.id!!).setValue(word)
        if (addSentenceDialogFragment != null)
            addSentenceDialogFragment!!.dismiss()
    }

    override fun onCancelClickListener() {
        if (addSentenceDialogFragment != null)
            addSentenceDialogFragment!!.dismiss()
    }

    override fun showEditSentenceDialogListener(sentence: Sentence) {

    }

    override fun onStart() {
        super.onStart()

        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                sentenceList.clear()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.getValue<Word>(Word::class.java)!!.id == word!!.id) {
                        word = snapshot.getValue<Word>(Word::class.java)
                        break
                    }
                }
                sentenceList.addAll(word!!.practice)
                Log.d("Done", sentenceList.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Error", "Error")
            }
        })
    }
}
