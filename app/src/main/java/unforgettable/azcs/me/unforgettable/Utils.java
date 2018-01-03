package unforgettable.azcs.me.unforgettable;

import android.text.TextUtils;
import android.util.Patterns;
import android.widget.EditText;

/**
 * Created by abdulazizalawshan on 1/2/18.
 */

public class Utils {

    public static boolean isValid(EditText editText){
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setError("This field is required");
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(EditText editText){
        if (!Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches()) {
            editText.setError("set correct email");
            editText.requestFocus();
            return false;
        }

        return true;
    }
}
