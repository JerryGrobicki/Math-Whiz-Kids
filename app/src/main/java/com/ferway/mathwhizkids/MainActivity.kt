package com.ferway.mathwhizkids

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ferway.mathwhizkids.ui.theme.MathWhizKidsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathWhizKidsTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MyNavigation()
                }
            }
        }
    }
}

@Composable
fun MyNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "FirstPage") {
        composable(
            route = "FirstPage"
        ) {
            FirstPage(navController = navController)
        }
        composable(
            route = "SecondPage/{category}",
            arguments = listOf(
                navArgument("category") {
                    type = NavType.StringType
                }
            )
        ) {
            val selectedCategory = it.arguments?.getString("category")
            selectedCategory?.let {category ->
                SecondPage(navController = navController, category = category)
            }

        }
        composable(
            route = "ResultPage/{score}",
            arguments = listOf(
                navArgument("score") {
                    type = NavType.IntType
                }
            )
        ) {
            val userScore = it.arguments?.getInt("score")
            userScore?.let {score ->
                ResultPage(navController = navController, score = score)
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MathWhizKidsTheme {
        MyNavigation()
    }
}