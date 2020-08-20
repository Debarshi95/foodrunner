package com.example.dev.foodrunner.model.pojo

data class BaseResponse<out T>(val data: Data<T>)