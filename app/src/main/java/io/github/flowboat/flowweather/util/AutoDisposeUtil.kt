package io.github.flowboat.flowweather.util

import android.support.v4.app.Fragment
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

fun Fragment.lifecycleScope()
        = AndroidLifecycleScopeProvider.from(this)
