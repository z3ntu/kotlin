/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.fir.symbols.impl.utils

/**
 * Lazily calculated value which runs postCompute in the same thread,
 * assuming that postCompute may try to read that value inside current thread,
 * So in the period then value is calculated but post compute was not finished,
 * only thread that initiated the calculating may see the value,
 * other threads will have to wait wait until that value is calculated
 */
internal class LazyValueWithPostCompute<V : Any>(calculate: () -> V, postCompute: (V) -> Unit) {
    private var _calculate: (() -> V)? = calculate
    private var _postCompute: ((V) -> Unit)? = postCompute

    @Volatile
    private var state: Any? = null

    @Suppress("UNCHECKED_CAST")
    fun getValue(): V {
        when (val stateSnapshot = state) {
            is CalculatingState -> {
                if (stateSnapshot.thread == Thread.currentThread()) {
                    return stateSnapshot.value as V
                } else {
                    synchronized(this) {
                        return state as V
                    }
                }
            }
            null -> synchronized(this) {
                // if we entered synchronized section that's mean that the value is not yet calculated and was not started to be calculated
                // or the some other thread calculated the value while we were waiting to acquire the lock

                if (state != null) { // some other thread calculated our value
                    return state as V
                }
                val value = _calculate!!()
                state = CalculatingState(value, Thread.currentThread()) // so current thread may see the value
                _postCompute!!(value)
                _calculate = null
                _postCompute = null
                state = value
                return value
            }
            else -> {
                return state as V
            }
        }
    }

    private class CalculatingState(val value: Any, val thread: Thread)
}