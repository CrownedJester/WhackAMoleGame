package com.crownedjester.soft.whackamolegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.crownedjester.soft.whackamolegame.presentation.game_screen.GameScreen
import com.crownedjester.soft.whackamolegame.presentation.result_screen.ResultScreen
import com.crownedjester.soft.whackamolegame.presentation.start_screen.GameStartScreen
import com.crownedjester.soft.whackamolegame.presentation.util.Screen
import com.crownedjester.soft.whackamolegame.ui.theme.WhackAMoleGameTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
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
                            GameStartScreen(
                                navController = navController,
                                onFinishApp = {
                                    exitProcess(0)
                                }
                            )
                        }

                        composable(
                            route = Screen.GameMainScreen.route
                        ) {
                            GameScreen(navController = navController)
                        }

                        composable(
                            route = Screen.GameResultScreen.route + "?result={${Screen.RESULT_ARGUMENT_NAME}}",
                            arguments = listOf(
                                navArgument(Screen.RESULT_ARGUMENT_NAME) {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            ResultScreen(navController = navController)
                        }

                    }

                }
            }
        }
    }
}