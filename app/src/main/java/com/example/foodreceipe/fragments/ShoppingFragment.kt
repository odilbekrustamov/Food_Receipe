package com.example.foodreceipe.fragments

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.activty.ChekActivity
import com.example.foodreceipe.adapter.ShoppingAdapter
import com.example.foodreceipe.database.ChekRepository
import com.example.foodreceipe.manager.DatabaseManager
import com.example.foodreceipe.manager.handler.DBPostsHandler
import com.example.foodreceipe.model.Chek
import com.example.foodreceipe.model.ShoppingList
import java.util.*
import java.util.concurrent.Executors
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ShoppingFragment : BaseFragment(){
    val TAG = ShoppingFragment::class.java.simpleName
    lateinit var tv_name: TextView
    lateinit var recyclerView: RecyclerView
    lateinit var ll_purchase_price: LinearLayout
    lateinit var adapter: ShoppingAdapter
    lateinit var tv_order: TextView
    var items = ArrayList<ShoppingList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_shopping, container, false)
        initViews(view)
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun initViews(view: View) {
        ll_purchase_price = view.findViewById(R.id.ll_purchase_price)
        tv_name = view.findViewById(R.id.tv_name)
        tv_name.text = "Shopping List"
        tv_order = view.findViewById(R.id.tv_order)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)

        loadUsers()
        refreshAdapter(items)

        //customerCosts(view)
    }

    private fun loadUsers(): ArrayList<ShoppingList> {
        showLoading(activity)
        DatabaseManager.loadUsers(object : DBPostsHandler {
            override fun onSuccess(users: ArrayList<ShoppingList>) {
               dismissLoading()
                items.clear()
                items.addAll(users)
                refreshAdapter(items)
            }

            override fun onError(e: Exception) {
                dismissLoading()
            }
        })
        return items
    }


    private fun refreshAdapter(items: ArrayList<ShoppingList>) {
        adapter = ShoppingAdapter(this, items)
        recyclerView.adapter = adapter
    }


    // Customer total cost window
    fun customerCosts(view: View, summa: Int, map1: HashMap<String, Int>, map2: HashMap<String, Int>) {
        val tv_sub_title = view.findViewById<TextView>(R.id.tv_sub_title)
        val tv_total = view.findViewById<TextView>(R.id.tv_total)
        val layout = view.findViewById<CoordinatorLayout>(R.id.layout)
        val tv_shipping_charges = view.findViewById<TextView>(R.id.tv_shipping_charges)
        tv_shipping_charges.text = "50000 so'm"
            if (summa > 0){
                ll_purchase_price.visibility = View.VISIBLE
                tv_sub_title.text = summa.toString()
                tv_total.text = (summa + 50000).toString()
                (layout.layoutParams as FrameLayout.LayoutParams).apply {
                    bottomMargin = convertDpToPixels(requireContext(), 112)
                }
                tv_order.setOnClickListener {
                    val intent = Intent(requireContext(), ChekActivity::class.java)
                    intent.putExtra("map1", map1)
                    intent.putExtra("map2", map2)
                    refreshAdapter(items)
                    ll_purchase_price.visibility = View.GONE
                    (layout.layoutParams as FrameLayout.LayoutParams).apply {
                        bottomMargin = convertDpToPixels(requireContext(), 0)
                    }
                    adapter.notifyDataSetChanged()
                    startActivity(intent)
                }
            }else {
                ll_purchase_price.visibility = View.GONE
                (layout.layoutParams as FrameLayout.LayoutParams).apply {
                    bottomMargin = convertDpToPixels(requireContext(), 0)
                }
            }
    }

        fun convertDpToPixels(context: Context, dp: Int) =
            (dp * context.resources.displayMetrics.density).toInt()
}