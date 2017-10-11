package io.github.flowboat.flowweather.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import io.github.flowboat.flowweather.R
import io.github.flowboat.flowweather.util.getResourceColor
import io.github.flowboat.flowweather.util.layoutInflater
import io.github.flowboat.flowweather.util.setVectorCompat
import kotlinx.android.synthetic.main.view_empty.view.*

/**
 * View used for showing empty screens (literally)
 *
 * @author inorichi
 * @author nulldev
 */
class EmptyView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
        RelativeLayout (context, attrs) {

    init {
        context.layoutInflater.inflate(R.layout.view_empty, this, true)
    }

    /*
     * Hide the information view
     */
    fun hide() {
        this.visibility = View.GONE
    }

    /**
     * Show the information view
     * @param drawable icon of information view
     * @param textResource text of information view
     */
    fun show(drawable: Int, textResource: Int) {
        image_view.setVectorCompat(drawable, context.theme.getResourceColor(android.R.attr.textColorHint))
        text_label.text = context.getString(textResource)
        this.visibility = View.VISIBLE
    }
}
