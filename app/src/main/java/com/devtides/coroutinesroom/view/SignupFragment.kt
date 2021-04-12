package com.devtides.coroutinesroom.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentSignupBinding
import com.devtides.coroutinesroom.viewmodel.SignupViewModel

class SignupFragment : Fragment() {

    private lateinit var viewModel: SignupViewModel
    private lateinit var binding: FragmentSignupBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.signupBtn.setOnClickListener { onSignUp(it) }
        binding.gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)
        observeViewModel()
        return view
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(activity, "Signup Complete", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.navigate(R.id.actionGoToMain)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onSignUp(v: View){
        val username: String = binding.signupUsername.text.toString()
        val password: String = binding.signupPassword.text.toString()
        val info: String = binding.otherInfo.text.toString()
        if (username.isEmpty()) {
            showError(binding.signupUsername, "Please enter a username!")
        } else if (password.isEmpty()) {
            showError(binding.signupPassword, "Please enter a password!")
        } else if (info.isEmpty()) {
            showError(binding.otherInfo, "Please enter more information!")
        } else {
            viewModel.signup(username, password, info)
        }
    }

    private fun onGotoLogin(v: View) {
        v.findNavController().navigate(R.id.actionGoToLogin)
    }

    private fun showError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}
