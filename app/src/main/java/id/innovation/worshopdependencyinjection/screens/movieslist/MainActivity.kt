package id.innovation.worshopdependencyinjection.screens.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import id.innovation.worshopdependencyinjection.networking.MovieDtoBean
import id.innovation.worshopdependencyinjection.screens.common.base.BaseActivity
import id.innovation.worshopdependencyinjection.screens.common.dialog.DialogsManager
import id.innovation.worshopdependencyinjection.screens.common.dialog.ServerErrorDialogFragment
import id.innovation.worshopdependencyinjection.usecase.MoviesUseCase

class MainActivity : BaseActivity(), MoviesUseCase.Listener {

    lateinit var mViewMvc: MoviesListViewMvc
    lateinit var mUseCase: MoviesUseCase

    lateinit var mDialogsManager: DialogsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //right now, Activity doesn't know about the view , all related to view already handle by ViewMvc
        mViewMvc = MoviesListViewMvcImpl(LayoutInflater.from(this), null)
        setContentView((mViewMvc as MoviesListViewMvcImpl).view)

        mUseCase = getCompositionRoot().getMoviesUseCase()

        mDialogsManager =
            getCompositionRoot().getDialogsManager()
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
        mDialogsManager.showRetainedDialogWithId(ServerErrorDialogFragment.newInstance(), "")
    }
}
