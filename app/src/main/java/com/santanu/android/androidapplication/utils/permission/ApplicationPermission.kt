package com.santanu.android.androidapplication.utils.permission

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ApplicationPermission{

    companion object {

        /** check permissions **/
        internal fun isPermissionsGranted(context: Context, permissionsList: Array<String>): Boolean {
            for (permission: String in permissionsList) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
            return true
        }

        /** check permission **/
        internal fun isPermissionGranted(context: Context, permission: String): Boolean {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            return true
        }

        /** show permission need dialog **/
        internal fun showPermissionDialog(context: Context, permission: Array<String>, permissionReqCode: Int) {
            AlertDialog.Builder(context)
                .setTitle("Need Permission")
                .setMessage("Application Need to Run Allow Permission")
                .setPositiveButton("Allow") { _: DialogInterface, _: Int ->
                    ActivityCompat.requestPermissions(
                        context as Activity,
                        permission,
                        permissionReqCode
                    )
                }
                .setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()
                }
                .setNeutralButton("Dyne") { dialogInterface: DialogInterface, _: Int ->
                    Toast.makeText(context, "Granted Permission Manually", Toast.LENGTH_LONG).show()
                    dialogInterface.dismiss()
                    AlertDialog.Builder(context)
                        .setTitle("Permission Manually")
                        .setMessage("Setting > AppManagement > Application > Permission Tab")
                        .setPositiveButton("Ok") { dialog_Interface: DialogInterface, _: Int ->
                            dialog_Interface.dismiss()
                        }
                        .show()
                }
            .show()
        }
    }

    /** permissions request **/
    internal fun requestPermissions(context: Context, permissionsList: Array<String>, permissionReqCode: Int) {
        ActivityCompat.requestPermissions(context as Activity, permissionsList, permissionReqCode)
    }

    /** permission request **/
    internal fun requestPermission(context: Context, permission: String, permissionReqCode: Int) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permission)) {
                AlertDialog.Builder(context)
                    .setTitle("Need Permission")
                    .setMessage("Application Need to Run Allow Permission")
                    .setPositiveButton("Allow") { _: DialogInterface, _: Int ->
                        ActivityCompat.requestPermissions(
                            context,
                            arrayOf(permission),
                            permissionReqCode
                        )
                    }
                    .setNegativeButton("Dyne") { dialogInterface: DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setNeutralButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
                        Toast.makeText(context, "Granted Permission Manually", Toast.LENGTH_LONG).show()
                        dialogInterface.dismiss()
                        AlertDialog.Builder(context)
                            .setTitle("Permission Manually")
                            .setMessage("Setting > AppManagement > Application > Permission Tab")
                            .setPositiveButton("Ok") { dialog_Interface: DialogInterface, _: Int ->
                                dialog_Interface.dismiss()
                            }
                            .show()
                    }
                    .show()
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(permission), permissionReqCode)
            }
        }
    }
}