package com.example.foodreceipe.activty

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodreceipe.R
import com.example.foodreceipe.adapter.CookingStepAdapter
import com.example.foodreceipe.adapter.FoodListAdapter
import com.example.foodreceipe.manager.DatabaseManager
import com.example.foodreceipe.manager.handler.DBBreakfasrHandler
import com.example.foodreceipe.manager.handler.DBBreakfasrStepHandler
import com.example.foodreceipe.model.FoodList
import com.example.foodreceipe.model.Step

class CookingActivity : BaseActivty() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking)
        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        var foodlist: FoodList = intent.getSerializableExtra("foodlist") as FoodList

        val tv_food_name = findViewById<TextView>(R.id.tv_food_name)
        tv_food_name.text = foodlist.name

        val tv_ingredients = findViewById<TextView>(R.id.tv_ingredients)
        tv_ingredients.text = foodlist.ingredients

        val tv_made = findViewById<TextView>(R.id.tv_made)
        tv_made.text = foodlist.name + " qanday tayyorlanadi"


        val iv_photo = findViewById<ImageView>(R.id.iv_photo)
        Glide.with(this).load(foodlist.foodImg).into(iv_photo)

        step(foodlist)
    }

    private fun step(foodlist: FoodList) {
        showLoading(this)
        DatabaseManager.loadStep(foodlist.path, foodlist.id, object : DBBreakfasrStepHandler {
            override fun onSuccess(users: ArrayList<Step>) {
                dismissLoading()
                refreshAdapter(users)
            }
            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }

    private fun refreshAdapter(items: ArrayList<Step>) {
        val adapter = CookingStepAdapter(this, items)
        recyclerView.adapter = adapter
    }
}
