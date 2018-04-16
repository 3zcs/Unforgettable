package unforgettable.azcs.me.unforgettable.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sentence.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.data.model.Sentence

/**
 * Created by abdulazizalawshan on 1/12/18.
 */

class SentenceAdapter(private val context: Context, private val sentences: List<Sentence>, private val listiner: IShowEditSentenceDialogListiner) : RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentenceViewHolder {
        return SentenceViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_sentence, parent, false))
    }

    override fun onBindViewHolder(holder: SentenceViewHolder, position: Int) {
        holder.bind(sentences[position])
    }

    override fun getItemCount(): Int {
        return sentences.size
    }

    interface IShowEditSentenceDialogListiner {
        fun showEditSentenceDialogListener(sentence: Sentence)
    }

    inner class SentenceViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
            LayoutContainer {

        lateinit var sentence: Sentence

        init {
            containerView.setOnClickListener { listiner.showEditSentenceDialogListener(sentence) }
        }

        fun bind(sentence: Sentence) {
            this.sentence = sentence
            tvSentence.text = sentence.sentence
        }
    }
}
