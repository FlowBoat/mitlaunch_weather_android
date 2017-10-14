package io.github.flowboat.flowweather.api.http

sealed class ApiResponse {
    class Success()
    class Failure(error: String)
}