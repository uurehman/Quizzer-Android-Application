package com.ap.quizzer

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.json.JSONObject
import org.json.JSONArray
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import android.content.DialogInterface
import android.content.DialogInterface.BUTTON_NEUTRAL
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView


class QuizActivity : AppCompatActivity() {
    lateinit var questions:JSONArray
    val radio_ids = arrayOf(R.id.radioButton,R.id.radioButton2,R.id.radioButton3,R.id.radioButton4)
    lateinit var selected:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        val quiz = JSONObject(intent.getStringExtra("quiz"))
        questions = quiz.getJSONArray("_problems")
        val quiz_view = findViewById<ListView>(R.id.quiz)
        quiz_view.adapter = QuizAdapter()
        val alertDialog = AlertDialog.Builder(this).create()
        selected = Array(questions.length(),{i->""})
        alertDialog.setTitle("Your Score is: ")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", { dialog, which -> dialog.dismiss() })
        alertDialog.setOnDismissListener({
            finish()
        })
        findViewById<Button>(R.id.finish).setOnClickListener {
            /** get all values of the EditText-Fields */
            var score = 0
            for (i in 0 until selected.size) {
                if (selected[i] == "")
                    continue

                val question = questions.getJSONObject(i)
                val ans = question.getString("answer")
                val type = question.getInt("type")
                if (type == 0) {
                    if (Integer.parseInt(selected[i]) == Integer.parseInt(ans)-1)
                        score++
                } else if(type==1) {
                    if (ans == "true" && selected[i] == "0" || ans == "false" && selected[i] == "1")
                        score++
                }else{
                    if (selected[i] == ans)
                        score++
                }
            }
            alertDialog.setMessage(score.toString() + "/" + questions.length().toString())
            alertDialog.show()
        }
    }

    private inner class QuizAdapter internal constructor() : BaseAdapter() {

        internal var inflater: LayoutInflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        override fun getCount(): Int {
            return questions.length()
        }

        override fun getItem(position: Int): JSONObject {
            return questions.getJSONObject(position)
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var CurrentView:View?=convertView
            val quiz = getItem(position)
            val type = quiz.getInt("type")
            if(convertView!=null){
                val data: Array<*> = convertView.tag as Array<*>
                if(data[0]==0||data[0]==1)
                    for (i in 0 until 4){
                        if(convertView.findViewById<RadioButton>(radio_ids[i]).isChecked)
                            selected[data[1] as Int]=i.toString()
                    }
                else
                    selected[data[1] as Int]=convertView.findViewById<EditText>(R.id.numeric_ans).text.toString()
            }

            if(type<2) {
                CurrentView = inflater.inflate(R.layout.mcq_true_false_template, parent, false)
                if(type==1) {
                    CurrentView.findViewById<RadioButton>(R.id.radioButton3).visibility=View.GONE
                    CurrentView.findViewById<RadioButton>(R.id.radioButton4).visibility=View.GONE
                }
            }
            else if(type==2)
                CurrentView = inflater.inflate(R.layout.numeric_template, parent, false)

            CurrentView!!.findViewById<TextView>(R.id.question).text = quiz.getString("question")
            if(type<2){
                val options= quiz.getJSONArray("options")
                if(type==0) {
                    for (i in 0 until 4){
                        CurrentView.findViewById<RadioButton>(radio_ids[i]).text = options.getString(i)
                    }
                }else{
                    CurrentView.findViewById<RadioButton>(R.id.radioButton).text = "True"
                    CurrentView.findViewById<RadioButton>(R.id.radioButton2).text = "False"
                }
                if(selected[position]!="") {
                    val pos = Integer.parseInt(selected[position])
                    CurrentView.findViewById<RadioButton>(radio_ids[pos]).isChecked=true
                }
            }else{
                CurrentView.findViewById<EditText>(R.id.numeric_ans).setText(selected[position])
            }
            CurrentView.setTag(arrayOf(type,position))
            return CurrentView
        }
    }
}
