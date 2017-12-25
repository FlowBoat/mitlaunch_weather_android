package io.github.flowboat.flowweather.ui.datawindow.day

import io.github.flowboat.flowweather.ui.base.presenter.BasePresenter
import io.github.flowboat.flowweather.ui.datawindow.DayDataComponent

class DWDayPresenter : BasePresenter<DWDayActivity>() {
    private var dataCache: List<DayDataComponent>? = null
    
    fun getData(): List<DayDataComponent> {
        var data = dataCache
        
        if(data == null) {
            data = TEMP_DAY_DATA()
            dataCache = data
        }
        
        return data
    }
}