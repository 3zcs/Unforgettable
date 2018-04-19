package unforgettable.azcs.me.unforgettable.feature.authentication

import android.support.transition.ChangeBounds
import android.support.transition.ChangeImageTransform
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet

class CustomTransition : TransitionSet() {
    init {
        ordering = TransitionSet.ORDERING_TOGETHER
        addTransition(ChangeBounds()).addTransition(ChangeTransform())
        addTransition(ChangeImageTransform())
    }
}