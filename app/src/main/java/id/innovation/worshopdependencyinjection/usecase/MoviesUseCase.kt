package id.innovation.worshopdependencyinjection.usecase

import id.innovation.worshopdependencyinjection.BuildConfig
import id.innovation.worshopdependencyinjection.common.BaseObservable
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.networking.MovieListDto
import id.innovation.worshopdependencyinjection.networking.MoviesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MoviesUseCase : BaseObservable<MoviesUseCase.Listener>() {

    interface Listener {
        fun onFetchOfMoviesSucceeded(questions: List<MovieDtoBean>)
        fun onFetchOfMoviesFailed()
    }


    private val mMoviesApi: MoviesApi

    init {
        //init okhttp
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient.Builder()
            .followRedirects(false)
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        mMoviesApi = retrofit.create(MoviesApi::class.java)

    }

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