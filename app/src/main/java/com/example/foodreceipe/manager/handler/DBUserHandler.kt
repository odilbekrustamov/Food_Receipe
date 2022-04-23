package com.example.foodreceipe.manager.handler

import com.example.foodreceipe.model.User

interface DBUserHandler {
    fun onSuccess(user: User? = null)
    fun onError(e: Exception)
}