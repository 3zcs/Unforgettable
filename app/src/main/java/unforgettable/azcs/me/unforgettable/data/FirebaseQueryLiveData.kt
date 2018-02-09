package unforgettable.azcs.me.unforgettable.data

import android.arch.lifecycle.LiveData
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener


class FirebaseQueryLiveData(private var query: Query) : LiveData<DataSnapshot>() {
    private val tag = "FirebaseQueryLiveData"
    private val listener = MyValueEventListener()

    override fun onActive() {
        super.onActive()
        Log.d(tag, "onActive")
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        super.onInactive()
        Log.d(tag, "onInactive")
        query.removeEventListener(listener)
    }


    private inner class MyValueEventListener : ValueEventListener {
        private val tag = "MyValueEventListener"

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            value = dataSnapshot
        }

        override fun onCancelled(dataSnapshot: DatabaseError?) {
            Log.d(tag, "onInactive" + query, dataSnapshot?.toException())
        }

    }

}