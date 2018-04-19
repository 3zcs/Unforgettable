package unforgettable.azcs.me.unforgettable.feature.show_word

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_show_word.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.Utils.WORD
import unforgettable.azcs.me.unforgettable.adapters.SentenceAdapter
import unforgettable.azcs.me.unforgettable.data.WordsViewModel
import unforgettable.azcs.me.unforgettable.data.model.Sentence
import unforgettable.azcs.me.unforgettable.data.model.Word
import unforgettable.azcs.me.unforgettable.feature.edit_word.EditWordActivity
import unforgettable.azcs.me.unforgettable.feature.main.MainActivity

class ShowWordActivity : AppCompatActivity(), onAddSentenceClickListener, SentenceAdapter.IShowEditSentenceDialogListiner {

    lateinit var adapter: SentenceAdapter
    internal var word: Word? = null
    private var addSentenceDialogFragment: AddSentenceDialogFragment? = null
    lateinit var sentenceList: MutableList<Sentence>
    private var mDatabase: DatabaseReference? = null
    private var user: FirebaseUser? = null
    private var sentenceReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_word)
        val viewModel = ViewModelProviders.of(this).get(WordsViewModel::class.java)
        user = FirebaseAuth.getInstance().currentUser
        mDatabase = FirebaseDatabase.getInstance().getReference(user!!.uid)

        if (intent.extras != null && intent.extras!!.containsKey(WORD)) {
            word = intent.getParcelableExtra(WORD)
            textView_word.text = word!!.word
            textView_meaning.text = word!!.meaning
            setSupportActionBar(toolbar)
            rvSentences.layoutManager = LinearLayoutManager(this)
            sentenceList = word!!.practice
            adapter = SentenceAdapter(this, sentenceList, this)
            rvSentences.adapter = adapter
            viewModel.setDataSnapShot(mDatabase!!)
            val liveData = viewModel.getDataSnapShot()
            liveData.observe(this, Observer<DataSnapshot> {
                sentenceList.clear()
                if (it != null) {
                    for (snapshot in it.children) {
                        val snapshotWord = snapshot.getValue<Word>(Word::class.java)
                        if (word != null && snapshotWord!!.id.equals(word!!.id))
                            sentenceList.addAll(snapshotWord.practice)
                    }
                    Log.d("Done", sentenceList.toString())
                    adapter.notifyDataSetChanged()
                }


            })
        } else {
            Toasty.error(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }

        FAB_addSentence.setOnClickListener({
            val fm = supportFragmentManager
            addSentenceDialogFragment = if (fm.findFragmentByTag("add") != null)
                fm.findFragmentByTag("add") as AddSentenceDialogFragment
            else
                AddSentenceDialogFragment.newInstance()

            addSentenceDialogFragment!!.show(fm, "add")
        })
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
                Toasty.success(this, getString(R.string.word_deleted), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.edit_word -> {
                val intent = Intent(this, EditWordActivity::class.java)
                intent.putExtra("Word", word)
                startActivity(intent)
                return true
            }
            R.id.logout -> {
                return Utils.logout(this)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveClickListener(sentence: String) {
        sentenceReference = mDatabase!!.child(word!!.id!!).push()
        val s = Sentence(sentence, sentenceReference!!.key)
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

}
