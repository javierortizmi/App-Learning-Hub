package com.javierortizmi.serverinteraction

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.net.URLConnection

class WriteServer(mainActivity: MainActivity, private val email: String, private val name: String, private val age: String): Thread() {

    var activity: MainActivity = mainActivity
    var result = ""

    override fun run() {
        val url = URL(MainActivity.URL_WRITE)
        val connection: URLConnection = url.openConnection()

        connection.doOutput = true
        connection.doInput = true

        val os: OutputStream = connection.getOutputStream()
        val osw = OutputStreamWriter(os)
        osw.write("data={\"email\":\"${email}\", \"name\":\"${name}\", \"number\":${age}}")
        osw.flush()

        val iStream: InputStream = connection.getInputStream()
        val isr = InputStreamReader(iStream)
        val br = BufferedReader(isr)
        var line = br.readLine()
        result = ""

        while (line != null) {
            result += line
            line = br.readLine()
        }
        Log.w( "MainActivity", "result is $result")

        // Update GUI
        val updateGui = UpdateGui()
        activity.runOnUiThread(updateGui)
    }

    inner class UpdateGui : Runnable {
        override fun run() {
            Log.w( "MainActivity", "Inside UpdateGui:run" )
            activity.updateWriteViewWithJson(result)
        }
    }
}