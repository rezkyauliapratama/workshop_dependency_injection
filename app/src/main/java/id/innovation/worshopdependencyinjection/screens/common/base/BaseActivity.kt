package id.innovation.worshopdependencyinjection.screens.common.base

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import id.innovation.worshopdependencyinjection.App
import id.innovation.worshopdependencyinjection.common.dependencyinjection.CompositionRoot
import id.innovation.worshopdependencyinjection.common.dependencyinjection.PresentationCompositionRoot

abstract class BaseActivity: AppCompatActivity() {

    private lateinit var mPresentationCompositionRoot: PresentationCompositionRoot

    protected fun getCompositionRoot(): PresentationCompositionRoot {
        if (!::mPresentationCompositionRoot.isInitialized){
            mPresentationCompositionRoot = PresentationCompositionRoot(
                getAppCompositionRoot(),
                supportFragmentManager,
                LayoutInflater.from(this)
            )
        }
        return mPresentationCompositionRoot
    }
    private fun getAppCompositionRoot(): CompositionRoot {
        return (application as App).getCompositionRoot()
    }
}