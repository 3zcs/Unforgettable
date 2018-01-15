package unforgettable.azcs.me.unforgettable;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static unforgettable.azcs.me.unforgettable.Utils.WORD;

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

public class WordsAdapter extends RecyclerView.Adapter<WordsAdapter.WordsViewHolder> {

    private List<Word> words = new ArrayList<>();
    private Context context;

    public WordsAdapter(List<Word> words, Context context) {
        this.words = words;
        this.context = context;
    }

    @Override
    public WordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordsViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent,false));
    }

    @Override
    public void onBindViewHolder(WordsViewHolder holder, int position) {
        holder.bind(words.get(position));
    }

    @Override
    public int getItemCount() {
        return words.size();
    }

    class WordsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mWord, mMeaning;
        Word word;
        public WordsViewHolder(View itemView) {
            super(itemView);
            mWord = itemView.findViewById(R.id.word);
            mMeaning = itemView.findViewById(R.id.meaning);
            itemView.setOnClickListener(this);
        }

        public void bind(Word word) {
            this.word = word;
            mWord.setText(word.getWord());
            mMeaning.setText(word.getMeaning());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,ShowWordActivity.class);
            intent.putExtra(WORD,word);
            context.startActivity(intent);
        }
    }
}
