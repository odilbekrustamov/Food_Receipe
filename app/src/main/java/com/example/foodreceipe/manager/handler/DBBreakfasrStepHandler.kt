package com.example.foodreceipe.manager.handler

import com.example.foodreceipe.model.FoodList
import com.example.foodreceipe.model.ShoppingList
import com.example.foodreceipe.model.Step

interface DBBreakfasrStepHandler {
    fun onSuccess(users: ArrayList<Step>)
    fun onError(e: Exception)
}