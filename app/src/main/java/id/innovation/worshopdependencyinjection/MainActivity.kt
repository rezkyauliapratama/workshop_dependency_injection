package id.innovation.worshopdependencyinjection

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init recycler view
        rvPopularMovies.layoutManager = LinearLayoutManager(this)

        mAdapter = MoviesAdapter()

        rvPopularMovies.adapter = mAdapter

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

        mDisposable = mMoviesApi
            .getPopularMovies(BuildConfig.API_KEY, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (it.totalResults > 0) mAdapter.bindData(it.moviesDto)
                    progressBar.visibility = View.GONE
                }, {
                    progressBar.visibility = View.GONE
                }
            )

    }

    override fun onStop() {
        super.onStop()
        mDisposable.dispose()
    }
}
