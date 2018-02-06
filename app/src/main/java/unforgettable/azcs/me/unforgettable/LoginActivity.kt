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

class LoginActivity : AppCompatActivity() {
    internal var hint: TextView
    internal var password: EditText
    internal var email: EditText
    internal var login: Button
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        hint = findViewById(R.id.hint)
        password = findViewById(R.id.password)
        email = findViewById(R.id.email)
        login = findViewById(R.id.login_button)
        mAuth = FirebaseAuth.getInstance()

        hint.setOnClickListener { startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }

        login.setOnClickListener {
            if (Utils.isValidEmail(email) && Utils.isValid(password)) {
                signin()
            }
        }


    }


    fun userInfo() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
    }


    fun signin() {
        mAuth!!.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(this@LoginActivity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }

                    // ...
                }
    }

    private fun updateUI(o: Any?) {
        startActivity(Intent(this, MainActivity::class.java))
    }

    companion object {
        private val TAG = Class<*>::class.java!!.getSimpleName()
    }


}
