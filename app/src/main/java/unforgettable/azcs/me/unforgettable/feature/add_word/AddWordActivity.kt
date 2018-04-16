package unforgettable.azcs.me.unforgettable.feature.add_word

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_word.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.data.model.Sentence
import unforgettable.azcs.me.unforgettable.data.model.Word
import unforgettable.azcs.me.unforgettable.feature.main.MainActivity
import java.util.*

class AddWordActivity : AppCompatActivity() {
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var wordIdRef: DatabaseReference
    lateinit var sentenceIdRef: DatabaseReference
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        database = FirebaseDatabase.getInstance()
        user = FirebaseAuth.getInstance().currentUser!!
        btnAdd.setOnClickListener {
            if (Utils.isValid(etWord) && Utils.isValid(etMeaning)) {
                myRef = database.getReference(user!!.uid)
                wordIdRef = myRef.push()
                myRef.child(wordIdRef.key).setValue(getWord())
                startActivity(Intent(this@AddWordActivity, MainActivity::class.java))
            }
        }


    }

    private fun getWord(): Word {
        if (TextUtils.isEmpty(etSentence.text.toString()))
            return Word(wordIdRef.key, etWord.text.toString(), etMeaning.text.toString(), ArrayList())

        sentenceIdRef = wordIdRef.push()
        val s = Sentence(etSentence.text.toString(), sentenceIdRef.key)
        return Word(wordIdRef.key, etWord.text.toString(), etWord.text.toString(), object : ArrayList<Sentence>() {
            init {
                add(s)
            }
        })
    }
}
