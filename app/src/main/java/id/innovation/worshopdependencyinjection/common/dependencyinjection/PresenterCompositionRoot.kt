package id.innovation.worshopdependencyinjection.common.dependencyinjection

import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import id.innovation.worshopdependencyinjection.screens.common.dialog.DialogsManager
import id.innovation.worshopdependencyinjection.screens.common.mvcviews.ViewMvcFactory
import id.innovation.worshopdependencyinjection.usecase.MoviesUseCase


class PresentationCompositionRoot(
    private val mCompositionRoot: CompositionRoot,
    private val mFragmentManager: FragmentManager,
    private val mLayoutInflater: LayoutInflater
) {

    fun getDialogsManager(): DialogsManager {
        return DialogsManager(mFragmentManager)
    }

    fun getMoviesUseCase(): MoviesUseCase {
        return MoviesUseCase(mCompositionRoot.getApi())
    }

    fun getViewMvcFactory(): ViewMvcFactory {
        return ViewMvcFactory(mLayoutInflater)
    }
}
