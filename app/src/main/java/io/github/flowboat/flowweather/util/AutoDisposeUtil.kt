package io.github.flowboat.flowweather.util

import android.arch.lifecycle.Lifecycle
import android.support.v4.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

fun Fragment.lifecycleScope()
        = AndroidLifecycleScopeProvider.from(this)!!

fun Fragment.untilDestroyed()
        = AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_DESTROY)!!

fun Fragment.untilStopped()
        = AndroidLifecycleScopeProvider.from(this, Lifecycle.Event.ON_STOP)!!
