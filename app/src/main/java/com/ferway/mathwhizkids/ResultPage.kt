package com.ferway.mathwhizkids

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ferway.mathwhizkids.ui.theme.MathWhizKidsTheme

@Composable
fun ResultPage(navController: NavController, score: Int) {
    val myContext = LocalContext.current as? Activity
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.result_page_bg_img), // Ensure this image exists
                contentScale = ContentScale.FillBounds
            )
            .padding(top = 400.dp), // Adding padding for better UI spacing
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Spacer(modifier = Modifier.weight(3f))

        Text(
            text = "Your score: $score",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxSize().padding(bottom = 240.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                    navController.popBackStack(route = "FirstPage", inclusive = false) // Ensure "FirstPage" exists in your navigation graph
                },
                modifier = Modifier.size(150.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.blue)) // Ensure R.color.blue exists
            ) {
                Text(
                    text = "Play again",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.blue)
                )
            }

            Button(
                onClick = {
                    myContext?.finish() // Exit the app
                },
                modifier = Modifier.size(150.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(2.dp, colorResource(id = R.color.blue)) // Ensure R.color.blue exists
            ) {
                Text(
                    text = "Exit",
                    fontSize = 20.sp,
                    color = colorResource(id = R.color.blue)
                )
            }
        }
        Spacer(modifier = Modifier.weight(2f))
    }
}

@Preview(showBackground = true)
@Composable
fun ResultPagePreview() {
    MathWhizKidsTheme {
        val navController = rememberNavController() // Mock NavController for preview
        ResultPage(navController = navController, score = 100) // Example score
    }
}
