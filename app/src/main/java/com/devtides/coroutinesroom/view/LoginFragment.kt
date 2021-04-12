package com.devtides.coroutinesroom.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.databinding.FragmentLoginBinding
import com.devtides.coroutinesroom.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.loginBtn.setOnClickListener { onLogin(it) }
        binding.gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        observeViewModel()
        return view
    }


    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(activity, "Login Complete", Toast.LENGTH_SHORT).show()
                view?.findNavController()?.navigate(R.id.actionGoToMain)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(activity, "Error: $error", Toast.LENGTH_SHORT).show()
        })
    }

    private fun onLogin(v: View) {
        val username: String = binding.loginUsername.text.toString()
        val password: String = binding.loginPassword.text.toString()
        if (username.isEmpty()) {
            showError(binding.loginUsername, "Please enter your username!")
        } else if (password.isEmpty()) {
            showError(binding.loginPassword, "Please enter your password!")
        } else {
            viewModel.login(username, password)
        }
    }

    private fun onGotoSignup(v: View){
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }

    private fun showError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
}
