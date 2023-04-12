package com.mego.imdb.presentation.popularmovies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mego.imdb.databinding.FragmentPopularMoviesListBinding
import com.mego.imdb.domain.Movie
import org.koin.androidx.viewmodel.ext.android.viewModel


class PopularMoviesFragment : Fragment() {

    private var columnCount = 1

    private val viewModel: PopularMoviesViewModel by viewModel()

    private val mValues = ArrayList<Movie>()

    private val binding : FragmentPopularMoviesListBinding by lazy {
        FragmentPopularMoviesListBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mValues.clear()
        mValues.addAll ( viewModel.popularMoviesLiveData.value!! )

        with(binding.recyclerView) {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyMovieRecyclerViewAdapter(mValues, this@PopularMoviesFragment)
        }

        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner) {
            mValues.clear()
            mValues.addAll(it)
            binding.recyclerView.adapter?.notifyItemInserted(it.size-1)
        }

        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            if (it)
                showProgressHideRecycler()
            else
                showRecyclerHideProgress()
        }

        return binding.root
    }

    private fun showProgressHideRecycler() {
        binding.progress.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }

    private fun showRecyclerHideProgress() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progress.visibility = View.GONE
    }

}