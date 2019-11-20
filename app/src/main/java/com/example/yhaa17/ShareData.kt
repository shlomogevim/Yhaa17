package com.example.yhaa17

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.*


private val filepath = "MyFileStorage"
internal var myExternalFile: File? = null


class ShareData(val context: Context, val fileNum: Int) : AppCompatActivity() {


    private val REQUEST_ID_READ_PERMISSION = 100
    private val REQUEST_ID_WRITE_PERMISSION = 200
    // private val fileName = "note.txt"
    private val fileName = "talklist" + fileNum.toString()+".txt"


    val TALKLIST = "talklist" + fileNum.toString()
    var myPref: SharedPreferences
    var editor: SharedPreferences.Editor

    init {
        myPref = PreferenceManager.getDefaultSharedPreferences(context)
        editor = myPref.edit()

    }

    private val filepath = "MyFileStorage"
    internal var myExternalFile: File? = null


    fun saveDataToExternalStorage(talkingList: ArrayList<Talker>) {
        val path1 = Environment.getExternalStorageDirectory().path + "/talkFolder/"


        try {
            myExternalFile = File(path1, TALKLIST)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val gson = Gson()
        val jsonString = gson.toJson(talkingList)


        try {
            val fileOutPutStream = FileOutputStream(myExternalFile)
            fileOutPutStream.write(jsonString.toByteArray())
            fileOutPutStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        // Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()
    }

    fun getTalkingListFromExternalStorage(ind: Int): ArrayList<Talker> {
        var talkList1: ArrayList<Talker> = arrayListOf()
        var jsonString = ""
        val gson = Gson()
        myExternalFile = File(context.getExternalFilesDir(filepath), TALKLIST)

        if (TALKLIST != null) {
            var fileInputStream = FileInputStream(myExternalFile)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            jsonString = stringBuilder.toString()

            //      while ({ jsonString = bufferedReader.readLine(); text }() != null)

            if (ind == 0 || jsonString == null) {
                talkList1 = createTalkListFromTheStart()
                saveData(talkList1)

            } else {
                val type = object : TypeToken<ArrayList<Talker>>() {}.type
                talkList1 = gson.fromJson(jsonString, type)

                //stringBuilder.append(text)
            }


            fileInputStream.close()
        }

        return talkList1

    }


    fun saveData(talkingList: ArrayList<Talker>) {

        val fileName = Environment.getExternalStorageDirectory().path + "/myFolder/myFile.txt"
        val gson = Gson()
        val jsonString = gson.toJson(talkingList)
        editor.putString(TALKLIST, jsonString)
        editor.apply()

    }

    fun createTalkListFromTheStart(): ArrayList<Talker> {
        var talkList1 = arrayListOf<Talker>()
        val ADAM = "-אדם-"
        val GOD = "-אלוהים-"
        val CURRENT_FILE = "text/text" + fileNum.toString() + ".txt"

        var countItem = 0
        var text = context.assets.open(CURRENT_FILE).bufferedReader().use {
            it.readText()
        }
        text = text.replace("\r", "")
        var list1 = text.split(ADAM)

        var talker = Talker()

        talkList1.add(countItem, talker)
        var i = 0

        for (element in list1) {
            //  if (element != "" && element.length > 25) {
            if (element != "") {
                i++
                var list2 = element.split(GOD)
                var st1 = improveString(list2[0])
                var st2 = improveString(list2[1])
                if (st1.isNullOrEmpty() || st2.isNullOrEmpty()) {
                    return talkList1
                }
                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "man"
                    taking = st1.trim()
                    numTalker = countItem
                    var arr = st1.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText = "#000000"
                    colorBack = "#ffffff"
                    animNum = 10
                }

                talkList1.add(talker)

                countItem++
                talker = Talker()
                with(talker) {
                    whoSpeake = "god"
                    talker.taking = st2.trim()
                    talker.numTalker = countItem
                    var arr = st2.split("\n")
                    for (item in arr) {
                        if (item != "") {
                            takingArray.add(item)
                        }
                    }
                    colorText = "#000000"
                    colorBack = "#ffffff"
                    animNum = 10
                }
                talkList1.add(talker)
            }
        }
        return talkList1
    }

    private fun improveString(st: String) = st.substring(1, st.length - 1)

    // ************************


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
            val permission= ActivityCompat.checkSelfPermission(context, permissionName)
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                requestPermissions(arrayOf<String>(permissionName),requestId)
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

    fun saveDataToExternalStorage1(talkingList: ArrayList<Talker>) {

        val canWrite = this.askPermission(
            REQUEST_ID_WRITE_PERMISSION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        if (canWrite) {
            writeFile1(talkingList)
        }
    }

    private fun writeFile1(talkingList: ArrayList<Talker>) {

        val gson = Gson()
        val jsonString = gson.toJson(talkingList)

        val extStore = Environment.getExternalStorageDirectory()
        // ==> /storage/emulated/0/note.txt
        val path = extStore.getAbsolutePath() + "/" + fileName
        Log.i("clima", "Save to: " + path)
        try {
            val myFile = File(path)
            myFile.createNewFile()
            val fOut = FileOutputStream(myFile)
            val myOutWriter = OutputStreamWriter(fOut)
            myOutWriter.append(jsonString)
            myOutWriter.close()
            fOut.close()
            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show()
            listExternalStorages()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }












    fun getTalkingListFromExternalStorage1(ind: Int): ArrayList<Talker> {
        var talkList1: ArrayList<Talker> = arrayListOf()
        var jsonString = ""
        val gson = Gson()


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
            // this.textView.setText(fileContent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show()

        jsonString = fileContent
        if (ind == 0 || jsonString == null) {
            talkList1 = createTalkListFromTheStart()
            saveData(talkList1)

        } else {
            val type = object : TypeToken<ArrayList<Talker>>() {}.type
            talkList1 = gson.fromJson(jsonString, type)
        }
        listExternalStorages()
        return talkList1


/*

        myExternalFile = File(context.getExternalFilesDir(filepath), TALKLIST)

        if (TALKLIST != null) {
            var fileInputStream = FileInputStream(myExternalFile)
            var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder: StringBuilder = StringBuilder()
            var text: String? = null

            while ({ text = bufferedReader.readLine(); text }() != null) {
                stringBuilder.append(text)
            }
            jsonString = stringBuilder.toString()*/

        //      while ({ jsonString = bufferedReader.readLine(); text }() != null)


    }


    private fun writeFile() {
        val extStore = Environment.getExternalStorageDirectory()
        // ==> /storage/emulated/0/note.txt
        val path = extStore.getAbsolutePath() + "/" + fileName
        Log.i("clima", "Save to: " + path)
        //   val data = editText.getText().toString()
        try {
            val myFile = File(path)
            myFile.createNewFile()
            val fOut = FileOutputStream(myFile)
            val myOutWriter = OutputStreamWriter(fOut)
            // myOutWriter.append(data)
            myOutWriter.close()
            fOut.close()
            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show()
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
            // this.textView.setText(fileContent)
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
        // this.textView.setText(sb.toString())
    }
}

