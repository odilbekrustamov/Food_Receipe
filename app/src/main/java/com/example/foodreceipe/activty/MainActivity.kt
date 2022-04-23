package com.example.foodreceipe.activty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.foodreceipe.R
import com.example.foodreceipe.adapter.ViewPagerAdapter
import com.example.foodreceipe.fragments.FavoriteFragment
import com.example.foodreceipe.fragments.HomeFragment
import com.example.foodreceipe.fragments.ProfileFragment
import com.example.foodreceipe.fragments.ShoppingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Contoins view pager with 4 fragments in MainActivity
 * and pages can be controlled by ButtomNavigationView
 */

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private var index = 0
    lateinit var viewPager: ViewPager
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewPager)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.navigation_home -> {
                    viewPager.setCurrentItem(0)
                }
                R.id.navigation_favorite -> {
                    viewPager.setCurrentItem(1)
                }
                R.id.navigation_shopping -> {
                    viewPager.setCurrentItem(2)
                }
                R.id.navigation_profile -> {
                    viewPager.setCurrentItem(3)
                }
            }
            true
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int,
            ) {

            }

            override fun onPageSelected(position: Int) {
                index = position
                bottomNavigationView.menu.getItem(index).setChecked(true)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })

        setupViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragmentList(HomeFragment())
        adapter.addFragmentList(FavoriteFragment())
        adapter.addFragmentList(ShoppingFragment())
        adapter.addFragmentList(ProfileFragment())
        viewPager.adapter = adapter
    }
}