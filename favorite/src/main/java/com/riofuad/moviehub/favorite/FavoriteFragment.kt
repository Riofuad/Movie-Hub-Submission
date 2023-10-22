package com.riofuad.moviehub.favorite

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.riofuad.moviehub.core.ui.MovieAdapter
import com.riofuad.moviehub.di.FavoriteModuleDependencies
import com.riofuad.moviehub.favorite.databinding.FragmentFavoriteBinding
import com.riofuad.moviehub.favorite.di.DaggerFavoriteModule
import com.riofuad.moviehub.favorite.di.ViewModelFactory
import com.riofuad.moviehub.detail.DetailActivity
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class FavoriteFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    private var _fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private val binding get() = _fragmentFavoriteBinding as FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentFavoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteModule.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
                startActivity(intent)
            }

            favoriteViewModel.moviesFavorite.observe(viewLifecycleOwner) { movie ->
                movieAdapter.setData(movie)
                binding.tvNoFavorite.visibility =
                    if (movie.isNotEmpty()) View.GONE else View.VISIBLE
                binding.progressBar.visibility =
                    if (movie.isNotEmpty()) View.GONE else View.GONE
            }

            with(binding.rvFavoriteMovieList) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentFavoriteBinding = null
    }
}