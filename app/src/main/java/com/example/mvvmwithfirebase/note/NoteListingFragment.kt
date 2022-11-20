package com.example.mvvmwithfirebase.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.databinding.FragmentNoteListingBinding
import com.example.mvvmwithfirebase.util.UiState
import com.example.mvvmwithfirebase.util.hide
import com.example.mvvmwithfirebase.util.show
import com.example.mvvmwithfirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    private lateinit var binding: FragmentNoteListingBinding
    private val viewModel: NoteViewModel by viewModels()
    private var deletePosition: Int = -1
    private var list: MutableList<Note> = arrayListOf()

    private val adapter by lazy {
        NoteListingAdapter(
            onItemCLicked = { pos, item ->
                findNavController().navigate(
                    R.id.action_noteListingFragment_to_noteDetailFragment3,
                    Bundle().apply {
                        putString("type", "view")
                        putParcelable("note", item)
                    })
            }, onDeleteClicked = { pos, item ->
                deletePosition = pos
                viewModel.deleteNote(item)
            }, onEditClicked = { pos, item ->
                findNavController().navigate(
                    R.id.action_noteListingFragment_to_noteDetailFragment3,
                    Bundle().apply {
                        putString("type", "edit")
                        putParcelable("note", item)
                    })
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

        binding.recyclerView.adapter = adapter
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment3,
                Bundle().apply { putString("type", "create") })// fragmentni almashtiradi
        }
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
                    list = state.data.toMutableList()
                    adapter.updateList(list)
                }
            }
        }

        viewModel.deleteNote.observe(viewLifecycleOwner) { state ->
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
                    toast(state.data)
                    if (deletePosition != -1) {
                        list.removeAt(deletePosition)
                        adapter.updateList(list)
                    }
                }
            }
        }
    }
}