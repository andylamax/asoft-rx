package com.asofttz.rx

@Deprecated(message = "Observer is deprecated",replaceWith = ReplaceWith("Use com.asofttz.rx.Subscriber<T>"))
typealias Observer<T> = (oldValue: T, newValue: T) -> Unit

@Deprecated(message = "ListObserver is deprecated",replaceWith = ReplaceWith("Use com.asofttz.rx.Subscriber<List<T>>"))
typealias ListObserver<T> = (oldValue: MutableList<T>, newValue: MutableList<T>) -> Unit