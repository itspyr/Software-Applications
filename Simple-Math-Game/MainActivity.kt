package com.example.assignmentone

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class MainActivity : AppCompatActivity() {

    //beginning score is 0
    var score = 0

    // streak to check # of correct answers in a row for toast msg
    var streak = 0

    // must be declared so initial "correctSign" is + (subject to change)
    var correctSign = '+'

    // keeping tally on how many rounds have passed for more in depth score
    var totalQuestions = 0

    lateinit var scoreText: TextView
    lateinit var feedbackText: TextView
    lateinit var answerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreText = findViewById(R.id.score)
        feedbackText = findViewById(R.id.feedback)
        answerText = findViewById(R.id.answer)

        scoreText.text = getString(R.string.score_start)
        generateQuestion()
    }

    fun generateQuestion() {
        val r = Random()

        // setting boundaries for integers random() can generate (two-digit numbers only)
        var num1 = r.nextInt(89) + 11   // 11–99
        var num2 = r.nextInt(89) + 10   // 10–98
        val signPick = r.nextInt(3)

        // ensuring the first number is always greater than the second
        if (num2 >= num1) {
            val temp = num1
            num1 = num2
            num2 = temp
        }

        totalQuestions++

        when (signPick) {
            0 -> {
                correctSign = '+'
                answerText.text = (num1 + num2).toString()
            }
            1 -> {
                correctSign = '-'
                answerText.text = (num1 - num2).toString()
            }
            else -> {
                correctSign = '*'
                answerText.text = (num1 * num2).toString()
            }
        }
        findViewById<TextView>(R.id.number1).text = num1.toString()
        findViewById<TextView>(R.id.number2).text = num2.toString()
    }

    // buttons and recursive calling of checkAnswer()
    fun plusButton(view: View) {
        checkAnswer('+')
    }

    fun minusButton(view: View) {
        checkAnswer('-')
    }

    fun multiplyButton(view: View) {
        checkAnswer('*')
    }

    // function checkAnswer() is the actual back end check to see if the chosen answer matches the correctSign
    fun checkAnswer(chosen: Char) {
        var message = ""

        if (chosen == correctSign) {
            score++
            streak++

            feedbackText.text = getString(R.string.correct_nice_job)
            feedbackText.setTextColor(getColor(android.R.color.holo_green_dark))

            if (streak == 3) {
                message = "Awesome job! 3 in a row!"
                // streak is reset if 3 is reached so toast can appear more than once
                streak = 0
            }

        } else {
            // there is no score-- here as it is no longer a normal counting system
            // streak is cleared back to 0 if answer is wrong
            streak = 0

            val correctAnswer = answerText.text.toString()
            feedbackText.text = getString(R.string.incorrect_the_correct_answer_was, correctAnswer)
            feedbackText.setTextColor(getColor(android.R.color.holo_red_dark))

            message = "Not quite. Try again."
        }

        // declaring toast message
        if (message != "") {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        scoreText.text = getString(R.string.score, score, totalQuestions)

        generateQuestion()
    }
    fun resetButton(view: View) {
        // reset all game values onclick
        score = 0
        streak = 0
        totalQuestions = 0
        correctSign = '+'

        // reset on screen text
        scoreText.text = getString(R.string.score_0_0)
        feedbackText.text = ""

        // generate a new question
        generateQuestion()
    }
}
