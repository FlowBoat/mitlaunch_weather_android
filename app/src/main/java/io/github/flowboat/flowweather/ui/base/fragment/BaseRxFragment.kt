package io.github.flowboat.flowweather.ui.base.fragment

import android.os.Bundle
import io.github.flowboat.flowweather.App
import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import nucleus5.view.NucleusSupportFragment

/**
 * @author inorichi
 * @author nulldev
 */
abstract class BaseRxFragment<P : BasePresenter<*>> : NucleusSupportFragment<P>(), FragmentMixin {

    override fun onCreate(savedState: Bundle?) {
        val superFactory = presenterFactory
        setPresenterFactory {
            superFactory.createPresenter().apply {
                (activity?.application)?.let {
                    context = it.applicationContext
                }
            }
        }
        super.onCreate(savedState)
    }
}