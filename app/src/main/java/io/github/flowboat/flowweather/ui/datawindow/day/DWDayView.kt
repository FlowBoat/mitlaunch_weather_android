package io.github.flowboat.flowweather.ui.datawindow.day

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent
import kotlinx.android.synthetic.main.item_dw_day.view.*

class DWDayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        ConstraintLayout(context, attrs) {
    var data: DayDataComponent? = null

    fun onCreate() {
        //Setup graph type spinner
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, ChartType.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                data?.let {
                    //Assemble entries
                    val entries = it.data.toList().mapIndexed { index, pair ->
                        Entry(index.toFloat(), pair.second.toFloat())
                    }

                    val newChart = ChartType.values()[pos].chartBuilder(context, it, entries)
                    chart.removeAllViews()
                    chart.addView(newChart)
                }
            }
        }
    }

    fun onBind(data: DayDataComponent) {
        this.data = data
    }
}

enum class ChartType(val chartBuilder: (Context, DayDataComponent, List<Entry>) -> Chart<*>) {
    LINE({ context, data, entries ->
        val lineChart = LineChart(context)
        val dataSet = LineDataSet(entries, data.name)
        val lData = LineData(dataSet)
        lineChart.data = lData
        lineChart
    })
}
