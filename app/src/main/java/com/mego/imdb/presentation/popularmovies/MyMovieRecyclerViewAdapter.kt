package com.mego.imdb.presentation.popularmovies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mego.imdb.R
import com.mego.imdb.databinding.FragmentPopularMoviesItemBinding
import com.mego.imdb.domain.Movie
import com.mego.imdb.presentation.movieDetails.MovieDetailsDialogFragment


class MyMovieRecyclerViewAdapter( private val values: List<Movie>, private val mFragment: PopularMoviesFragment)
    : RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPopularMoviesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        Glide.with(mFragment)
            .load(item.image.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .thumbnail(Glide.with(mFragment).load(R.drawable.spinner))
            .error(R.drawable.corrupted_48)
            .into(holder.binding.moviePoster)

        holder.binding.title.text = item.title

        holder.binding.year.text = item.year.toString()

        holder.itemView.setOnClickListener {
            MovieDetailsDialogFragment.newInstance(item).show(mFragment.parentFragmentManager, MovieDetailsDialogFragment.TAG)
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: FragmentPopularMoviesItemBinding) :
        RecyclerView.ViewHolder(binding.root)

}