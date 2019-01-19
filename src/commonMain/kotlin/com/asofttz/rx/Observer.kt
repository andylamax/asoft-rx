package com.asofttz.rx

typealias Observer<T> = (oldValue: T, newValue: T) -> Unit

typealias ListObserver<T> = (oldValue: MutableList<T>, newValue: MutableList<T>) -> Unit