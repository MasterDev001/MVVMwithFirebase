package com.example.mvvmwithfirebase.ui.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.databinding.FragmentNoteListingBinding
import com.example.mvvmwithfirebase.ui.auth.AuthViewModel
import com.example.mvvmwithfirebase.util.UiState
import com.example.mvvmwithfirebase.util.hide
import com.example.mvvmwithfirebase.util.show
import com.example.mvvmwithfirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM1 = "param1"

@AndroidEntryPoint
class NoteListingFragment : Fragment() {

    private lateinit var binding: FragmentNoteListingBinding
    private val viewModel: NoteViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private val adapter by lazy {
        NoteListingAdapter(
            onItemCLicked = { pos, item ->
                findNavController().navigate(
                    R.id.action_noteListingFragment_to_noteDetailFragment3,
                    Bundle().apply {
                        putString("type", "view")
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

        observer()
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = null
        binding.recyclerView.layoutManager = staggeredGridLayoutManager
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_noteListingFragment_to_noteDetailFragment3,
                Bundle().apply { putString("type", "create") })// fragmentni almashtiradi
        }

        authViewModel.getSession {
            viewModel.getNotes(it)
        }
    }

    private fun observer() {
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

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            NoteListingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}