package com.ap.quizzer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class QuizGeneraterActivity : AppCompatActivity() {
    var questions:JSONArray = JSONArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_generater)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val frame = findViewById<FrameLayout>(R.id.question)
        val mcq_true_false_creation_view = layoutInflater.inflate(R.layout.mcq_true_false_creater,null)
        val numeric_creation_view = layoutInflater.inflate(R.layout.numeric_creater,null)
        val opt_ids = arrayOf(R.id.op1,R.id.op2,R.id.op3,R.id.op4)
        val radio_ids = arrayOf(R.id.radioButton,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4)
        val qnum_text = findViewById<TextView>(R.id.qnum)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        findViewById<TextView>(R.id.title).setText(title)
        findViewById<Button>(R.id.add).setOnClickListener{
            val position = spinner.selectedItemPosition
            val question = JSONObject()
            val options = Array(4,{ i->""})
            var answer =""
            var ques = mcq_true_false_creation_view.findViewById<EditText>(R.id.question)
            if (position==0){
                for (i in 0 until 4) {
                    val radio_text = mcq_true_false_creation_view.findViewById<EditText>(opt_ids[i])
                    options[i] = radio_text.text.toString()
                    val radioBtn = mcq_true_false_creation_view.findViewById<RadioButton>(radio_ids[i])
                    if(radioBtn.isChecked)
                        answer = i.toString()
                    radio_text.setText("")
                }
            }else if(position==1){
                for (i in 0 until 2) {
                    val radioBtn = mcq_true_false_creation_view.findViewById<RadioButton>(radio_ids[i])
                    if(radioBtn.isChecked)
                        answer = i.toString()
                }
            }else{
                ques=numeric_creation_view.findViewById<EditText>(R.id.question)
                val ans_text=numeric_creation_view.findViewById<EditText>(R.id.answer)
                answer = ans_text.text.toString()
                ans_text.setText("")
            }
            question.put("question",ques.text.toString())
            question.put("answer",answer)
            question.put("type",position)
            question.put("options",JSONArray(options.toList()))
            ques.setText("")
            Toast.makeText(baseContext,"QUESTION ADDED!!",Toast.LENGTH_SHORT).show()
            qnum_text.text = (Integer.parseInt(qnum_text.text.toString())+1).toString()
            questions.put(question)
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                if (position==0){
                    frame.removeAllViews()
                    mcq_true_false_creation_view.findViewById<RadioButton>(R.id.radioButton3).visibility=View.VISIBLE
                    mcq_true_false_creation_view.findViewById<RadioButton>(R.id.radioButton4).visibility=View.VISIBLE
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op3).visibility=View.VISIBLE
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op4).visibility=View.VISIBLE
                    frame.addView(mcq_true_false_creation_view)
                }else if(position==1){
                    frame.removeAllViews()
                    mcq_true_false_creation_view.findViewById<RadioButton>(R.id.radioButton3).visibility=View.GONE
                    mcq_true_false_creation_view.findViewById<RadioButton>(R.id.radioButton4).visibility=View.GONE
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op3).visibility=View.GONE
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op4).visibility=View.GONE
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op1).setText("True")
                    mcq_true_false_creation_view.findViewById<EditText>(R.id.op2).setText("False")
                    frame.addView(mcq_true_false_creation_view)
                }else{
                    frame.removeAllViews()
                    frame.addView(numeric_creation_view)
                }
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                // your code here
            }
        }
        findViewById<Button>(R.id.create).setOnClickListener{
            val quiz = JSONObject()
            quiz.put("title",title)
            quiz.put("description",description)
            quiz.put("problems",questions)
            NetworkRequest.post("quiz",quiz.toString(),object : Callback {
                override fun onFailure(call: Call?, e: IOException?) {
                    runOnUiThread{Toast.makeText(baseContext,"Network Error! ",Toast.LENGTH_SHORT).show()}
                }
                override fun onResponse(call: Call?, response: Response?) {
                    finish()
                }
            })
        }
    }
}
