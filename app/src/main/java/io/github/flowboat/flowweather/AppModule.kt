package io.github.flowboat.flowweather

import android.app.Application
import com.fatboyindustrial.gsonjavatime.Converters
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import io.github.flowboat.flowweather.api.ApiManager
import io.github.flowboat.flowweather.api.hurricane.Hurricane
import io.github.flowboat.flowweather.data.preference.PreferencesHelper
import io.github.flowboat.flowweather.data.quiz.QuizManager

/**
 * Dependency injection config
 *
 * @author nulldev
 */

class AppModule {
    companion object {
        fun create(app: Application) = Kodein.Module {
            bind<PreferencesHelper>() with singleton { PreferencesHelper(app) }

            bind<ApiManager>() with singleton { ApiManager() }

            bind<QuizManager>() with singleton { QuizManager(app) }

            bind<JsonParser>() with singleton { JsonParser() }

            bind<Gson>() with singleton { Converters.registerAll(GsonBuilder()).create() }

            bind<Hurricane>() with singleton { Hurricane(app) }
        }
    }
}
