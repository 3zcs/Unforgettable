package unforgettable.azcs.me.unforgettable.feature.show_word

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.dialog_fragment_add_sentence.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils

/**
 * Created by azcs on 18/01/18.
 */

class AddSentenceDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.dialog_fragment_add_sentence, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog.setTitle("title")
        etSentence.requestFocus()

        val listener = activity as onAddSentenceClickListener
        btnSave.setOnClickListener {
            if (Utils.isValid(etSentence))
                listener.onSaveClickListener(etSentence.text.toString())
        }

        btnCancel.setOnClickListener { listener.onCancelClickListener() }

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
