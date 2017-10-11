package io.github.flowboat.flowweather.ui.base.fragment

import android.support.v4.app.FragmentActivity
import io.github.flowboat.flowweather.ui.base.activity.ActivityMixin

/**
 * @author inorichi
 * @author nulldev
 */
interface FragmentMixin {

    fun setToolbarTitle(title: String) {
        (getActivity() as ActivityMixin).setToolbarTitle(title)
    }

    fun setToolbarTitle(resourceId: Int) {
        (getActivity() as ActivityMixin).setToolbarTitle(getString(resourceId))
    }

    fun getActivity(): FragmentActivity

    fun getString(resource: Int): String
}
