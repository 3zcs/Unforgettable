package unforgettable.azcs.me.unforgettable

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

/**
 * Created by azcs on 18/01/18.
 */

class AddSentenceDialogFragment : DialogFragment() {
    lateinit var save: Button
    lateinit var cancel: Button
    lateinit var sentence: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_fragment_add_sentence, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sentence = view!!.findViewById(R.id.sentence)
        save = view.findViewById(R.id.btn_save)
        cancel = view.findViewById(R.id.btn_cancel)

        dialog.setTitle("title")
        sentence.requestFocus()

        val listener = activity as onAddSentenceClickListener
        save.setOnClickListener {
            if (Utils.isValid(sentence))
                listener.onSaveClickListener(sentence.text.toString())
        }

        cancel.setOnClickListener { listener.onCancelClickListener() }

    }

    companion object {

        fun newInstance(): AddSentenceDialogFragment {

            val args = Bundle()

            val fragment = AddSentenceDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
