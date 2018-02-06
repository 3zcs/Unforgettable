package unforgettable.azcs.me.unforgettable

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddWordActivity : AppCompatActivity() {
    lateinit var word: EditText
    lateinit var meaning: EditText
    lateinit var sentence: EditText
    lateinit var sentencelist: RecyclerView
    lateinit var addButton: Button
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var wordIdRef: DatabaseReference
    lateinit var sentenceIdRef: DatabaseReference
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)
        word = findViewById(R.id.word)
        meaning = findViewById(R.id.meaning)
        sentence = findViewById(R.id.sentence)
        sentencelist = findViewById(R.id.sentencesList)
        addButton = findViewById(R.id.addbtn)
        database = FirebaseDatabase.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!
        addButton.setOnClickListener {
            if (Utils.isValid(word) && Utils.isValid(meaning)) {
                myRef = database.getReference(user!!.uid)
                wordIdRef = myRef.push()
                myRef.child(wordIdRef.key).setValue(getWord())
                startActivity(Intent(this@AddWordActivity, MainActivity::class.java))
            }
        }


    }

    private fun getWord(): Word {

        if (TextUtils.isEmpty(sentence.text.toString()))
            return Word(wordIdRef.key, word.text.toString(), meaning.text.toString(), ArrayList())

        sentenceIdRef = wordIdRef.push()
        val s = Sentence(sentence.text.toString(), sentenceIdRef.key)
        return Word(wordIdRef.key, word.text.toString(), meaning.text.toString(), object : ArrayList<Sentence>() {
            init {
                add(s)
            }
        })
    }
}
