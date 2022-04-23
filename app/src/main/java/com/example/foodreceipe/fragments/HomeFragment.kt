package com.example.foodreceipe.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.activty.CookingActivity
import com.example.foodreceipe.adapter.FoodListAdapter
import com.example.foodreceipe.manager.DatabaseManager
import com.example.foodreceipe.manager.handler.DBBreakfasrHandler
import com.example.foodreceipe.model.FoodList

class HomeFragment : BaseFragment() {
    val TAG = HomeFragment::class.java.simpleName
    private lateinit var recyclerView: RecyclerView
    lateinit var tv_breakfast: TextView
    lateinit var tv_main_course: TextView
    lateinit var tv_dessert: TextView
    lateinit var tv_salad: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("ResourceAsColor")
    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        tv_breakfast = view.findViewById(R.id.tv_breakfast)
        tv_main_course = view.findViewById(R.id.tv_main_course)
        tv_dessert = view.findViewById(R.id.tv_dessert)
        tv_salad = view.findViewById(R.id.tv_salad)

        tv_breakfast.setTextColor(Color.parseColor("#27910B"));
        tv_main_course.setTextColor(Color.parseColor("#000000"));
        tv_dessert.setTextColor(Color.parseColor("#000000"));
        tv_salad.setTextColor(Color.parseColor("#000000"));
        allFoodList("breakfast")
        val iv_breakfast = view.findViewById<ImageView>(R.id.iv_breakfast)
        iv_breakfast.setOnClickListener {
            tv_breakfast.setTextColor(Color.parseColor("#27910B"));
            tv_main_course.setTextColor(Color.parseColor("#000000"));
            tv_dessert.setTextColor(Color.parseColor("#000000"));
            tv_salad.setTextColor(Color.parseColor("#000000"));
            allFoodList("breakfast")
        }
        val iv_main_course = view.findViewById<ImageView>(R.id.iv_main_course)
        iv_main_course.setOnClickListener {
            tv_breakfast.setTextColor(Color.parseColor("#000000"));
            tv_main_course.setTextColor(Color.parseColor("#27910B"))
            tv_dessert.setTextColor(Color.parseColor("#000000"));
            tv_salad.setTextColor(Color.parseColor("#000000"));
            allFoodList("main_course")
        }
        val iv_main_dessert = view.findViewById<ImageView>(R.id.iv_dessert)
        iv_main_dessert.setOnClickListener {
            tv_breakfast.setTextColor(Color.parseColor("#000000"));
            tv_main_course.setTextColor(Color.parseColor("#000000"));
            tv_dessert.setTextColor(Color.parseColor("#27910B"))
            tv_salad.setTextColor(Color.parseColor("#000000"));
            allFoodList("dessert")
        }
        val iv_main_salad = view.findViewById<ImageView>(R.id.iv_salad)
        iv_main_salad.setOnClickListener {
            tv_breakfast.setTextColor(Color.parseColor("#000000"));
            tv_main_course.setTextColor(Color.parseColor("#000000"));
            tv_dessert.setTextColor(Color.parseColor("#000000"));
            tv_salad.setTextColor(Color.parseColor("#27910B"))
            allFoodList("salad")}
    }

    private fun refreshAdapter(items: ArrayList<FoodList>) {
        val adapter = FoodListAdapter(requireContext(), items)
        recyclerView.adapter = adapter

        adapter.imageClick = {
            val intent = Intent(requireContext(), CookingActivity::class.java)
            intent.putExtra("foodlist", it)
            startActivity(intent)
        }
    }

    private fun allFoodList(path: String){
        showLoading(activity)
        DatabaseManager.loadBreakfast(path, object : DBBreakfasrHandler{
            override fun onSuccess(users: ArrayList<FoodList>) {
                dismissLoading()
                refreshAdapter(users)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
    }
}