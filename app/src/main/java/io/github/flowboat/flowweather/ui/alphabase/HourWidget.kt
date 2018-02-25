package io.github.flowboat.flowweather.ui.alphabase

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View

class HourViewChild : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    private var rangeBegin: Double = 0.0
    private var rangeEnd: Double = 0.0

    private var begin: Double = 0.0
    private var end: Double = 0.0

    private var showEdge: Boolean = false

    private var beginHeight: Float = 0f
    private var endHeight: Float = 0f

    private val paint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 3f
    }

    private val edgePaint = Paint().apply {
        color = Color.WHITE
        strokeWidth = 2f
        alpha = 150
    }

    fun bindData(rangeBegin: Double,
                 rangeEnd: Double,
                 begin: Double,
                 end: Double,
                 showEdge: Boolean) {
        this.rangeBegin = rangeBegin
        this.rangeEnd = rangeEnd
        this.begin = begin
        this.end = end
        this.showEdge = showEdge

        calcHeights()
    }

    private fun calcHeights() {
        this.beginHeight = tempToHeight(begin)
        this.endHeight = tempToHeight(end)
    }

    private fun tempToHeight(temp: Double): Float {
        val range = Math.abs(rangeBegin - rangeEnd)
        val extra = temp - Math.min(rangeBegin, rangeEnd)
        if(range == 0.0) return 0f
        val percent = 1.0 - extra / range
        return GRAPH_PADDING + (percent * (height.toDouble() - 2 * GRAPH_PADDING)).toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        calcHeights()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(0.0f,
                beginHeight,
                width.toFloat(),
                endHeight,
                paint)

        if(showEdge)
            canvas.drawLine(width.toFloat(),
                    16f,
                    width.toFloat(),
                    height.toFloat(),
                    edgePaint)
    }

    companion object {
        private const val GRAPH_PADDING = 32f
    }
}