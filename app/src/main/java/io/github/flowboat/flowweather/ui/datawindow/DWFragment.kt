package io.github.flowboat.flowweather.ui.datawindow

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.ui.base.fragment.BaseRxFragment
import kotlinx.android.synthetic.main.fragment_datawindow.*
import nucleus5.factory.RequiresPresenter
import org.threeten.bp.ZonedDateTime

@RequiresPresenter(DWPresenter::class)
class DWFragment : BaseRxFragment<DWPresenter>() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            = inflater.inflate(R.layout.fragment_datawindow, container, false)!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbarTitle("View data")

        //Optimize performance for fixed size recycler view
        data_recycler.setHasFixedSize(true)
        data_recycler.layoutManager = LinearLayoutManager(view.context)

        //TODO Use real data
        val precipList = PrecipEstimation.values().toList()
        val now = ZonedDateTime.now()
        val range = -20 .. 20
        val VOLATILE_DW = (0 .. 5000).map {
            VolatileDW().apply {
                day = now.plusDays(it.toLong())
                precip = precipList.shuffled().first()
                
                val tempRange = range.shuffled()
                high = tempRange[0]
                low = tempRange[1]
            }
        }

        val adapter = FlexibleAdapter(VOLATILE_DW)
        data_recycler.adapter = adapter
    }
    
    companion object {
        fun newInstance() = DWFragment()
    }
}