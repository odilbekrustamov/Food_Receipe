package com.example.foodreceipe.manager.handler

import com.example.foodreceipe.model.ShoppingList

interface DBPostsHandler {
    fun onSuccess(users: ArrayList<ShoppingList>)
    fun onError(e: Exception)
}