package io.github.flowboat.flowweather.util

import android.arch.lifecycle.Lifecycle
import android.support.v4.app.Fragment
import com.uber.autodispose.android.lifecycle.CustomAndroidLifecycleScopeProvider

fun Fragment.lifecycleScope()
        = CustomAndroidLifecycleScopeProvider.from(this)

fun Fragment.untilDestroyed()
        = CustomAndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)

fun Fragment.untilStopped()
        = CustomAndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)
