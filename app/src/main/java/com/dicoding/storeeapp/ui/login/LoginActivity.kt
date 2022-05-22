package com.dicoding.storeeapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.dicoding.storeeapp.R
import com.dicoding.storeeapp.data.user.Login
import com.dicoding.storeeapp.databinding.ActivityLoginBinding
import com.dicoding.storeeapp.ui.home.MainActivity
import com.dicoding.storeeapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLoginResponse()
        setEnableSigninButton()
        signinButtonClickListener()
    }

    private fun observeLoginResponse() {
        loginViewModel.loginResponse.observe(this) { loginresponse ->
            loginresponse.error.let {
                when (it) {
                    true -> {
                        Toast.makeText(this, loginresponse.message, Toast.LENGTH_SHORT).show()
                        binding.pbLogin.visibility = View.GONE
                    }
                    false -> {
                        startActivity(Intent(this, MainActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        })
                        finish()
                    }
                    null -> Toast.makeText(this, getString(R.string.null_response), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setEnableSigninButton() {
        binding.tvPassword.addTextChangedListener {
            val password = binding.tvPassword.text
            val email = binding.tvEmail.text
            val buttonShouldEnabled = (password.toString()
                .isNotEmpty() && password.toString().length >= 6) && (email.toString()
                .isNotEmpty() && email.toString().isValidEmail())
            binding.btnSignin.isEnabled = buttonShouldEnabled
        }
    }

    private fun signinButtonClickListener() {
        binding.btnSignin.setOnClickListener {
            binding.apply {
                tvPassword.setOnEditorActionListener { textView, actionId, _ ->
                    if (textView.text.isNotEmpty() && textView.error.isNullOrEmpty() && binding.tvEmail.error.isNullOrEmpty()) {
                        loginViewModel.login(
                            Login(
                                binding.tvEmail.text.toString(),
                                textView.text.toString()
                            )
                        )
                    }
                    actionId == EditorInfo.IME_ACTION_DONE
                }
                binding.pbLogin.visibility = View.VISIBLE
            }
            loginViewModel.login(
                Login(
                    binding.tvEmail.text.toString(),
                    binding.tvPassword.text.toString()
                )
            )
        }
    }
}