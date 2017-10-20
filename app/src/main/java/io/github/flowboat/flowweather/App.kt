package io.github.flowboat.flowweather

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.conf.global
import com.jakewharton.threetenabp.AndroidThreeTen
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule
import timber.log.Timber

/**
 * Custom Android application
 *
 * @author nulldev
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        //Android time support
        AndroidThreeTen.init(this);

        //Setup logging
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        //Setup DI
        Kodein.global.addImport(AppModule.create(this))

        //Setup Iconify
        Iconify.with(FontAwesomeModule())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        //Install MultiDex
        MultiDex.install(this)
    }
}
