package id.innovation.worshopdependencyinjection.screens.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import id.innovation.worshopdependencyinjection.R

class ServerErrorDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)

        alertDialogBuilder.setTitle(R.string.server_error_dialog_title)

        alertDialogBuilder.setMessage(R.string.server_error_dialog_message)

        alertDialogBuilder.setPositiveButton(
            R.string.server_error_dialog_button_caption,
            DialogInterface.OnClickListener { dialog, which -> dismiss() }
        )

        return alertDialogBuilder.create()
    }

    companion object {


        fun newInstance(): ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}
