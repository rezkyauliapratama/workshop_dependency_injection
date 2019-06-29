package id.innovation.worshopdependencyinjection.screens.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.innovation.worshopdependencyinjection.R
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.screens.common.mvcviews.BaseViewMvc
import kotlinx.android.synthetic.main.activity_main.view.*

class MoviesListViewMvcImpl(inflater: LayoutInflater, parent: ViewGroup?) : BaseViewMvc(), MoviesListViewMvc {

    private val mAdapter: MoviesAdapter
    init {
        view = inflater.inflate(R.layout.activity_main, parent, false)
        // init recycler view
        view.rvPopularMovies.layoutManager = LinearLayoutManager(view.context)

        mAdapter = MoviesAdapter()

        view.rvPopularMovies.adapter = mAdapter

    }

    override fun bindMovies(movies: List<MovieDtoBean>) {
        mAdapter.bindData(movies)
    }

    override fun showProgressBar() {
        view.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        view.progressBar.visibility = View.GONE
    }

}