package unforgettable.azcs.me.unforgettable;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by abdulazizalawshan on 1/16/18.
 */

public class EditSentenceDialogFragment extends DialogFragment {

    Button save, delete, cancel;
    EditText sentence;

    public EditSentenceDialogFragment() {
    }

    public static EditSentenceDialogFragment newInstance(Sentence sentence) {

        Bundle args = new Bundle();

        EditSentenceDialogFragment fragment = new EditSentenceDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_edit_sentence, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sentence = view.findViewById(R.id.sentence);
        save = view.findViewById(R.id.btn_save);
        delete = view.findViewById(R.id.btn_delete);
        cancel = view.findViewById(R.id.btn_cancel);

        getDialog().setTitle("title");
        sentence.requestFocus();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
