package unforgettable.azcs.me.unforgettable;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abdulazizalawshan on 1/12/18.
 */

public class SentenceAdapter extends RecyclerView.Adapter<SentenceAdapter.SentenceViewHolder> {

    private Context context;
    private List<Sentence> sentences;
    private IShowEditSentenceDialogListiner listiner;

    public SentenceAdapter(Context context, List<Sentence> sentences, IShowEditSentenceDialogListiner listiner) {
        this.context = context;
        this.sentences = sentences;
        this.listiner = listiner;
    }

    @Override
    public SentenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SentenceViewHolder(
                LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,parent,false));
    }

    @Override
    public void onBindViewHolder(SentenceViewHolder holder, int position) {
            holder.bind(sentences.get(position));
    }

    @Override
    public int getItemCount() {
        return sentences.size();
    }

    interface IShowEditSentenceDialogListiner {
        void showEditSentenceDialogListener(Sentence sentence);
    }

    class SentenceViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Sentence sentence;
        SentenceViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listiner.showEditSentenceDialogListener(sentence);
                }
            });
        }

        void bind(Sentence sentence){
            this.sentence = sentence;
            textView.setText(sentence.getSentence());
        }
    }
}
