package com.coco_hkk.translation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.coco_hkk.translation.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 视图绑定
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Find the view pager that will allow the user to swipe between fragments
        val viewPager: ViewPager2 = binding.viewpager

        // Create an adapter that knows which fragment should be shown on each page
        val adapter = CategoryAdapter(this)

        // Set the adapter onto the view pager
        viewPager.adapter = adapter

        // Find the tab layout that shows the tabs
        val tabLayout = binding.tabs
        TabLayoutMediator(tabLayout, viewPager, true) { tab, position ->
            tab.text = adapter.getTabName(position)
        }.attach()
    }
}
