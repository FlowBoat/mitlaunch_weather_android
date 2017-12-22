package io.github.flowboat.flowweather.util

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import timber.log.Timber
import java.util.*

/**
 * ViewGroup extensions
 */

operator fun ViewGroup.plusAssign(other: View) = this.addView(other)

operator fun ViewGroup.plusAssign(other: Iterable<View>) = other.forEach { this += it }

operator fun ViewGroup.get(i: Int): View? = this.getChildAt(i)

// The below methods enable iterating over ViewGroups! //

fun ViewGroup.iterable() = object: Iterable<View> {
    override fun iterator(): Iterator<View> {
        return this@iterable.iterator()
    }
}

operator fun ViewGroup.iterator() = object : Iterator<View> {
    var index = 0

    override fun hasNext() = index < this@iterator.childCount

    override fun next(): View = this@iterator[index++] ?: throw NoSuchElementException((index - 1).toString())
}

/**
 * Get every child including nested ones.
 */
fun ViewGroup.getAllNestedChildren(): List<View> =
    this.iterable().flatMap {
        listOf(it) + ((it as? ViewGroup)?.getAllNestedChildren() ?: emptyList())
    }

/**
 * Extension method to inflate a view directly from its parent.
 * @param layout the layout to inflate.
 * @param attachToRoot whether to attach the view to the root or not. Defaults to false.
 */
fun ViewGroup.inflate(@LayoutRes layout: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layout, this, attachToRoot)
}