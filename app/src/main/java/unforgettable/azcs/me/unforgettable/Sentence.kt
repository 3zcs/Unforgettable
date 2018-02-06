package unforgettable.azcs.me.unforgettable

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by abdulazizalawshan on 1/6/18.
 */

class Sentence : Parcelable {
    var sentence: String? = null
    var key: String? = null

    constructor() {}

    constructor(sentence: String, key: String) {
        this.sentence = sentence
        this.key = key
    }

    protected constructor(`in`: Parcel) {
        sentence = `in`.readString()
        key = `in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(sentence)
        dest.writeString(key)
    }

    companion object {
        val CREATOR: Parcelable.Creator<Sentence> = object : Parcelable.Creator<Sentence> {
            override fun createFromParcel(`in`: Parcel): Sentence {
                return Sentence(`in`)
            }

            override fun newArray(size: Int): Array<Sentence?> {
                return arrayOfNulls(size)
            }
        }
    }
}
