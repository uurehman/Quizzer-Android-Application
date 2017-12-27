package com.ap.quizzer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException
import java.util.*
import android.widget.AdapterView.OnItemSelectedListener


class QuizSelectionActivity : AppCompatActivity(), Callback {
    lateinit var quiz_names:Spinner
    lateinit var quizzes:JSONArray
    lateinit var quiz_description:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_selection)
        quiz_names = findViewById(R.id.quizzes)
        quizzes = JSONArray()
        quiz_description = findViewById(R.id.description)
        quiz_names.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                quiz_description.setText(quizzes.getJSONObject(position).getString("description"))
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        findViewById<Button>(R.id.finish).setOnClickListener({
            val selected = quiz_names.selectedItemPosition
            if(selected>-1) {
                val intent = Intent(baseContext, QuizActivity::class.java)
                intent.putExtra("quiz", quizzes.getJSONObject(selected).toString())
                startActivity(intent)
            }
        })
        NetworkRequest.get("quiz/all",this)
    }

    override fun onFailure(call: Call?, e: IOException?) {
        runOnUiThread({ Toast.makeText(applicationContext,"Network Error!!", Toast.LENGTH_SHORT).show()})
    }

    override fun onResponse(call: Call?, response: Response?) {
        quizzes = JSONArray(response!!.body()!!.string())
        val names:LinkedList<String> = LinkedList<String>()
        for (i in 0..(quizzes.length() - 1)) {
            val quiz = quizzes.getJSONObject(i)
            names.add(quiz.getString("title"))
        }
        runOnUiThread({quiz_names.adapter=ArrayAdapter<String>(this,R.layout.text_view,names)})
    }
}
