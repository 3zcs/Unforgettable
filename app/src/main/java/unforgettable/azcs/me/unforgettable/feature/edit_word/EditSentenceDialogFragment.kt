package unforgettable.azcs.me.unforgettable.feature.edit_word

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_fragment_edit_sentence.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.data.model.Sentence

/**
 * Created by abdulazizalawshan on 1/16/18.
 */

class EditSentenceDialogFragment : DialogFragment() {

    lateinit var listener: onEditSentenceClickListener
    internal var sentence: Sentence? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_fragment_edit_sentence, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null && args.containsKey("sentence")) {
            sentence = args.getParcelable("sentence")
            etSentence.setText(sentence!!.sentence)
        }
        dialog.setTitle("title")
        etSentence.requestFocus()

        btnCancel.setOnClickListener { listener.onCancelClickListener() }

        btnDelete.setOnClickListener { listener.onDeleteClickListener(sentence) }

        btnSave.setOnClickListener {
            if (Utils.isValid(etSentence)) {
                sentence!!.sentence = etSentence.text.toString()
                listener.onSaveClickListener(sentence)
            }
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as onEditSentenceClickListener
        } catch (e: ClassCastException) {
            e.fillInStackTrace()
        }

    }

    interface onEditSentenceClickListener {
        fun onSaveClickListener(sentence: Sentence?)

        fun onDeleteClickListener(sentence: Sentence?)

        fun onCancelClickListener()
    }

    companion object {

        fun newInstance(sentence: Sentence): EditSentenceDialogFragment {

            val args = Bundle()
            args.putParcelable("sentence", sentence)
            val fragment = EditSentenceDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
