package id.innovation.worshopdependencyinjection

import android.app.Application
import id.innovation.worshopdependencyinjection.common.dependencyinjection.CompositionRoot

class App : Application() {

    lateinit var mCompositionRoot: CompositionRoot

    fun getCompositionRoot() : CompositionRoot {
        if (::mCompositionRoot.isInitialized){
            mCompositionRoot = CompositionRoot()
        }
        return mCompositionRoot
    }
}