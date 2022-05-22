package com.dicoding.storeeapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.dicoding.storeeapp.data.user.Register
import com.dicoding.storeeapp.databinding.ActivityRegisterBinding
import com.dicoding.storeeapp.utils.Constants.REGISTER_RESPONSE
import com.dicoding.storeeapp.utils.Utils.isValidEmail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setButtonShouldEnabled()
        setSubmitButton()
        observeResponse()
    }

    private fun setButtonShouldEnabled() {
        binding.apply {
            edtName.addTextChangedListener {
                setButtonEnabled()
            }
            edtEmail.addTextChangedListener {
                setButtonEnabled()
            }
            edtPassword.addTextChangedListener {
                setButtonEnabled()
            }
        }
    }

    private fun setButtonEnabled() {
        val username = binding.edtName.text.toString()
        val password = binding.edtPassword.text.toString()
        val email = binding.edtEmail.text.toString()
        val buttonShouldEnabled = (password
            .isNotEmpty() && password.length >= 6) && (email
            .isNotEmpty() && email.isValidEmail() && username.isNotEmpty())
        binding.btSignup.isEnabled = buttonShouldEnabled
    }

    private fun setSubmitButton() {
        binding.btSignup.setOnClickListener {
            binding.pbSignup.visibility = View.VISIBLE

            val username = binding.edtName.text.toString()
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            lifecycleScope.launch {
                registerViewModel.register(Register(username, email, password))
            }
        }
    }


    private fun observeResponse() {
        registerViewModel.response.observe(this) {
            if (!it.error) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                setResult(RESULT_OK, Intent().putExtra(REGISTER_RESPONSE, it))
                finishAndRemoveTask()
            }
            if(it.error){
                setResult(RESULT_OK, Intent().putExtra(REGISTER_RESPONSE, it))
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                binding.pbSignup.visibility = View.GONE
            }
        }
    }
}