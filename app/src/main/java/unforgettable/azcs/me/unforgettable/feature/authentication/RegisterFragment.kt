package unforgettable.azcs.me.unforgettable.feature.authentication


import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_register.*
import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.feature.main.MainActivity

class RegisterFragment : Fragment() {
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register_front, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        btnSignIn.setOnClickListener {
            if (Utils.isValidEmail(etEmail) && Utils.isValid(etPassword)) {
                createNewUser()
            }
        }
    }

    private fun createNewUser() {
        mAuth!!.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("RegisterActivity", "createUserWithEmail:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.exception)
                        Toasty.error(activity!!, task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null)
            startActivity(Intent(activity, MainActivity::class.java))
    }

    private fun singupPageInFront() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_register_front)

        val transition = CustomTransition()
        transition.interpolator = AnticipateOvershootInterpolator()
        transition.duration = 800
        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    private fun signupPageInBackground() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_register)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (activity != null)
            when (isVisibleToUser) {
                true -> singupPageInFront()
                else -> signupPageInBackground()
            }
    }
}
