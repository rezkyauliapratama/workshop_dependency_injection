package id.innovation.worshopdependencyinjection.usecase

import id.innovation.worshopdependencyinjection.BuildConfig
import id.innovation.worshopdependencyinjection.common.BaseObservable
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.networking.MovieListDto
import id.innovation.worshopdependencyinjection.networking.MoviesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class MoviesUseCase(retrofit: Retrofit) : BaseObservable<MoviesUseCase.Listener>() {

    interface Listener {
        fun onFetchOfMoviesSucceeded(questions: List<MovieDtoBean>)
        fun onFetchOfMoviesFailed()
    }

    private val mMoviesApi: MoviesApi = retrofit.create(MoviesApi::class.java)

    public fun fetchMovies() {
        mDisposable.add(mMoviesApi
            .getPopularMovies(BuildConfig.API_KEY, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    notifySucceeded(it)
                }, {
                    notifyFailed()
                }
            ))
    }

    private fun notifyFailed() {
        for (listener in listeners) {
            listener.onFetchOfMoviesFailed()
        }
    }

    private fun notifySucceeded(it: MovieListDto) {
        if (it.totalResults > 0) {
            for (listener in listeners) {
                listener.onFetchOfMoviesSucceeded(it.moviesDto)
            }
        }
    }

}