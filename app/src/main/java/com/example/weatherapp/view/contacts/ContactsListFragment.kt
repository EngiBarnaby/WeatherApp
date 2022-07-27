package com.example.weatherapp.view.contacts

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherapp.databinding.FragmentContactsBinding
import com.example.weatherapp.utils.REQUEST_CODE_READ_CONTACTS
import com.example.weatherapp.view.weatherlist.WeatherListFragment

class ContactsListFragment : Fragment() {

    lateinit var binding: FragmentContactsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun checkPermission(){
        val perResult = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_CONTACTS)
        if(perResult == PackageManager.PERMISSION_GRANTED){
            getContacts()
        }
        else if(shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)){
            AlertDialog.Builder(requireContext())
                .setTitle("Доступ к контактам")
                .setMessage("Объяснение")
                .setPositiveButton("Предоставить доступ") { _, _ ->
                    permissionRequest(Manifest.permission.READ_CONTACTS)
                }
                .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
        else{
            permissionRequest(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun permissionRequest(permission : String){
        requestPermissions(arrayOf(permission), REQUEST_CODE_READ_CONTACTS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_CODE_READ_CONTACTS){
            for(permissionIndex in permissions.indices){
                if(permissions[permissionIndex] == Manifest.permission.READ_CONTACTS
                    && grantResults[permissionIndex] == PackageManager.PERMISSION_GRANTED){
                    getContacts()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun getContacts(){
        val contentResolver : ContentResolver = requireContext().contentResolver
        val cursorWithContacts : Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (cursorWithContacts != null) {
            while (cursorWithContacts.moveToNext()){
                val name = cursorWithContacts?.getString(cursorWithContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val phone = cursorWithContacts?.getString(cursorWithContacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                binding.containerForContacts.addView(TextView(requireContext()).apply {
                    text = name + " " + phone
                    textSize = 18f
                })
            }
        }

        cursorWithContacts?.let { cursor ->
            for(i in 0 until cursor.count){
            }
        }
        cursorWithContacts?.close()
    }

    companion object {
        fun newInstance() = WeatherListFragment()
    }

}