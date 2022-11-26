package com.example.mvvmwithfirebase.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvmwithfirebase.ui.note.NoteListingFragment
import com.example.mvvmwithfirebase.ui.task.TaskListingFragment
import com.example.mvvmwithfirebase.util.HomeTabs

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = HomeTabs.values().size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HomeTabs.NOTES.index -> NoteListingFragment.newInstance(HomeTabs.NOTES.name)
            HomeTabs.TASKS.index -> TaskListingFragment.newInstance(HomeTabs.TASKS.name)
            else -> throw IllegalStateException("Fragment not found")
        }
    }
}