package com.android.core.base

abstract class BaseUseCase<T> {
    abstract suspend operator fun invoke(data: T): Any?
}