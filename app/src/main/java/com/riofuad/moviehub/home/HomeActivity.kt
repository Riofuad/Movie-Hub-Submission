package com.riofuad.moviehub.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.riofuad.moviehub.R
import com.riofuad.moviehub.databinding.ActivityHomeBinding
import com.riofuad.moviehub.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi

@FlowPreview
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        switchFragment(MovieFragment())
        supportActionBar?.title = getString(R.string.nav_movie)

        binding.bottomNavbar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.movie_navigation -> {
                    switchFragment(MovieFragment())
                    supportActionBar?.title = getString(R.string.nav_movie)
                    true
                }
                R.id.favorite_navigation -> {
                    switchFavoriteFragment()
                    supportActionBar?.title = getString(R.string.nav_favorite)
                    true
                }
                else -> false
            }
        }
    }

    private val className: String
        get() = "com.riofuad.moviehub.favorite.FavoriteFragment"

    private fun switchFavoriteFragment() {
        val fragment = instantiateFragment(className)
        if (fragment != null) {
            switchFragment(fragment)
        }
    }


    private fun instantiateFragment(className: String): Fragment? {
        return try {
            Class.forName(className).newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(this, "Module not found", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .commit()
    }
}