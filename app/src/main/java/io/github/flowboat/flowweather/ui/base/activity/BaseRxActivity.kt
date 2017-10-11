package io.github.flowboat.flowweather.ui.base.activity

import android.os.Bundle
import io.github.flowboat.flowweather.App
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import nucleus5.view.NucleusAppCompatActivity

/**
 * @author inorichi
 * @author nulldev
 */
abstract class BaseRxActivity<P : BasePresenter<*>> : NucleusAppCompatActivity<P>(), ActivityMixin {

    override fun onCreate(savedState: Bundle?) {
        val superFactory = presenterFactory
        setPresenterFactory {
            superFactory.createPresenter().apply {
                val app = application as App
                context = app.applicationContext
            }
        }
        super.onCreate(savedState)
    }

    override fun getActivity() = this

    var resumed = false
        private set

    override fun onResume() {
        super.onResume()
        resumed = true
    }

    override fun onPause() {
        resumed = false
        super.onPause()
    }

}
