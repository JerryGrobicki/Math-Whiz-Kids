package com.ferway.mathwhizkids

import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondPage(navController: NavController, category: String) {
    val myContext = LocalContext.current
    val life = remember { mutableIntStateOf(3) }
    val score = remember { mutableIntStateOf(0) }
    val remainingTimeText = remember { mutableStateOf("30") }
    val myQuestion = remember { mutableStateOf("What is 2 + 2?") }
    val myAnswer = remember { mutableStateOf("") }
    val isEnabled = remember { mutableStateOf(true) }
    val correctAnswer = remember { mutableIntStateOf(0) }
    val totalTimeInMillis = remember { mutableLongStateOf(30000L) }
    val timer = remember {
        mutableStateOf(
            object : CountDownTimer(totalTimeInMillis.longValue, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    remainingTimeText.value =
                        String.format(Locale.getDefault(), "%2d", millisUntilFinished / 1000)
                }

                override fun onFinish() {
                    cancel()
                    myQuestion.value = "Sorry, Time is up."
                    life.intValue -= 1
                    isEnabled.value = false
                }


            }.start()

        )
    }




    LaunchedEffect(key1 = "math", block = {
        val resultList = generateQuestion(category)
        myQuestion.value = resultList[0].toString()
        correctAnswer.intValue = resultList[1].toString().toInt()
        Log.d("question", myQuestion.value)
    })


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "back")
                    }
                },
                title = {
                    Text(
                        text =
                        when (category) {
                            "add" -> "Addition"
                            "sub" -> "Subtraction"
                            "multi" -> "Multiplication"
                            else -> "Division"
                        },
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.blue),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .paint(
                        painter = painterResource(id = R.drawable.second_page_bg_img),
                        contentScale = ContentScale.FillBounds
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                //Spacer(modifier = Modifier.height(5.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(text = "Life: ${life.intValue}", fontSize = 24.sp, color = Color.White)
                    Text(text = "Score: ${score.intValue}", fontSize = 24.sp, color = Color.White)
                    Text(
                        text = "Time: ${remainingTimeText.value}",
                        fontSize = 24.sp,
                        color = Color.White
                    )
                }
                //Spacer(modifier = Modifier.height(30.dp))
                TextForQuestion(text = myQuestion.value)
                Spacer(modifier = Modifier.height(5.dp))
                TextFieldForAnswer(text = myAnswer)
                Spacer(modifier = Modifier.height(300.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ButtonOkNext(
                        buttonText = "OK",
                        myOnClick = {

                            if (myAnswer.value.isEmpty()) {
                                Toast.makeText(
                                    myContext,
                                    "Write an answer or click the Next button.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                            else {

                                timer.value.cancel()
                                isEnabled.value = false
                                if (myAnswer.value.toInt() == correctAnswer.intValue) {

                                    score.intValue += 10//score.value = score.value + 10
                                    myQuestion.value = "Congratulations..."
                                    myAnswer.value = ""
                                }
                                else {

                                    life.intValue -= 1
                                    myQuestion.value = "Sorry, wrong answer!"
                                }
                            }


                        },
                        isEnabled = isEnabled.value
                    )
                    ButtonOkNext(
                        buttonText = "Next",
                        myOnClick = {
                            timer.value.cancel()
                            timer.value.start()

                            if (life.intValue == 0) {
                                Toast.makeText(
                                    myContext,
                                    "GAME OVER",
                                    Toast.LENGTH_SHORT
                                ).show()
                                timer.value.cancel()
                                navController.navigate("ResultPage/${score.value}"){
                                    popUpTo("FirstPage"){inclusive=false}

                                }


                            }
                            else {
                                val newResultList = generateQuestion(category)
                                myQuestion.value = newResultList[0].toString()
                                correctAnswer.intValue = newResultList[1].toString().toInt()
                                myAnswer.value = ""
                                isEnabled.value = true
                            }


                        },
                        isEnabled = true
                    )
                }
            }
        }
    )
}

