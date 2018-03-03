package io.github.flowboat.flowweather.ui.base.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * @author inorichi
 * @author nulldev
 */
abstract class BaseActivity : AppCompatActivity(), ActivityMixin {

    override fun getActivity() = this

    var resumed = false
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        setAppTheme()
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onPause() {
        resumed = false
        super.onPause()
    }

}