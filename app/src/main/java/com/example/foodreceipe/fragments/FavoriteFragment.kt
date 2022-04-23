package com.example.foodreceipe.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.activty.CookingActivity
import com.example.foodreceipe.adapter.FoodListAdapter
import com.example.foodreceipe.model.FoodList

class FavoriteFragment : BaseFragment() {
    val TAG = FavoriteFragment::class.java.simpleName
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        refreshAdapter(allFoodList())
    }

    private fun refreshAdapter(items: ArrayList<FoodList>) {
        val adapter = FoodListAdapter(requireContext(), items)
        recyclerView.adapter = adapter

        adapter.imageClick = {
            val intent = Intent(requireContext(), CookingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun allFoodList(): ArrayList<FoodList> {
        val items = ArrayList<FoodList>()
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))
//        items.add(FoodList(R.drawable.iv_dessert))

        return items
    }
}