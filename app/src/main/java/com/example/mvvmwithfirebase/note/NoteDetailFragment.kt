package com.example.mvvmwithfirebase.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.databinding.FragmentNoteDetailBinding
import com.example.mvvmwithfirebase.databinding.FragmentNoteListingBinding

class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }


}