package com.example.foodreceipe.manager

import android.util.Log
import com.example.foodreceipe.manager.handler.DBBreakfasrHandler
import com.example.foodreceipe.manager.handler.DBBreakfasrStepHandler
import com.example.foodreceipe.manager.handler.DBPostsHandler
import com.example.foodreceipe.manager.handler.DBUserHandler
import com.example.foodreceipe.model.FoodList
import com.example.foodreceipe.model.ShoppingList
import com.example.foodreceipe.model.Step
import com.example.foodreceipe.model.User
import com.google.firebase.firestore.FirebaseFirestore

private var LIST_PATH = "list"
private var USER_PATH = "users"
private var FOOD_PATH = "food"
private var STEP_PATH = "steps"


object DatabaseManager {
    private var database = FirebaseFirestore.getInstance()

    fun storeUser(user: User, handler: DBUserHandler){
        database.collection(USER_PATH).document(user.uid).set(user).addOnCompleteListener {
            handler.onSuccess()
        }.addOnFailureListener {
            handler.onError(it)
        }
    }

    fun loadUser(uid: String, handler: DBUserHandler){
        database.collection(USER_PATH).document(uid).get().addOnSuccessListener {
            if (it.exists()){
                val fullname: String? = it.getString("fullname")
                val email: String? = it.getString("email")
                val userImg: String? = it.getString("userImg")

                val user = User(fullname!!, email!!, userImg!!)
                user.uid = uid
                handler.onSuccess(user)
            }else {
                handler.onSuccess(null)
            }
        }.addOnFailureListener {
            handler.onError(it)
        }
    }


    fun updateUserImage(userImg: String){
        val uid = AuthManager.currentUser()!!.uid
        database.collection(USER_PATH).document(uid).update("userImg", userImg)
    }

    fun loadUsers(handler: DBPostsHandler){
        database.collection(LIST_PATH).get().addOnCompleteListener {
            val items = ArrayList<ShoppingList>()
            if (it.isSuccessful) {
                Log.d("less",it.result.size().toString())
                for (document in it.result) {
                    Log.d("dsdd",document.getString("postImg").toString())
                   items.add(ShoppingList(document.getString("name").toString(), document.getString("price").toString(), postImg = document.getString("postImg").toString())
                   )
                }
                handler.onSuccess(items)
            }else{
                handler.onError(it.exception!!)
            }
        }
    }

    fun loadBreakfast(path: String, handler: DBBreakfasrHandler){
        database.collection(FOOD_PATH).document(path).collection(FOOD_PATH).get().addOnCompleteListener {
            val items = ArrayList<FoodList>()
            if (it.isSuccessful){
                for (document in it.result){
                    val id = document.getString("id")
                    val foodImg = document.getString("foodImg")
                    val ingredients = document.getString("ingredients")
                    val name = document.getString("name")
                    val note = document.getString("note")
                    val foodList = FoodList(id!!, foodImg!!, ingredients!!, name!!, note!!, path)
                    items.add(foodList)
                }
                handler.onSuccess(items)
            }else {
                handler.onError(it.exception!!)
            }
        }
    }

    fun loadStep(path: String, id: String, handler: DBBreakfasrStepHandler){
        val reference = database.collection(FOOD_PATH).document(path).collection(FOOD_PATH).document(id.trim()).collection(STEP_PATH).orderBy("id")
            reference.get().addOnCompleteListener {
                val items = ArrayList<Step>()
                if (it.isSuccessful){
                    for (document in it.result){
                        val step = document.getString("step")
                        val item = Step(step!!)
                        Log.d("@@@##", step)
                        items.add(item)
                    }
                    handler.onSuccess(items)
                }else {
                    handler.onError(it.exception!!)
                }
            }
    }
}