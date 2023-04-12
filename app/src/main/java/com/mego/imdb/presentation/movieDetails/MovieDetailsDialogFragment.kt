package com.mego.imdb.presentation.movieDetails

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.mego.imdb.R
import com.mego.imdb.databinding.FragmentMovieDetailsDialogBinding
import com.mego.imdb.domain.Movie

private const val ARG_MOVIE = "com.mego.imdb.presentation.movieDetails"

class MovieDetailsDialogFragment : DialogFragment() {

    private var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movie = if (Build.VERSION.SDK_INT>=33)
                it.getSerializable(ARG_MOVIE, Movie::class.java)
            else
                it.getSerializable(ARG_MOVIE) as Movie
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val binding = FragmentMovieDetailsDialogBinding.inflate(requireActivity().layoutInflater)

        binding.movieName.text = movie?.title
        binding.movieYear.text = movie?.year.toString()
        Glide.with(this)
            .load(movie?.image?.url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .thumbnail(Glide.with(this).load(R.drawable.spinner))
            .error(R.drawable.corrupted_48)
            .into(binding.moviePoster)

        return AlertDialog.Builder( requireActivity(), )
            .setView( binding.root )
            .create()

    }

    companion object {
        const val TAG = "com.mego.imdb.autocomplete.detailsDialog"
        @JvmStatic
        fun newInstance( movie: Movie ) =
            MovieDetailsDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_MOVIE, movie)
                }
            }
    }
}