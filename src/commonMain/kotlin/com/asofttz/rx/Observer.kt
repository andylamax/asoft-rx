package com.asofttz.rx

typealias Observer<T> = (oldValue: T, newValue: T) -> Unit

@Deprecated("use subscriber instead")
typealias ListObserver<T> = (oldValue: MutableList<T>, newValue: MutableList<T>) -> Unit