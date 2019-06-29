package id.innovation.worshopdependencyinjection.common.dependencyinjection

import id.innovation.worshopdependencyinjection.BuildConfig
import id.innovation.worshopdependencyinjection.networking.MoviesApi
import id.innovation.worshopdependencyinjection.usecase.MoviesUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CompositionRoot  {
    var mRetrofit: Retrofit? = null
    var mMoviesApi: MoviesApi? = null

    fun getRetrofit(): Retrofit {
        if (mRetrofit == null) {
            //init okhttp
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val clientBuilder = OkHttpClient.Builder()
                .followRedirects(false)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)

            // init retrofit
            mRetrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        }
        return mRetrofit!!
    }

    fun getApi() : MoviesApi {
        if (mMoviesApi == null){
            mMoviesApi = getRetrofit().create(MoviesApi::class.java)
        }
        return mMoviesApi!!
    }

    fun getMoviesUseCase(): MoviesUseCase {
        return MoviesUseCase(getApi())
    }
}