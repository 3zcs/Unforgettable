package unforgettable.azcs.me.unforgettable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdulazizalawshan on 1/6/18.
 */

public class Sentence implements Parcelable {
    public static final Creator<Sentence> CREATOR = new Creator<Sentence>() {
        @Override
        public Sentence createFromParcel(Parcel in) {
            return new Sentence(in);
        }

        @Override
        public Sentence[] newArray(int size) {
            return new Sentence[size];
        }
    };
    private String sentence;
    private String key;

    public Sentence(String sentence, String key) {
        this.sentence = sentence;
        this.key = key;
    }

    protected Sentence(Parcel in) {
        sentence = in.readString();
        key = in.readString();
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sentence);
        dest.writeString(key);
    }
}
