package unforgettable.azcs.me.unforgettable.feature.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import unforgettable.azcs.me.unforgettable.data.FirebaseQueryLiveData

/**
 * Created by azcs on 06/02/18.
 */
class WordsViewModel : ViewModel() {
    private lateinit var liveData: FirebaseQueryLiveData

    fun getDataSnapShot(): LiveData<DataSnapshot> {
        return liveData
    }

    fun setDataSnapShot(reference: DatabaseReference) {
        this.liveData = FirebaseQueryLiveData(reference)
    }
}