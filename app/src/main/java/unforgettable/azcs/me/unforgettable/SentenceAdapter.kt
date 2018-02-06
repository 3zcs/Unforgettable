package unforgettable.azcs.me.unforgettable

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by abdulazizalawshan on 1/12/18.
 */

class SentenceAdapter(private val context: Context, private val sentences: List<Sentence>, private val listiner: IShowEditSentenceDialogListiner) : RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        return SentenceViewHolder(
                LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        holder.bind(sentences[position])
    }

    override fun getItemCount(): Int {
        return sentences.size
    }

    internal interface IShowEditSentenceDialogListiner {
        fun showEditSentenceDialogListener(sentence: Sentence)
    }

    internal inner class SentenceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var sentence: Sentence

        init {
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnClickListener { listiner.showEditSentenceDialogListener(sentence) }
        }

        fun bind(sentence: Sentence) {
            this.sentence = sentence
            textView.text = sentence.sentence
        }
    }
}
