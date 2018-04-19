package unforgettable.azcs.me.unforgettable.feature.authentication


import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.ChangeBounds
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_login.*

import unforgettable.azcs.me.unforgettable.R
import unforgettable.azcs.me.unforgettable.Utils
import unforgettable.azcs.me.unforgettable.feature.main.MainActivity

class LoginFragment : Fragment() {

    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.InverseAppTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        return localInflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()

        btnLogin.setOnClickListener {
            if (Utils.isValidEmail(etEmail) && Utils.isValid(etPassword)) {
                signin()
            }
        }
    }

    fun signin() {
        mAuth!!.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(activity!!) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("LoginActivity", "signInWithEmail:success")
                        val user = mAuth!!.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("LoginActivity", "signInWithEmail:failure", task.exception)
                        Toasty.error(activity!!, task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
    }

    private fun updateUI(o: Any?) {
        startActivity(Intent(activity, MainActivity::class.java))
    }

    private fun loginPageInFront() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_login_front)

        val transition = CustomTransition()
        transition.interpolator = AnticipateOvershootInterpolator()
        transition.duration = 800
        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    private fun loginPageInBackground() {
        val constraintSet = ConstraintSet()
        constraintSet.clone(activity, R.layout.fragment_login)
        val transition = ChangeBounds()
        transition.interpolator = AnticipateOvershootInterpolator(1.0f)
        transition.duration = 1200

        TransitionManager.beginDelayedTransition(constraint, transition)
        constraintSet.applyTo(constraint)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        if (activity != null)
            when (isVisibleToUser) {
                true -> loginPageInFront()
                else -> loginPageInBackground()
            }
    }
}
