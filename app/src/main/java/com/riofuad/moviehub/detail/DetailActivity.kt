package com.riofuad.moviehub.detail

import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import com.riofuad.moviehub.R
import com.riofuad.moviehub.core.domain.model.Movie
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.material.snackbar.Snackbar
import com.riofuad.moviehub.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val detailMovie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_MOVIE)
        }
        showDetail(detailMovie)

    }

    private fun share(name: String, overview: String?) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder.from(this).apply {
            setType(mimeType)
            setChooserTitle(name)
            setText("$name \n $overview\n" + getString(R.string.share_msg))
            startChooser()
        }
    }

    private fun showDetail(detailMovie: Movie?) {
        detailMovie?.let {
            binding.apply {
                movieTitle.text = detailMovie.name
                movieDescription.text = detailMovie.overview
                tvRatingMovie.text = detailMovie.vote_average.toString()
                moviePoster.loadImage(detailMovie.poster)
                movieSubPoster.loadImage(detailMovie.poster)
                var isFavorite = detailMovie.isFavorite
                setFavoriteMovie(isFavorite)

                fabFavorite.setOnClickListener {
                    isFavorite = !isFavorite
                    detailViewModel.setFavoriteMovie(detailMovie, isFavorite)
                    setFavoriteMovie(isFavorite)
                    showSnackbar(isFavorite, detailMovie.name)
                }

                btnShare.setOnClickListener {
                    share(detailMovie.name , detailMovie.overview)
                }

                btnBack.setOnClickListener {
                    onBackPressed()
                }
            }
        }
    }

    private fun ImageView.loadImage(url: String?) {
        Glide.with(this@DetailActivity)
            .load("https://image.tmdb.org/t/p/w500${url}")
            .transition(DrawableTransitionOptions.withCrossFade())
            .placeholder(R.drawable.placeholder_movie)
            .into(this)
    }

    private fun showSnackbar(state: Boolean, name: String) {
        val msg = if (state) {
            "$name " + getString(R.string.added_to_favorite)
        } else {
            "$name " + getString(R.string.remove_from_favorite)
        }
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun setFavoriteMovie(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_filled
                )
            )

        } else {
            binding.fabFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_outlined
                )
            )
        }
    }

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
}