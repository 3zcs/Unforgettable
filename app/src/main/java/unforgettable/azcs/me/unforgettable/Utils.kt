package unforgettable.azcs.me.unforgettable

import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText

/**
 * Created by abdulazizalawshan on 1/2/18.
 */

object Utils {

    val WORD = "WORD"

    fun isValid(editText: EditText): Boolean {
        if (TextUtils.isEmpty(editText.text.toString().trim { it <= ' ' })) {
            editText.error = "This field is required"
            editText.requestFocus()
            return false
        }

        return true
    }

    fun isValidEmail(editText: EditText): Boolean {
        if (!Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()) {
            editText.error = "set correct email"
            editText.requestFocus()
            return false
        }

        return true
    }
}
