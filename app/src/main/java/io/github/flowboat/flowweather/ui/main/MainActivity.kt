package io.github.flowboat.flowweather.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.data.preference.PreferencesHelper
import io.github.flowboat.flowweather.ui.adebug.ADebugFragment
import io.github.flowboat.flowweather.ui.base.activity.BaseActivity
import io.github.flowboat.flowweather.ui.bridgeui.BridgeUIFragment
import io.github.flowboat.flowweather.ui.datawindow.DWFragment
import io.github.flowboat.flowweather.ui.home.HomeFragment
import io.github.flowboat.flowweather.ui.setting.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Main activity
 *
 * @author inorichi
 * @author nulldev
 */
class MainActivity : BaseActivity() {

    val preferences: PreferencesHelper by Kodein.global.lazy.instance()

    val startScreenId = R.id.nav_drawer_home //TODO Allow customization of this value

    override fun onCreate(savedState: Bundle?) {
        setAppTheme()
        super.onCreate(savedState)

        // Do not let the launcher create a new activity
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

        setContentView(R.layout.activity_main)

        // Handle Toolbar
        setupToolbar(toolbar, backNavigation = false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        // Set behavior of Navigation drawer
        nav_view.setNavigationItemSelectedListener { item ->
            // Make information view invisible
            empty_view.hide()

            val id = item.itemId
            //TODO Implement
            when (id) {
                R.id.nav_drawer_home -> setFragment(HomeFragment.newInstance(), id)
                R.id.nav_drawer_adebug -> setFragment(ADebugFragment.newInstance(), id)
                R.id.nav_drawer_bridgeui -> setFragment(BridgeUIFragment.newInstance(), id)
                R.id.nav_drawer_datawindow -> setFragment(DWFragment.newInstance(), id)
                R.id.nav_drawer_settings -> {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivityForResult(intent, REQUEST_OPEN_SETTINGS)
                }
            }
            drawer.closeDrawer(GravityCompat.START)
            true
        }

        if (savedState == null) {
            // Set start screen
            setSelectedDrawerItem(startScreenId)

            //Put anything else you want to do on app start here
            //It will not be executed when the activity is recreated at any other time
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> drawer.openDrawer(GravityCompat.START)
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame_container)
        if (fragment != null && fragment.tag?.toInt() != startScreenId) {
            if (resumed) {
                setSelectedDrawerItem(startScreenId)
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_OPEN_SETTINGS && resultCode != 0) {
            if (resultCode and SettingsActivity.FLAG_THEME_CHANGED != 0) {
                // Delay activity recreation to avoid fragment leaks.
                nav_view.post { recreate() }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun setSelectedDrawerItem(itemId: Int, triggerAction: Boolean = true) {
        nav_view.setCheckedItem(itemId)
        if (triggerAction) {
            nav_view.menu.performIdentifierAction(itemId, 0)
        }
    }

    private fun setFragment(fragment: Fragment, itemId: Int) {
        val tag = "$itemId"
        if(supportFragmentManager.findFragmentByTag(tag) == null) {
            clearAppBarFragments()
            supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment, "$itemId")
                    .commit()
        }
    }

    fun clearAppBarFragments() {
        supportFragmentManager.findFragmentById(R.id.appbar_layout)?.let {
            supportFragmentManager.beginTransaction()
                    .remove(it)
                    .commit()
        }
    }

    fun setAppBarFragment(fragment: Fragment, itemId: Int)
        = setAppBarFragment(fragment, "$itemId")

    fun setAppBarFragment(fragment: Fragment, itemId: String) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.appbar_layout, fragment, "${itemId}_appbar_header")
                .commit()
    }

    fun updateEmptyView(show: Boolean, textResource: Int, drawable: Int) {
        if (show) empty_view.show(drawable, textResource) else empty_view.hide()
    }

    companion object {
        private const val REQUEST_OPEN_SETTINGS = 200
    }
}
