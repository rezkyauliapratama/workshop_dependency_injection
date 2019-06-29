package id.innovation.worshopdependencyinjection.screens.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import id.innovation.worshopdependencyinjection.App
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.screens.common.ServerErrorDialogFragment
import id.innovation.worshopdependencyinjection.usecase.MoviesUseCase

class MainActivity : AppCompatActivity(), MoviesUseCase.Listener {

    lateinit var mViewMvc: MoviesListViewMvc
    lateinit var mUseCase: MoviesUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //right now, Activity doesn't know about the view , all related to view already handle by ViewMvc
        mViewMvc = MoviesListViewMvcImpl(LayoutInflater.from(this), null)
        setContentView((mViewMvc as MoviesListViewMvcImpl).view)

        mUseCase = (application as App).getMoviesUseCase()
    }


    override fun onStart() {
        super.onStart()
        mViewMvc.showProgressBar()
        mUseCase.registerListener(this)
        mUseCase.fetchMovies()

    }

    override fun onStop() {
        super.onStop()
        mUseCase.unregisterListener(this)
    }

    override fun onFetchOfMoviesSucceeded(questions: List<MovieDtoBean>) {
        mViewMvc.hideProgressBar()
        mViewMvc.bindMovies(questions)
    }

    override fun onFetchOfMoviesFailed() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}
