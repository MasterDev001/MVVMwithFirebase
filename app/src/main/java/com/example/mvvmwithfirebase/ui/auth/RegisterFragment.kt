package com.example.mvvmwithfirebase.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mvvmwithfirebase.R
import com.example.mvvmwithfirebase.data.model.User
import com.example.mvvmwithfirebase.databinding.FragmentRegisterBinding
import com.example.mvvmwithfirebase.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint                                                    ///// / / / /
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        binding.registerBtn.setOnClickListener {
            if (validation()) {
                viewModel.register(
                    email = binding.emailEt.text.toString(),
                    password = binding.passEt.text.toString(),
                    user = getUserObj()
                )
            }
        }
    }

    private fun observer() {
        viewModel.register.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.registerBtn.text = ""
                    binding.registerProgress.show()
                }
                is UiState.Failure -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    toast(state.error)
                }
                is UiState.Success -> {
                    binding.registerBtn.text = "Register"
                    binding.registerProgress.hide()
                    toast(state.data)
                    findNavController().navigate(R.id.action_registerFragment2_to_noteListingFragment)
                    // agar birinchi kirganida register holati success bo'lasa notelistfragmentga jo'natadi
                }
            }
        }
    }


    private fun getUserObj(): User {
        return User(
            id = "",
            first_name = binding.firstNameEt.text.toString(),
            last_name = binding.lastNameEt.text.toString(),
            job_title = binding.jobTitleEt.text.toString(),
            email = binding.emailEt.text.toString()
        )
    }

    private fun validation(): Boolean {
        var isValid = true
        if (binding.firstNameEt.text.isEmpty()) {
            isValid = false
            toast(getString(R.string.enter_first_name))
        }
        if (binding.lastNameEt.text.isEmpty()) {
            isValid = false
            toast(getString(R.string.enter_last_name))
        }
        if (binding.jobTitleEt.text.isEmpty()) {
            isValid = false
            toast(getString(R.string.enter_job_title))
        }
        if (binding.emailEt.text.isEmpty()) {
            isValid = false
            toast(getString(R.string.enter_email))
        } else {
            if (!binding.emailEt.text.toString().isValidEmail()) {
                isValid = false
                toast(getString(R.string.invalid_email))
            }
        }
        if (binding.passEt.text.isEmpty()) {
            isValid = false
            toast(getString(R.string.enter_password))
        } else {
            if (binding.passEt.text.toString().length < 8) {
                isValid = false
                toast(getString(R.string.invalid_password))
            }
        }
        return isValid
    }
}