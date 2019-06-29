package id.innovation.worshopdependencyinjection.common.dependencyinjection

import id.innovation.worshopdependencyinjection.screens.movieslist.MainActivity

class Injector(private val mPresentationCompositionRoot: PresentationCompositionRoot) {

    fun inject(client: Any) {
        if (client is MainActivity) {
            injectMainActivity(client)
        } else {
            throw RuntimeException("invalid client: $client")
        }
    }

    private fun injectMainActivity(client: MainActivity) {
        client.mViewMvcFactory = mPresentationCompositionRoot.getViewMvcFactory()
        client.mDialogsManager = mPresentationCompositionRoot.getDialogsManager()
        client.mUseCase = mPresentationCompositionRoot.getMoviesUseCase()
    }

}