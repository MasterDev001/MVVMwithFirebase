package com.example.mvvmwithfirebase.note

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.databinding.FragmentNoteListingBinding
import com.example.mvvmwithfirebase.util.UiState
import com.example.mvvmwithfirebase.util.hide
import com.example.mvvmwithfirebase.util.show
import com.example.mvvmwithfirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    private lateinit var binding: FragmentNoteListingBinding
    private val viewModel: NoteViewModel by viewModels()
    private val adapter by lazy {
        NoteListingAdapter(onItemCLicked = { pos, item ->

        }, onDeleteClicked = { pos, item ->

        }, onEditClicked = { pos, item ->

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteListingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener { findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment3) }// fragmentni almashtiradi
        binding.recyclerView.adapter = adapter
        viewModel.getNotes()
        viewModel.note.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.show()
                }
                is UiState.Failure -> {
                    binding.progressBar.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.progressBar.hide()
                    adapter.updateList(state.data.toMutableList())
                }
            }
        }
    }

}