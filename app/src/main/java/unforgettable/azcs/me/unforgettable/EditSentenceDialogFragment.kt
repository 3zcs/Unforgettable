package unforgettable.azcs.me.unforgettable

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * Created by abdulazizalawshan on 1/16/18.
 */

class EditSentenceDialogFragment : DialogFragment() {

    lateinit var save: Button
    lateinit var delete: Button
    lateinit var cancel: Button
    lateinit var editTextSentence: EditText
    lateinit var listener: onEditSentenceClickListener
    internal var sentence: Sentence? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_fragment_edit_sentence, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editTextSentence = view!!.findViewById(R.id.sentence)
        save = view.findViewById(R.id.btn_save)
        delete = view.findViewById(R.id.btn_delete)
        cancel = view.findViewById(R.id.btn_cancel)
        val args = arguments
        if (args != null && args.containsKey("sentence")) {
            sentence = args.getParcelable("sentence")
            editTextSentence.setText(sentence!!.sentence)
        }
        dialog.setTitle("title")
        editTextSentence.requestFocus()

        cancel.setOnClickListener { listener.onCancelClickListener() }

        delete.setOnClickListener { listener.onDeleteClickListener(sentence) }

        save.setOnClickListener {
            if (Utils.isValid(editTextSentence)) {
                sentence!!.sentence = editTextSentence.text.toString()
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
