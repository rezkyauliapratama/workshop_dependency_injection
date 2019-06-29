package id.innovation.worshopdependencyinjection.common.dependencyinjection

import id.innovation.worshopdependencyinjection.screens.common.dialog.DialogsManager
import id.innovation.worshopdependencyinjection.screens.common.mvcviews.ViewMvcFactory
import id.innovation.worshopdependencyinjection.usecase.MoviesUseCase
import java.lang.reflect.Field

class Injector(private val mPresentationCompositionRoot: PresentationCompositionRoot) {

    fun inject(client: Any) {
        val clazz = client.javaClass

        val fields = clazz.declaredFields

        for (field in fields) {
            if (isAnnotatedForInjection(field)) {
                injectField(client, field)
            }
        }
    }

    private fun isAnnotatedForInjection(field: Field): Boolean {
        val annotations = field.declaredAnnotations

        for (annotation in annotations) {
            if (annotation is Service) {
                return true
            }
        }

        return false
    }

    private fun injectField(client: Any, field: Field) {
        try {
            val isAccessibleInitially = field.isAccessible
            field.isAccessible = true
            field.set(client, getServiceForClass(field.type))
            field.isAccessible = isAccessibleInitially
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }

    }

    private fun getServiceForClass(type: Class<*>): Any {
        return if (type == DialogsManager::class.java) {
            mPresentationCompositionRoot.getDialogsManager()
        } else if (type == ViewMvcFactory::class.java) {
            mPresentationCompositionRoot.getViewMvcFactory()
        } else if (type == MoviesUseCase::class.java) {
            mPresentationCompositionRoot.getMoviesUseCase()
        } else {
            throw RuntimeException("unsupported service type class: $type")
        }
    }


}