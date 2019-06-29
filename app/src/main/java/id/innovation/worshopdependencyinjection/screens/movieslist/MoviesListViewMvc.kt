package id.innovation.worshopdependencyinjection.screens.movieslist

import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.screens.common.mvcviews.ViewMvc

interface MoviesListViewMvc : ViewMvc {

    fun bindMovies(movies: List<MovieDtoBean>)

    fun showProgressBar()

    fun hideProgressBar()
}