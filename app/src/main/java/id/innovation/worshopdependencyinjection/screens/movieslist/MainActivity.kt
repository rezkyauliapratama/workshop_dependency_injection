package id.innovation.worshopdependencyinjection.screens.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import id.innovation.worshopdependencyinjection.BuildConfig
import id.innovation.worshopdependencyinjection.networking.MoviesApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var mAdapter: MoviesAdapter

    lateinit var mMoviesApi: MoviesApi

    lateinit var mDisposable: Disposable

    lateinit var mViewMvc: MoviesListViewMvc

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //right now, Activity doesn't know about the view , all related to view already handle by ViewMvc
        mViewMvc = MoviesListViewMvcImpl(LayoutInflater.from(this), null)
        setContentView((mViewMvc as MoviesListViewMvcImpl).view)

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


    override fun onStart() {
        super.onStart()
        mViewMvc.showProgressBar()
        mDisposable = mMoviesApi
            .getPopularMovies(BuildConfig.API_KEY, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.totalResults > 0) mViewMvc.bindMovies(it.moviesDto)
                    mViewMvc.hideProgressBar()
                }, {
                    mViewMvc.hideProgressBar()
                }
            )

    }

    override fun onStop() {
        super.onStop()
        mDisposable.dispose()
    }
}
