package io.github.flowboat.flowweather.data.bridge.report

class ReportEntry {
    //Already diffed from station time
    var collectionTime: Long? = null
    
    var temp: Float? = null
    
    var pressure: Float? = null
    
    var humidity: Float? = null
    
    var windSpeed: Float? = null
}