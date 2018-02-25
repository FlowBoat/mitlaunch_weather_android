package io.github.flowboat.flowweather.ui.alphabase.horiz

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.Gravity
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.alphabase.horiz.next.NextItem
import kotlinx.android.synthetic.main.item_horiz_next.view.*

class HorizNextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        ConstraintLayout(context, attrs) {
    fun onCreate(forward: Boolean) {
        next_pager.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }

        val testDetails = listOf(
                DetailType.PRECIPITATION to "30",
                DetailType.PRESSURE to "101.43",
                DetailType.HUMIDITY to "13.8",
                DetailType.AIR_QUALITY to "4",
                DetailType.WIND_SPEED to "20",
                DetailType.WIND_DIR to "NW",
                DetailType.SEASON to "Summer",
                DetailType.UV_INDEX to "1"
        ).map { DetailItem(it.first, it.second) }

        val fakeItems = (1 .. 50).map {
            NextItem(0, it, testDetails)
        }

        next_pager.setHasFixedSize(true)
        next_pager.adapter = FlexibleAdapter(fakeItems)

        val snap = GravitySnapHelper(Gravity.START)
        snap.attachToRecyclerView(next_pager)

        next_pager.onFlingListener = object : RecyclerView.OnFlingListener() {
            override fun onFling(velocityX: Int, velocityY: Int): Boolean {
                if (Math.abs(velocityX) > MAX_VELOCITY) {
                    val newVelo = MAX_VELOCITY * Math.signum(velocityX.toDouble()).toInt()
                    next_pager.fling(newVelo, velocityY)
                    return true
                }

                return false
            }
        }

        // Scroll to last if going backwards
        if(!forward)
            next_pager.scrollToPosition(next_pager.adapter.itemCount - 1)
    }

    companion object {
        private const val MAX_VELOCITY = 500
    }
}
