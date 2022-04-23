package com.example.foodreceipe.manager.handler

import com.example.foodreceipe.model.FoodList
import com.example.foodreceipe.model.ShoppingList

interface DBBreakfasrHandler {
    fun onSuccess(users: ArrayList<FoodList>)
    fun onError(e: Exception)
}