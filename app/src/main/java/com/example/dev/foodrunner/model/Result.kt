package com.example.dev.foodrunner.model

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception):Result<Any>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> "data $data"
            is Error->"Exception $exception"
        }
    }
}