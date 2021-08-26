package com.coco_hkk.translation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_PAGES = 4

class CategoryAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private  val context: Context = fragment

    private val translationTabs = listOf(
        R.string.category_numbers,
        R.string.category_family,
        R.string.category_colors,
        R.string.category_phrases
    )

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NumbersFragment.newInstance()
            1 -> FamilyFragment.newInstance()
            2 -> ColorsFragment.newInstance()
            else -> PhrasesFragment.newInstance()
        }
    }

    fun getTabName(position: Int): String {
        return when(position) {
            0,1,2,3 -> context.getString(translationTabs[position]).split(" ")[0]
            else -> "null"
        }
    }
}