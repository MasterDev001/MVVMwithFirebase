package com.example.mvvmwithfirebase.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.data.model.Note
import com.example.mvvmwithfirebase.databinding.FragmentNoteDetailBinding
import com.example.mvvmwithfirebase.databinding.FragmentNoteListingBinding
import com.example.mvvmwithfirebase.util.UiState
import com.example.mvvmwithfirebase.util.hide
import com.example.mvvmwithfirebase.util.show
import com.example.mvvmwithfirebase.util.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding
    private val viewModel: NoteViewModel by viewModels()
    private var isEdit = false
    private var objNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        binding.button.setOnClickListener {
            if (isEdit) {
                updateNote()
            } else {
                creteNote()
            }
        }
    }


    private fun creteNote() {
        if (validation()) {
            viewModel.addNote(
                Note(
                    id = "",
                    text = binding.noteMsg.text.toString(),
                    date = Date()
                )
            )
        }

        viewModel.addNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnProgressAr.show()
                    binding.button.text = ""
                }
                is UiState.Failure -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Create"
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Create"
                    toast(state.data)
                }
            }
        }
    }


    private fun updateNote() {
        if (validation()) {
            viewModel.updateNote(
                Note(
                    id = objNote?.id ?: "",
                    text = binding.noteMsg.text.toString(),
                    date = Date()
                )
            )
        }

        viewModel.updateNote.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.btnProgressAr.show()
                    binding.button.text = ""
                }
                is UiState.Failure -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Update"
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.btnProgressAr.hide()
                    binding.button.text = "Update"
                    toast(state.data)
                }
            }
        }
    }


    private fun updateUI() {
        val type = arguments?.getString("type", null)
        type.let {
            when (it) {
                "view" -> {
                    binding.noteMsg.isEnabled = false
                    binding.button.hide()
                    isEdit = false
                    objNote = arguments?.getParcelable("note")
                    binding.noteMsg.setText(objNote?.text)
                }
                "create" -> {
                    isEdit = false
                    binding.button.text = "Create"
                }
                "edit" -> {
                    isEdit = true
                    objNote = arguments?.getParcelable("note")
                    binding.noteMsg.setText(objNote?.text)
                    binding.button.text = "Update"
                }
            }
        }
    }


    private fun validation(): Boolean {
        return if (binding.noteMsg.text.toString().isEmpty()) {
            toast("Enter Message")
            false
        } else {
            true
        }
    }
}