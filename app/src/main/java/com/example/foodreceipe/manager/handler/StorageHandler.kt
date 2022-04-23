package com.example.foodreceipe.manager.handler

interface StorageHandler {
    fun onSuccess(userImg: String)
    fun onError(e: Exception)
}