package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.view.View
import android.view.ViewGroup
import com.nightlynexus.viewstatepageradapter.ViewStatePagerAdapter
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.util.inflate
import io.reactivex.subjects.PublishSubject

class HorizAdapter(val jumpSubject: PublishSubject<Int>) : ViewStatePagerAdapter() {
    override fun createView(container: ViewGroup, position: Int): View {
        return when(position) {
            0 -> container.inflate(R.layout.item_horiz_next)
                    .also { (it as HorizNextView).onCreate(false, jumpSubject) }
            1 -> container.inflate(R.layout.item_horiz_main)
                    .also { (it as HorizMainView).onCreate() }
            2 -> container.inflate(R.layout.item_horiz_next)
                    .also { (it as HorizNextView).onCreate(true, jumpSubject) }
            else -> throw IllegalArgumentException("Invalid position!")
        }
    }

    override fun destroyView(container: ViewGroup?, position: Int, view: View?) {
        (view as? HorizNextView)?.onDestroy()
    }

    override fun getCount() = 3
}
