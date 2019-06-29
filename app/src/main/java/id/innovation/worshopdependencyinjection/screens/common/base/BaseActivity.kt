package id.innovation.worshopdependencyinjection.screens.common.base

import androidx.appcompat.app.AppCompatActivity
import id.innovation.worshopdependencyinjection.App
import id.innovation.worshopdependencyinjection.common.dependencyinjection.CompositionRoot

abstract class BaseActivity: AppCompatActivity() {

    protected fun getCompositionRoot(): CompositionRoot {
        return (application as App).getCompositionRoot()
    }
}