package com.mego.imdb.presentation.autocomplete

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.mego.imdb.R
import com.mego.imdb.domain.Movie
import com.mego.imdb.presentation.movieDetails.MovieDetailsDialogFragment
import org.koin.android.ext.android.inject

class AutocompleteFragment : Fragment() {

    private val viewModel: AutocompleteViewModel by inject()
    private lateinit var arrayAdapter :ArrayAdapter<Movie>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_autocomplete, container, false)

        arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item)

        viewModel.moviesAutocompleteLiveData.observe(viewLifecycleOwner) {
            arrayAdapter.clear()
            arrayAdapter.addAll(it)
        }

        val autocompleteET = v.findViewById<TextInputLayout>(R.id.search_titles_autocomplete).editText as AutoCompleteTextView
        autocompleteET.setAdapter(arrayAdapter)
        autocompleteET.threshold = 2
        autocompleteET.doAfterTextChanged {
            viewModel.updateAutocompleteList(it.toString())
        }
        autocompleteET.setOnItemClickListener { adapterView, view, position, l ->
            val selectedMovie = adapterView.getItemAtPosition(position) as Movie
            MovieDetailsDialogFragment.newInstance(selectedMovie).show(parentFragmentManager,MovieDetailsDialogFragment.TAG)
        }

        return v
    }
}