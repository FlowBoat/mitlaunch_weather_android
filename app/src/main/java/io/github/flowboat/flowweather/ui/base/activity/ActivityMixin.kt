package io.github.flowboat.flowweather.ui.base.activity

import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import io.github.flowboat.flowweather.R

/**
 * @author inorichi
 * @author nulldev
 */
interface ActivityMixin {

    fun setupToolbar(toolbar: Toolbar, backNavigation: Boolean = true) {
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        if (backNavigation) {
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }

    fun setAppTheme() {
        //TODO Theme switcher
        setTheme(R.style.Theme_FlowWeather)
    }

    fun setToolbarTitle(title: String) {
        getSupportActionBar()?.title = title
    }

    fun setToolbarTitle(titleResource: Int) {
        getSupportActionBar()?.title = getString(titleResource)
    }

    fun setToolbarSubtitle(title: String) {
        getSupportActionBar()?.subtitle = title
    }

    fun setToolbarSubtitle(titleResource: Int) {
        getSupportActionBar()?.subtitle = getString(titleResource)
    }

    fun getActivity(): AppCompatActivity

    fun onBackPressed()

    fun getSupportActionBar(): ActionBar?

    fun setSupportActionBar(toolbar: Toolbar?)

    fun setTheme(resource: Int)

    fun getString(resource: Int): String

}
