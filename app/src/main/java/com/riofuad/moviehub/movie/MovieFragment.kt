package com.riofuad.moviehub.movie

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.riofuad.moviehub.R
import com.riofuad.moviehub.core.data.Resource
import com.riofuad.moviehub.core.ui.MovieAdapter
import com.riofuad.moviehub.databinding.FragmentMovieBinding
import com.riofuad.moviehub.detail.DetailActivity
import com.riofuad.moviehub.setting.SettingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi


@FlowPreview
@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment() {

    internal val movieViewModel: MovieViewModel by viewModels()
    private var _fragmentMovieBinding: FragmentMovieBinding? = null
    private val binding get() = _fragmentMovieBinding as FragmentMovieBinding
    private lateinit var searchView: SearchView
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentMovieBinding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search_menu, menu)
                val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
                searchView = menu.findItem(R.id.search_menu).actionView as SearchView
                searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
                searchView.queryHint = resources.getString(R.string.search)

                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(newText: String): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {

                        newText.let {
                            if (newText == "" || newText.isEmpty()) {
                                observerMovie()
                            } else {
                                movieViewModel.setSearchQuery(it)
                            }
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_setting -> {
                        val intent = Intent(activity, SettingActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        if (activity != null) {

            movieAdapter = MovieAdapter()
            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE, selectedData)
                startActivity(intent)
            }

            movieViewModel.movieResult.observe(viewLifecycleOwner) { movie ->
                if (searchView.query.toString() == "") {
                    binding.tvNoData.visibility = View.GONE
                    observerMovie()
                } else {
                    if (movie.isNullOrEmpty()) {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNoData.visibility = View.GONE
                    }
                    movieAdapter.setData(movie)
                }
            }


            with(binding.rvMovieList) {
                layoutManager = LinearLayoutManager(activity)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
            observerMovie()
        }
    }

    internal fun observerMovie() {
        movieViewModel.movies.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.tvNoData.visibility = View.GONE
                    }

                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNoData.visibility = View.GONE
                        movieAdapter.setData(movie.data)
                    }

                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.tvNoData.visibility = View.VISIBLE
                        Toast.makeText(context, "Something is wrong!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentMovieBinding = null
    }
}