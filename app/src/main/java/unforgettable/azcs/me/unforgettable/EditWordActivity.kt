package unforgettable.azcs.me.unforgettable

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EditWordActivity : AppCompatActivity(), SentenceAdapter.IShowEditSentenceDialogListiner, EditSentenceDialogFragment.onEditSentenceClickListener {
    lateinit var mWord: EditText
    lateinit var meaning: EditText
    lateinit var sentenceList: RecyclerView
    lateinit var wordDatabase: DatabaseReference
    lateinit var sentences: MutableList<Sentence>
    internal var user: FirebaseUser? = null
    internal var word: Word? = null
    lateinit var adapter: SentenceAdapter
    internal var editSentenceDialogFragment: EditSentenceDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_word)
        mWord = findViewById(R.id.word)
        meaning = findViewById(R.id.meaning)
        sentenceList = findViewById(R.id.sentencesList)
        word = intent.getParcelableExtra("Word")
        user = FirebaseAuth.getInstance().currentUser
        wordDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)

        mWord.setText(word!!.word)
        meaning.setText(word!!.meaning)
        sentenceList.layoutManager = LinearLayoutManager(this)
        sentences = word!!.practice
        adapter = SentenceAdapter(this, sentences, this)
        sentenceList.adapter = adapter
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.word_edit_menu, menu)
        menuInflater.inflate(R.menu.common_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_word -> {
                if (Utils.isValid(mWord) && Utils.isValid(meaning)) {
                    val newWord = Word(word!!.id!!, mWord.text.toString(),
                            meaning.text.toString(),
                            word!!.practice)
                    wordDatabase.child(word!!.id!!).setValue(newWord)
                    startActivity(Intent(this, MainActivity::class.java))
                }
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

    override fun showEditSentenceDialogListener(sentence: Sentence) {
        val fm = supportFragmentManager
        editSentenceDialogFragment = EditSentenceDialogFragment.newInstance(sentence)
        editSentenceDialogFragment!!.show(fm, "")
    }

    override fun onSaveClickListener(sentence: Sentence?) {
        var index = -1
        for (i in 0 until word!!.practice.size) {

            if (word!!.practice[i].key == sentence!!.key) {
                index = i
                break
            }
        }
        word!!.practice.removeAt(index)
        word!!.practice.add(sentence!!)
        wordDatabase.child(word!!.id!!).setValue(word)

        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment!!.dismiss()
    }

    override fun onDeleteClickListener(sentence: Sentence?) {
        word!!.practice.remove(sentence)
        wordDatabase.child(word!!.id!!).setValue(word)
        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment!!.dismiss()
    }

    override fun onCancelClickListener() {
        if (editSentenceDialogFragment != null)
            editSentenceDialogFragment!!.dismiss()
    }


    override fun onStart() {
        super.onStart()

        wordDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                sentences.clear()
                for (snapshot in dataSnapshot.children) {
                    if (snapshot.getValue<Word>(Word::class.java)!!.id == word!!.id) {
                        word = snapshot.getValue<Word>(Word::class.java)
                        break
                    }
                }
                sentences.addAll(word!!.practice)
                Log.d("Done", sentences.toString())
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("Error", "Error")
            }
        })
    }


}
