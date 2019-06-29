package id.innovation.worshopdependencyinjection.screens.movieslist

import id.innovation.worshopdependencyinjection.networking.MovieDtoBean

interface MoviesListViewMvc {

    fun bindMovies(movies: List<MovieDtoBean>)

    fun showProgressBar()

    fun hideProgressBar()
}