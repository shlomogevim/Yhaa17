package com.example.yhaa17

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_main2.view.*
import java.io.*

class Main2Activity : AppCompatActivity() {


    private val REQUEST_ID_READ_PERMISSION = 100
    private val REQUEST_ID_WRITE_PERMISSION = 200
    private val fileName = "talker.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        button_save.setOnClickListener {
            askPermissionAndWriteFile()
        }
        button_read.setOnClickListener {
            askPermissionAndReadFile()
        }
        button_list.setOnClickListener {
            listExternalStorages()
        }
    }

    private fun askPermissionAndWriteFile() {
        val canWrite = this.askPermission(
            REQUEST_ID_WRITE_PERMISSION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        //
        if (canWrite) {
            this.writeFile()
        }
    }

    private fun askPermissionAndReadFile() {
        val canRead = this.askPermission(
            REQUEST_ID_READ_PERMISSION,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        //
        if (canRead) {
            this.readFile()
        }
    }

    private fun askPermission(requestId: Int, permissionName: String): Boolean {
        /* if (android.os.Build.VERSION.SDK_INT >= 23) {
             // Check if we have permission
             val permission = ActivityCompat.checkSelfPermission(this, permissionName)
             if (permission != PackageManager.PERMISSION_GRANTED) {
                 // If don't have permission so prompt the user.
                 this.requestPermissions(
                     arrayOf<String>(permissionName),
                     requestId
                 )
                 return false
             }
         }*/
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //  if (grantResults.length > 0) {
        when (requestCode) {
            REQUEST_ID_READ_PERMISSION -> {
                run({
                    if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                        readFile()
                    }
                })
                run({
                    if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                        writeFile()
                    }
                })
            }
            REQUEST_ID_WRITE_PERMISSION -> {
                if (grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    writeFile()
                }
            }
        }
        /*} else {
            Toast.makeText(getApplicationContext(), "Permission Cancelled!", Toast.LENGTH_SHORT)
                .show()
        }*/


    }

    private fun writeFile() {
        val extStore = Environment.getExternalStorageDirectory()
        // ==> /storage/emulated/0/note.txt
        val path = extStore.getAbsolutePath() + "/" + fileName
        Log.i("clima", "Save to: " + path)
        val data = editText.getText().toString()
        try {
            val myFile = File(path)
            myFile.createNewFile()
            val fOut = FileOutputStream(myFile)
            val myOutWriter = OutputStreamWriter(fOut)
            myOutWriter.append(data)
            myOutWriter.close()
            fOut.close()
            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG)
                .show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readFile() {
        val extStore = Environment.getExternalStorageDirectory()
        // ==> /storage/emulated/0/note.txt
        val path = extStore.getAbsolutePath() + "/" + fileName
        Log.i("clima", "Read file: " + path)
        var s: String? = null
        var fileContent = ""
        try {
            val myFile = File(path)
            val fIn = FileInputStream(myFile)
            val myReader = BufferedReader(InputStreamReader(fIn))

            s = myReader.readLine()


            while (s != null) {
                fileContent += s + "\n"
                s = myReader.readLine()
            }
            myReader.close()
            this.textView.setText(fileContent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show()
    }

    private fun readFile1() {

        val extStore = Environment.getExternalStorageDirectory()
        // ==> /storage/emulated/0/note.txt
        val path = extStore.getAbsolutePath() + "/" + fileName



        Log.i("clima", "Read file: " + path)
        var s = ""
        var fileContent = ""
        try {
            val myFile = File(path)
            val fIn = FileInputStream(myFile)
            val myReader = BufferedReader(InputStreamReader(fIn))
            val stringBuilder: java.lang.StringBuilder = java.lang.StringBuilder()

            var text: String? = null
            while ({ text = myReader.readLine();text }() != null) {
                stringBuilder.append(text)
            }



            myReader.close()
            fIn.close()
            this.textView.setText(text)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show()
    }

    private fun listExternalStorages() {
        val sb = StringBuilder()
        sb.append("Data Directory: ").append("\n - ")
            .append(Environment.getDataDirectory().toString()).append("\n")
        sb.append("Download Cache Directory: ").append("\n - ")
            .append(Environment.getDownloadCacheDirectory().toString()).append("\n")
        sb.append("External Storage State: ").append("\n - ")
            .append(Environment.getExternalStorageState().toString()).append("\n")
        sb.append("External Storage Directory: ").append("\n - ")
            .append(Environment.getExternalStorageDirectory().toString()).append("\n")
        sb.append("Is External Storage Emulated?: ").append("\n - ")
            .append(Environment.isExternalStorageEmulated()).append("\n")
        sb.append("Is External Storage Removable?: ").append("\n - ")
            .append(Environment.isExternalStorageRemovable()).append("\n")
        sb.append("External Storage Public Directory (Music): ").append("\n - ")
            .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString())
            .append("\n")
        sb.append("Download Cache Directory: ").append("\n - ")
            .append(Environment.getDownloadCacheDirectory().toString()).append("\n")
        sb.append("Root Directory: ").append("\n - ")
            .append(Environment.getRootDirectory().toString()).append("\n")
        Log.i("clima", sb.toString())
        this.textView.setText(sb.toString())
    }

}
