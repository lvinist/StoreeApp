package com.dicoding.storeeapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.storeeapp.R
import com.dicoding.storeeapp.data.DefaultResponse
import com.dicoding.storeeapp.databinding.ActivityMainBinding
import com.dicoding.storeeapp.ui.home.adapter.StoryAdapter
import com.dicoding.storeeapp.ui.login.LoginActivity
import com.dicoding.storeeapp.ui.maps.MapsActivity
import com.dicoding.storeeapp.ui.postimage.PostImageActivity
import com.dicoding.storeeapp.utils.Constants.POST_STORY_RESPONSE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()
    private val storyAdapter: StoryAdapter by lazy { StoryAdapter() }

    private var response: DefaultResponse? = null

    private val launchPostStory =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                response = it.data?.getParcelableExtra(POST_STORY_RESPONSE)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeLoginToken()
        observeStories()

        binding.fbAddstory.setOnClickListener{
            val intent = Intent(this, PostImageActivity::class.java)
            launchPostStory.launch(intent)
        }

        binding.rvPhotos.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.logout -> {
                mainViewModel.logout()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            R.id.to_map -> {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
                true
            }
            else -> true
        }
    }

    private fun observeStories() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                if (response != null && response?.error == false) {
                    mainViewModel.getLatestStories()
                }
                mainViewModel.stories.collect {
                    storyAdapter.submitData(it)
                }
            }
        }
    }

    private fun observeLoginToken() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                mainViewModel.loginToken.collect {
                    if(it.isEmpty()) {
                        startActivity(Intent(this@MainActivity, LoginActivity::class.java).apply {
                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        })
                        finish()
                    }
                }
            }
        }
    }
}