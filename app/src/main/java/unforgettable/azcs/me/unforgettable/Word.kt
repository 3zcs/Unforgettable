package unforgettable.azcs.me.unforgettable

import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by abdulazizalawshan on 12/16/17.
 */

class Word : Parcelable {
    var id: String? = null
    var word: String? = null
    var meaning: String? = null
    var practice: MutableList<Sentence> = ArrayList()

    constructor() {}

    internal constructor(id: String, word: String, meaning: String, practice: MutableList<Sentence>) {
        this.id = id
        this.word = word
        this.meaning = meaning
        this.practice = practice
    }

    protected constructor(`in`: Parcel) {
        id = `in`.readString()
        word = `in`.readString()
        meaning = `in`.readString()
        `in`.readTypedList(practice, Sentence.CREATOR)
    }


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(id)
        dest.writeString(word)
        dest.writeString(meaning)
        dest.writeTypedList(practice)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Word> = object : Parcelable.Creator<Word> {
            override fun createFromParcel(`in`: Parcel): Word {
                return Word(`in`)
            }

            override fun newArray(size: Int): Array<Word?> {
                return arrayOfNulls(size)
            }
        }
    }
}
