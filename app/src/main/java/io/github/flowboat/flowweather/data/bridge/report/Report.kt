package io.github.flowboat.flowweather.data.bridge.report

class Report {
    //Already diffed from station time
    var reportTime: Long? = null
    
    var protocolVersion: Int? = null
    
    var deviceId: Int? = null
    
    var errors = mutableListOf<StationError>()
    
    var entries = mutableListOf<ReportEntry>()
}