package unforgettable.azcs.me.unforgettable;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

public class Word implements Parcelable {
    String word;
    String meaning;
    List<String> practice = new ArrayList<>();


    protected Word(Parcel in) {
        word = in.readString();
        meaning = in.readString();
        practice = in.createStringArrayList();
    }

    public Word(String word, String meaning, List<String> practice) {
        this.word = word;
        this.meaning = meaning;
        this.practice = practice;
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

    public List<String> getPractice() {
        return practice;
    }

    public void setPractice(List<String> practice) {
        this.practice = practice;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(word);
        dest.writeString(meaning);
        dest.writeStringList(practice);
    }
}
