package unforgettable.azcs.me.unforgettable

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : AppCompatActivity() {
    internal var hint: TextView
    internal var password: EditText
    internal var email: EditText
    internal var signup: Button
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        hint = findViewById(R.id.hint)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        signup = findViewById(R.id.signin_button)
        mAuth = FirebaseAuth.getInstance()

        hint.setOnClickListener { startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)) }

        signup.setOnClickListener {
            if (Utils.isValidEmail(email) && Utils.isValid(password)) {
                createNewUser()
            }
        }


    }

    fun createNewUser() {
        mAuth!!.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        Toast.makeText(this@RegisterActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }
    }


    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null)
            startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        private val TAG = Class<*>::class.java!!.getSimpleName()
    }


}
