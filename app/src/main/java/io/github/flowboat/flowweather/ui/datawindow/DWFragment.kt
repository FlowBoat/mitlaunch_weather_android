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
        val VOLATILE_DW = listOf<VolatileDW>(
                VolatileDW().apply {
                    day = ZonedDateTime.now()
                    precip = PrecipEstimation.CLOUDY
                    high = -3
                    low = -8
                },
                VolatileDW().apply {
                    day = ZonedDateTime.now()
                    precip = PrecipEstimation.DOWNPOUR
                    high = 0
                    low = -10
                },
                VolatileDW().apply {
                    day = ZonedDateTime.now()
                    precip = PrecipEstimation.DRIZZLE
                    high = 99
                    low = -99
                }
        )

        val adapter = FlexibleAdapter(VOLATILE_DW)
        data_recycler.adapter = adapter
    }
    
    companion object {
        fun newInstance() = DWFragment()
    }
}