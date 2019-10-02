package rynkbit.tk.coffeelist.ui.admin.backup


import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.PermissionChecker
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_directory_chooser.*

import rynkbit.tk.coffeelist.R
import java.io.File
import java.nio.file.Files

/**
 * A simple [Fragment] subclass.
 */
class DirectoryChooserFragment : Fragment() {
    companion object {
        const val REQUEST_PERMISSION_CODE = 10
    }

    private lateinit var directoryAdapter: DirectoryChooserAdapter
    private lateinit var viewmodel: DirectoryChooserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_directory_chooser, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewmodel = ViewModelProvider(this)[DirectoryChooserViewModel::class.java]
        directoryAdapter = DirectoryChooserAdapter()

        listDirectories.layoutManager = LinearLayoutManager(context!!, LinearLayoutManager.VERTICAL, false)
        listDirectories.adapter = directoryAdapter

        askPermissions(checkPermissions())
    }

    private fun readDirectory() {
        if (viewmodel.isRoot()) {
            val files = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            } else {
                context?.getExternalFilesDir("Documents")
            }

            if (files != null) {
                val dirs = files.list()

                if (dirs != null){
                    directoryAdapter.clear()
                    dirs.forEach {
                        directoryAdapter.add(it)
                    }
                    directoryAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == REQUEST_PERMISSION_CODE) {
            handlePermissionResult(grantResults)
        }
    }

    private fun handlePermissionResult(grantResults: IntArray) {
        if (grantResults.find { it == PackageManager.PERMISSION_DENIED } != null){
            Navigation.findNavController(activity!!, R.id.nav_host)
                    .popBackStack()
        } else {
            readDirectory()
        }
    }

    private fun askPermissions(missingPermissions: List<String>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && missingPermissions.isNotEmpty()) {
            activity?.requestPermissions(missingPermissions.toTypedArray(), REQUEST_PERMISSION_CODE)
        } else {
            readDirectory()
        }
    }

    private fun checkPermissions(): List<String> {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val missingPermissions = mutableListOf<String>()
            if (activity?.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                missingPermissions.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            if (activity?.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                missingPermissions.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            return missingPermissions
        }
        return emptyList()
    }
}
