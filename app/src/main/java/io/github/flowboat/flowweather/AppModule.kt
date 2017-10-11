package io.github.flowboat.flowweather

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.singleton
import io.github.flowboat.flowweather.data.preference.PreferencesHelper

/**
 * Dependency injection config
 *
 * @author nulldev
 */

class AppModule {
    companion object {
        fun create(app: Application) = Kodein.Module {
            bind<PreferencesHelper>() with singleton { PreferencesHelper(app) }
        }
    }
}
