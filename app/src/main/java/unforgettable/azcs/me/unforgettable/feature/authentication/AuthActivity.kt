package unforgettable.azcs.me.unforgettable.feature.authentication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import unforgettable.azcs.me.unforgettable.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        viewPager.adapter = PagerAdapter(supportFragmentManager, viewPager)
    }
}
