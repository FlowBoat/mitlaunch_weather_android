package io.github.flowboat.flowweather.ui.alphabase.loc

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.github.salomonbrys.kodein.instance
import io.github.flowboat.flowweather.data.preference.PreferencesHelper
import io.github.flowboat.flowweather.ui.unipager.UPItem
import org.jetbrains.anko.bottomPadding
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.textView
import org.jetbrains.anko.topPadding

class LocationItem(val location: String, val id: Int, private val activity: AppCompatActivity) : UPItem() {
    override fun getView(parent: ViewGroup): View {
        return FrameLayout(parent.context).apply {
            textView {
                text = location
                topPadding = dip(4)
                bottomPadding = dip(4)

                onClick {
                    activity.finish()
                    Kodein.global.instance<PreferencesHelper>().locationCurrent.set(id)
                }
            }
        }
    }
}