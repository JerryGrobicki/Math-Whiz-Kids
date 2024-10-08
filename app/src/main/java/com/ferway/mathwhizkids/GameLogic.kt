package com.ferway.mathwhizkids

import kotlin.random.Random

fun generateQuestion(selectedCategory: String): ArrayList<Any> {

    var number1 = Random.nextInt(0, 51)
    var number2 = Random.nextInt(0, 51)

    val textQuestion: String
    val correctAnswer: Int
    when (selectedCategory) {
        "add" -> {
            textQuestion = "$number1+$number2"
            correctAnswer = number1 + number2
        }

        "sub" -> {
            if (number1 > number2) {
                textQuestion = "$number1-$number2"
                correctAnswer = number1 - number2
            }
            else {
                textQuestion = "$number2-$number1"
                correctAnswer = number2 - number1
            }
        }

        "multi" -> {
            number1 = Random.nextInt(0, 11)
            number2 = Random.nextInt(0, 11)
            textQuestion = "$number1*$number2"
            correctAnswer = number1 * number2

        }

        else -> {
            if (number1 == 0 || number2 == 0) {
                textQuestion = "0 / 1"
                correctAnswer = 0 //15
            }
            else if (number1 >= number2) {
                val newBigNumber = number1 - (number1 % number2)
                textQuestion = "$newBigNumber/$number2"
                correctAnswer = newBigNumber / number2
            }
            else {
                val newBigNumber = number2 - (number2 % number1)
                textQuestion = "$newBigNumber/$number1"
                correctAnswer = newBigNumber / number1
            }
        }

    }
    val gameResultList = ArrayList<Any>()
    gameResultList.add(textQuestion)
    gameResultList.add(correctAnswer)
    return gameResultList
}