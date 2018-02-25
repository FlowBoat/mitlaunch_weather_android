package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.view.View
import android.view.ViewGroup
import com.nightlynexus.viewstatepageradapter.ViewStatePagerAdapter
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.util.inflate

class HorizAdapter : ViewStatePagerAdapter() {
    override fun createView(container: ViewGroup, position: Int): View {
        return when(position) {
            0 -> container.inflate(R.layout.item_horiz_next)
                    .also { (it as HorizNextView).onCreate(false) }
            1 -> container.inflate(R.layout.item_horiz_main)
                    .also { (it as HorizMainView).onCreate() }
            2 -> container.inflate(R.layout.item_horiz_next)
                    .also { (it as HorizNextView).onCreate(true) }
            else -> throw IllegalArgumentException("Invalid position!")
        }
    }

    override fun getCount() = 3
}
