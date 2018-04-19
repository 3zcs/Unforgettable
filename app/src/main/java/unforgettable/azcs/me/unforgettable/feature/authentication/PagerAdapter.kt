package unforgettable.azcs.me.unforgettable.feature.authentication

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager

class PagerAdapter(fm: FragmentManager?, viewPager: ViewPager) : FragmentStatePagerAdapter(fm) {

    var pager: ViewPager = viewPager

    override fun getItem(position: Int): Fragment = when (position) {
        1 -> LoginFragment()
        else -> RegisterFragment()
    }

    override fun getCount(): Int = 2

    override fun getPageWidth(position: Int): Float {
        return 0.8f
    }
}