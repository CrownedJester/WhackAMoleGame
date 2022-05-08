package com.crownedjester.soft.whackamolegame.presentation.util

sealed class Screen(val route: String) {

    object GameStartScreen : Screen("game_start_screen")
    object GameMainScreen : Screen("game_screen")
    object GameResultScreen : Screen("game_result_screen")

}
