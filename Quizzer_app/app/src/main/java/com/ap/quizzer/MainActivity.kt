package com.ap.quizzer

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.support.v7.widget.AppCompatEditText
import org.json.JSONObject
import android.widget.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException


class MainActivity : AppCompatActivity() {
    lateinit var username: AutoCompleteTextView
    lateinit var password: AppCompatEditText
    lateinit var progress:ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        username = findViewById(R.id.username)
        password = findViewById(R.id.password)

        progress=findViewById(R.id.progress)
        val data = getSharedPreferences("data", Context.MODE_PRIVATE)
        NetworkRequest.ip="http://"+data.getString("ip","")+":8080/"
        val ip_text_view = EditText(this)

        // 1. Instantiate an AlertDialog.Builder with its constructor
        val builder = AlertDialog.Builder(this)

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setTitle("Enter Server IP")
        builder.setView(ip_text_view)
        builder.setPositiveButton("Ok", { dialog, whichButton ->
            NetworkRequest.ip="http://"+ip_text_view.text.toString()+":8080/"
            data.edit().putString("ip",ip_text_view.text.toString()).apply()
        })

        // 3. Get the AlertDialog from create()
        val ip_dialog = builder.create()

        findViewById<Button>(R.id.ip_change).setOnClickListener({
            ip_text_view.setText(data.getString("ip",""))
            ip_dialog.show()
        })
    }

    fun login(v: View){
        val tosend = JSONObject()
        tosend.put("username", username.text)
        tosend.put("password", password.text)
        val handler = object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                runOnUiThread({
                    progress.visibility=View.GONE
                    Toast.makeText(applicationContext,"Network Error!!",Toast.LENGTH_SHORT).show()})
            }

            override fun onResponse(call: Call?, response: Response?) {
                progress.visibility=View.GONE
                var reply = -1
                try {
                    reply = Integer.parseInt(response!!.body()!!.string())
                }catch (e:Exception){
                    e.printStackTrace()
                }
                if (reply > 0) {
                    if (reply == 1)
                        startActivity(Intent(applicationContext, QuizCreationActivity::class.java))
                    else
                        startActivity(Intent(applicationContext, QuizSelectionActivity::class.java))
                }
                else
                    runOnUiThread({Toast.makeText(applicationContext,"Invalid Username or Password!!",Toast.LENGTH_SHORT).show()})
            }
        }
        //progress.visibility=View.VISIBLE
        NetworkRequest.post("users/sign_in", tosend.toString(),handler)
    }
}
