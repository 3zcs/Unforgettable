package unforgettable.azcs.me.unforgettable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

public class Word implements Parcelable {
    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };
    private String id;
    private String word;
    private String meaning;
    private List<Sentence> practice = new ArrayList<>();

    public Word() {
    }

    Word(String id, String word, String meaning, List<Sentence> practice) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.practice = practice;
    }

    protected Word(Parcel in) {
        id = in.readString();
        word = in.readString();
        meaning = in.readString();
        in.readTypedList(practice, Sentence.CREATOR);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<Sentence> getPractice() {
        return practice;
    }

    public void setPractice(List<Sentence> practice) {
        this.practice = practice;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(word);
        dest.writeString(meaning);
        dest.writeTypedList(practice);
    }
}
