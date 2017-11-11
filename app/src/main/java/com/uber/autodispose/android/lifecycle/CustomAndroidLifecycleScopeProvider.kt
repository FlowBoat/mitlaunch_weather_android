/*
 * Copyright (c) 2017. Uber Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uber.autodispose.android.lifecycle

import android.annotation.SuppressLint
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import com.uber.autodispose.LifecycleEndedException
import com.uber.autodispose.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * A [LifecycleScopeProvider] that can provide scoping for Android [Lifecycle] and
 * [LifecycleOwner] classes.
 *
 *
 * <pre>`
 * AutoDispose.with(AndroidLifecycleScopeProvider.from(lifecycleOwner))
`</pre> *
 */
class CustomAndroidLifecycleScopeProvider : LifecycleScopeProvider<Lifecycle.Event> {

    private val correspondingEvents: Function<Lifecycle.Event, Lifecycle.Event>

    private val lifecycleObservable: LifecycleEventsObservable

    @SuppressLint("RestrictedApi")
    private constructor(lifecycle: Lifecycle) {
        this.lifecycleObservable = LifecycleEventsObservable(lifecycle)
        this.correspondingEvents = DEFAULT_CORRESPONDING_EVENTS
    }

    @SuppressLint("RestrictedApi")
    private constructor(lifecycle: Lifecycle, untilEvent: Lifecycle.Event) {
        this.lifecycleObservable = LifecycleEventsObservable(lifecycle)
        this.correspondingEvents = UntilEventFunction(untilEvent)
    }

    override fun lifecycle(): Observable<Lifecycle.Event> {
        return lifecycleObservable
    }

    override fun correspondingEvents(): Function<Lifecycle.Event, Lifecycle.Event> {
        return correspondingEvents
    }

    @SuppressLint("RestrictedApi")
    override fun peekLifecycle(): Lifecycle.Event? {
        return lifecycleObservable.value
    }

    private class UntilEventFunction internal constructor(private val untilEvent: Lifecycle.Event) : Function<Lifecycle.Event, Lifecycle.Event> {

        @Throws(Exception::class)
        override fun apply(event: Lifecycle.Event): Lifecycle.Event {
            return untilEvent
        }
    }

    companion object {

        private val DEFAULT_CORRESPONDING_EVENTS = Function<Lifecycle.Event, Lifecycle.Event> { lastEvent ->
            when (lastEvent) {
                Lifecycle.Event.ON_CREATE -> Lifecycle.Event.ON_DESTROY
                Lifecycle.Event.ON_START -> Lifecycle.Event.ON_STOP
                Lifecycle.Event.ON_RESUME -> Lifecycle.Event.ON_PAUSE
                Lifecycle.Event.ON_PAUSE -> Lifecycle.Event.ON_STOP
                Lifecycle.Event.ON_STOP, Lifecycle.Event.ON_DESTROY -> throw LifecycleEndedException()
                else -> throw LifecycleEndedException()
            }
        }

        /**
         * Creates a [AndroidLifecycleScopeProvider] for Android LifecycleOwners.
         *
         * @param owner the owner to scope for.
         * @return a [AndroidLifecycleScopeProvider] against this owner.
         */
        fun from(owner: LifecycleOwner): CustomAndroidLifecycleScopeProvider {
            return from(owner.lifecycle)
        }

        /**
         * Creates a [AndroidLifecycleScopeProvider] for Android LifecycleOwners.
         *
         * @param owner the owner to scope for.
         * @param untilEvent the event until the scope is valid.
         * @return a [AndroidLifecycleScopeProvider] against this owner.
         */
        fun from(
                owner: LifecycleOwner,
                untilEvent: Lifecycle.Event): CustomAndroidLifecycleScopeProvider {
            return from(owner.lifecycle, untilEvent)
        }

        /**
         * Creates a [AndroidLifecycleScopeProvider] for Android Lifecycles.
         *
         * @param lifecycle the lifecycle to scope for.
         * @return a [AndroidLifecycleScopeProvider] against this lifecycle.
         */
        fun from(lifecycle: Lifecycle): CustomAndroidLifecycleScopeProvider {
            return CustomAndroidLifecycleScopeProvider(lifecycle)
        }

        /**
         * Creates a [AndroidLifecycleScopeProvider] for Android Lifecycles.
         *
         * @param lifecycle the lifecycle to scope for.
         * @param untilEvent the event until the scope is valid.
         * @return a [AndroidLifecycleScopeProvider] against this lifecycle.
         */
        fun from(
                lifecycle: Lifecycle,
                untilEvent: Lifecycle.Event): CustomAndroidLifecycleScopeProvider {
            return CustomAndroidLifecycleScopeProvider(lifecycle, untilEvent)
        }
    }
}