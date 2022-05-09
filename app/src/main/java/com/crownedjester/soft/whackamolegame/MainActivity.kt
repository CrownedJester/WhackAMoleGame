package com.crownedjester.soft.whackamolegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.crownedjester.soft.whackamolegame.presentation.game_screen.GameScreen
import com.crownedjester.soft.whackamolegame.presentation.result_screen.ResultScreen
import com.crownedjester.soft.whackamolegame.presentation.start_screen.GameStartScreen
import com.crownedjester.soft.whackamolegame.presentation.util.Screen
import com.crownedjester.soft.whackamolegame.ui.theme.WhackAMoleGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhackAMoleGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.GameStartScreen.route
                    ) {

                        composable(Screen.GameStartScreen.route) {
                            GameStartScreen(navController = navController)
                        }

                        composable(Screen.GameMainScreen.route) {
                            GameScreen()
                        }

                        composable(Screen.GameResultScreen.route) {
                            ResultScreen(navController = navController)
                        }

                    }

                }
            }
        }
    }
}