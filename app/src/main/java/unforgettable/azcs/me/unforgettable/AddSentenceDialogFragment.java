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
 * Created by azcs on 18/01/18.
 */

public class AddSentenceDialogFragment extends DialogFragment {
    Button save, cancel;
    EditText sentence;

    public AddSentenceDialogFragment() {
    }

    public static AddSentenceDialogFragment newInstance() {

        Bundle args = new Bundle();

        AddSentenceDialogFragment fragment = new AddSentenceDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_add_sentence, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sentence = view.findViewById(R.id.sentence);
        save = view.findViewById(R.id.btn_save);
        cancel = view.findViewById(R.id.btn_cancel);

        getDialog().setTitle("title");
        sentence.requestFocus();

        final onAddSentenceClickListener listener = (onAddSentenceClickListener) getActivity();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isValid(sentence))
                    listener.onSaveClickListener(sentence.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancelClickListener();
            }
        });

    }
}
