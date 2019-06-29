package id.innovation.worshopdependencyinjection.screens.common.mvcviews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import id.innovation.worshopdependencyinjection.screens.movieslist.MoviesListViewMvc
import id.innovation.worshopdependencyinjection.screens.movieslist.MoviesListViewMvcImpl


class ViewMvcFactory(private val mLayoutInflater: LayoutInflater){

    /**
     * Instantiate a new implementation of MVC view. The returned instance will be casted to MVC view
     * type inferred by java's automatic type inference.
     * @param mvcViewClass the class of the required MVC view
     * @param container this container will be used as MVC view's parent. See [LayoutInflater.inflate]
     * @param <T> the type of the required MVC view
     * @return new instance of MVC view
    </T> */
    fun <T : ViewMvc> newInstance(mvcViewClass: Class<T>, container: ViewGroup?): T {

        val viewMvc: ViewMvc

        if (mvcViewClass == MoviesListViewMvc::class.java) {
            viewMvc = MoviesListViewMvcImpl(mLayoutInflater, container)
        } else {
            throw IllegalArgumentException("unsupported MVC view class $mvcViewClass")
        }


        return viewMvc as T
    }
}