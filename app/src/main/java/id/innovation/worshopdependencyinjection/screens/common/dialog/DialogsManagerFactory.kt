package id.innovation.worshopdependencyinjection.screens.common.dialog

import androidx.fragment.app.FragmentManager


class DialogsManagerFactory {

    fun newDialogsManager(fragmentManager: FragmentManager): DialogsManager {
        return DialogsManager(fragmentManager)
    }
}
