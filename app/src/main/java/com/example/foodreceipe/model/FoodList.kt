package com.example.foodreceipe.model

import java.io.Serializable

class FoodList: Serializable{
    var id: String = ""
    var foodImg: String = ""
    var ingredients: String = ""
    var name: String = ""
    var note: String = ""
    var path: String = ""

    constructor(id: String, foodImg: String, ingredients: String, name: String, note: String, path: String){
        this.id = id
        this.foodImg = foodImg
        this.ingredients = ingredients
        this.name = name
        this.note = note
        this.path = path
    }
}
