package com.ap.quizzer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class QuizCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_creation)
        val title_view = findViewById<EditText>(R.id.title)
        val desc_view = findViewById<EditText>(R.id.description)
        findViewById<Button>(R.id.create).setOnClickListener {
            val intent = Intent(baseContext,QuizGeneraterActivity::class.java)
            intent.putExtra("title",title_view.text.toString())
            intent.putExtra("description",desc_view.text.toString())
            startActivity(intent)
        }
    }
}
