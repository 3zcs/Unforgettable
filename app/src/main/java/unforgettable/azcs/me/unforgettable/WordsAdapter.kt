package unforgettable.azcs.me.unforgettable

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import unforgettable.azcs.me.unforgettable.Utils.WORD

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

class WordsAdapter(private val context: Context, private val words: List<Word>) : RecyclerView.Adapter<WordsAdapter.WordsViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        return WordsViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
        holder.bind(words[position])
    }

    override fun getItemCount(): Int {
        return words.size
    }

    inner class WordsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private var mWord: TextView = itemView.findViewById(R.id.word)
        private var mMeaning: TextView = itemView.findViewById(R.id.meaning)
        lateinit var word: Word

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(word: Word) {
            this.word = word
            mWord.text = word.word
            mMeaning.text = word.meaning
        }

        override fun onClick(v: View) {
            val intent = Intent(context, ShowWordActivity::class.java)
            intent.putExtra(WORD, word)
            context.startActivity(intent)
        }
    }
}
