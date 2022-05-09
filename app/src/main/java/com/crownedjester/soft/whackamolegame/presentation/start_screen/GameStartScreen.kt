package com.crownedjester.soft.whackamolegame.presentation.start_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.crownedjester.soft.whackamolegame.R
import com.crownedjester.soft.whackamolegame.presentation.util.Screen
import com.crownedjester.soft.whackamolegame.ui.theme.LightOrange

@Composable
fun GameStartScreen(
    navController: NavController,
    onFinishApp: () -> Unit
) {

    var isRulesShown by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        RulesDialog(isShown = isRulesShown, onDialogDismiss = { isRulesShown = false })

        Image(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f),
            painter = painterResource(id = R.drawable.game_start),
            contentScale = ContentScale.FillBounds,
            contentDescription = "game start picture"
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            GameStartButtonsStyle(
                text = "Start",
                onClick = { navController.navigate(Screen.GameMainScreen.route) })

            Spacer(modifier = Modifier.height(BUTTON_SPACING.dp))

            GameStartButtonsStyle(text = "Rules", onClick = { isRulesShown = true })

            Spacer(modifier = Modifier.height(BUTTON_SPACING.dp))

            GameStartButtonsStyle(text = "Exit", onClick = onFinishApp)

            Spacer(modifier = Modifier.height(32.dp))

        }
    }

}

@Composable
private fun RulesDialog(isShown: Boolean, onDialogDismiss: () -> Unit) {
    if (isShown) {

        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {

            Dialog(
                onDismissRequest = onDialogDismiss,
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Text(
                    text = buildAnnotatedString {

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp,
                                letterSpacing = 1.sp
                            )
                        ) {
                            append(
                                "Rules:"
                            )
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                fontSize = 16.sp,
                                letterSpacing = 2.sp
                            )
                        ) {
                            append(
                                "\nThe rules is very simple. " +
                                        "Your main goal is whack as much moles as you can within given amount of the time.",
                            )
                        }

                    },
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun GameStartButtonsStyle(text: String, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier
            .size(196.dp, 48.dp)
            .shadow(elevation = 8.dp, RoundedCornerShape(BUTTON_CORNER_PERCENT)),
        onClick = onClick,
        contentPadding = PaddingValues(8.dp, 6.dp),
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(BUTTON_CORNER_PERCENT),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = LightOrange,
            contentColor = Color.White
        ),
        content = {
            Text(text = text, style = MaterialTheme.typography.h6)
        }
    )
}

private const val BUTTON_CORNER_PERCENT = 24
private const val BUTTON_SPACING = 16