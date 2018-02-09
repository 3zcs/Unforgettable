package unforgettable.azcs.me.unforgettable.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_word.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils.WORD
import unforgettable.azcs.me.unforgettable.data.model.Word
import unforgettable.azcs.me.unforgettable.feature.show_word.ShowWordActivity

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

class WordsAdapter(private val context: Context, private val words: List<Word>) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(LayoutInflater.from(context).inflate(R.layout.item_word, parent, false))
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }

    inner class WordsViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer,
            View.OnClickListener {

        lateinit var word: Word

        init {
            containerView.setOnClickListener(this)
        }

        fun bind(word: Word) {
            this.word = word
            tvWord.text = word.word
            tvMeaning.text = word.meaning
        }

        override fun onClick(v: View) {
            val intent = Intent(context, ShowWordActivity::class.java)
            intent.putExtra(WORD, word)
            context.startActivity(intent)
        }
    }
}
