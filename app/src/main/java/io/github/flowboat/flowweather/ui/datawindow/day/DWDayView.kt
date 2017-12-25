package io.github.flowboat.flowweather.ui.datawindow.day

import android.content.Context
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import eu.davidea.flexibleadapter.FlexibleAdapter
import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent
import kotlinx.android.synthetic.main.item_dw_day.view.*
import org.threeten.bp.format.DateTimeFormatter

class DWDayView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        ConstraintLayout(context, attrs) {
    var data: DayDataComponent? = null
    lateinit var rAdapter: FlexibleAdapter<DWDayEntry>

    fun onCreate() {
        //Setup graph type spinner
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, ChartType.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                updateChart(pos)
            }
        }
        
        rAdapter = FlexibleAdapter(emptyList())
        numbers.setHasFixedSize(true)
        numbers.layoutManager = LinearLayoutManager(context)
        numbers.adapter = rAdapter
    }
    
    fun updateChart(pos: Int) {
        data?.let {
            //Assemble entries
            val entries = it.data.toList().mapIndexed { index, pair ->
                Entry(index.toFloat(), pair.second.toFloat())
            }
        
            val newChart = ChartType.values()[pos].chartBuilder(context, it, entries)
            chart.removeAllViews()
            chart.addView(newChart)
        
            //Animate chart
            newChart.animateXY(750, 750)
        
            //Setup axis
            newChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            newChart.xAxis.valueFormatter = IAxisValueFormatter { value, _ ->
                HOUR_FORMAT.format(it.data.keys.toList()[value.toInt()])
            }
            newChart.description = Description().apply { text = "" }
            (newChart as? BarLineChartBase<*>)?.let { chart ->
                chart.axisRight.isEnabled = false
    
                chart.axisLeft.axisMinimum = it.range.first.toFloat()
                chart.axisLeft.axisMaximum = it.range.last.toFloat()
            }
    
            //Charts are static
            (newChart as? BarLineChartBase<*>)?.let {
                it.isDragEnabled = false
                it.setScaleEnabled(false)
                it.setPinchZoom(false)
                it.isDoubleTapToZoomEnabled = false
            }
        }
    }

    fun onBind(data: DayDataComponent) {
        this.data = data
        
        updateChart(spinner.selectedItemPosition)
        
        //Update plain adapter
        rAdapter.clear()
        rAdapter.addItems(0, data.data.map { DWDayEntry(it.key, it.value) })
    }
    
    companion object {
        val HOUR_FORMAT = DateTimeFormatter.ofPattern("k:mm")
    }
}

enum class ChartType(val chartBuilder: (Context, DayDataComponent, List<Entry>) -> Chart<*>) {
    LINE({ context, data, entries ->
        val lineChart = LineChart(context)
        val dataSet = LineDataSet(entries, data.name)
        dataSet.color = COLOR
        dataSet.setCircleColor(COLOR)
        val lData = LineData(dataSet)
        lineChart.data = lData
        lineChart
    }),
    BAR({ context, data, entries ->
        val barChart = BarChart(context)
        val dataSet = BarDataSet(entries.map {
            BarEntry(it.x, it.y)
        }, data.name)
        dataSet.color = COLOR
        val bData = BarData(dataSet)
        barChart.data = bData
        barChart
    });
    
    companion object {
        private val COLOR = Color.BLUE
    }
}
