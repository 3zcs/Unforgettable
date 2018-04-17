package unforgettable.azcs.me.unforgettable.feature.authentication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_register.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.feature.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        tvHint.setOnClickListener { startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)) }

        btnSignIn.setOnClickListener {
            if (Utils.isValidEmail(etEmail) && Utils.isValid(etPassword)) {
                createNewUser()
            }
        }


    }

    private fun createNewUser() {
        mAuth!!.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("RegisterActivity", "createUserWithEmail:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                        Toasty.error(this@RegisterActivity, task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
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


}
