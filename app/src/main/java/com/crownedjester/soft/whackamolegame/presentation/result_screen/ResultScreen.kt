package com.crownedjester.soft.whackamolegame.presentation.result_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Replay
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.crownedjester.soft.whackamolegame.R
import com.crownedjester.soft.whackamolegame.presentation.util.Screen
import com.crownedjester.soft.whackamolegame.ui.theme.LightOrange

@Composable
fun ResultScreen(
    modifier: Modifier = Modifier,
    viewModel: ResultViewModel = hiltViewModel(),
    navController: NavController
) {

    val record by viewModel.gameRecord.collectAsState()
    val result by viewModel.gameResult.collectAsState()

    Box(
        modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (menuBackground,
                menuText,
                toStartScreenBtn,
                restartGameBtn,
                recordText,
                resultText
            ) = createRefs()

            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(id = R.drawable.game_end),
                contentDescription = "game end picture",
                contentScale = ContentScale.FillBounds
            )

            ResultBoardBackground(modifier = Modifier
                .constrainAs(menuBackground) {
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                    )
                    linkTo(
                        start = parent.start,
                        end = parent.end
                    )
                }
            )

            Text(
                text = "Game Result",
                modifier = Modifier
                    .zIndex(90f)
                    .constrainAs(menuText) {
                        linkTo(start = menuBackground.start, end = menuBackground.end)
                        top.linkTo(menuBackground.top, 24.dp)

                    },
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )

            Text(
                modifier = Modifier
                    .constrainAs(recordText) {
                        top.linkTo(menuText.bottom, DATA_TOP_MARGIN.dp)
                        start.linkTo(menuBackground.start, DATA_HORIZONTAL_MARGIN.dp)
                    },
                text = "Record: $record",
                style = MaterialTheme.typography.h6,
                fontStyle = FontStyle.Italic
            )

            Text(
                modifier = Modifier
                    .constrainAs(resultText) {
                        top.linkTo(menuText.bottom, DATA_TOP_MARGIN.dp)
                        end.linkTo(menuBackground.end, DATA_HORIZONTAL_MARGIN.dp)
                    },
                text = "Result: $result",
                style = MaterialTheme.typography.h6,
                fontStyle = FontStyle.Italic
            )

            MenuButton(modifier = Modifier
                .constrainAs(restartGameBtn) {
                    top.linkTo(recordText.bottom, BUTTON_TOP_MARGIN.dp)
                    start.linkTo(menuText.start, BUTTON_HORIZONTAL_MARGIN.dp)
                },
                vector = Icons.Default.Replay, onClick = {
                    navController.navigate(Screen.GameMainScreen.route)
                })

            MenuButton(
                modifier = Modifier.constrainAs(toStartScreenBtn) {
                    top.linkTo(recordText.bottom, BUTTON_TOP_MARGIN.dp)
                    end.linkTo(menuText.end, BUTTON_HORIZONTAL_MARGIN.dp)
                },
                vector = Icons.Default.Menu, onClick = {
                    navController.navigate(Screen.GameStartScreen.route)
                }
            )

        }
    }

}


@Composable
fun MenuButton(modifier: Modifier = Modifier, vector: ImageVector, onClick: () -> Unit) {

    IconButton(
        modifier = modifier
            .size(BUTTON_SIZE.dp)
            .shadow(elevation = 8.dp, clip = true)
            .background(
                color = LightOrange.copy(0.95f),
                shape = CircleShape
            ),
        onClick = onClick,
        content = {
            Icon(
                imageVector = vector,
                contentDescription = "menu button",
                tint = Color.Black
            )
        },
    )

}

@Composable
fun ResultBoardBackground(modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier.size(BOARD_WIDTH.dp, BOARD_HEIGHT.dp),
    ) {

        drawRoundRect(
            topLeft = Offset(0f, 0f),
            color = Color.Black,
            size = Size(size.width, size.height),
            cornerRadius = CornerRadius(24f, 24f),
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            alpha = 0.7f
        )

        drawRoundRect(
            topLeft = Offset(12f, 12f),
            color = Color(0xFFFFA726),
            size = Size(size.width - 24f, size.height - 24f),
            cornerRadius = CornerRadius(15f, 15f),
            style = Stroke(width = 20f, cap = StrokeCap.Round),
            alpha = 1f
        )

        drawRoundRect(
            topLeft = Offset(22f, 22f),
            color = Color.Black,
            size = Size(size.width - 42f, size.height - 42f),
            cornerRadius = CornerRadius(6f, 6f),
            style = Stroke(width = 4f, cap = StrokeCap.Round),
            alpha = 0.7f
        )

        drawRoundRect(
            topLeft = Offset(24f, 24f),
            color = Color(0xFF9CCC65),
            size = Size(size.width - 46f, size.height - 46f),
            cornerRadius = CornerRadius(6f, 6f),
            alpha = 0.8f
        )

    }

}

private const val BOARD_WIDTH = 256
private const val BOARD_HEIGHT = 192
private const val BUTTON_SIZE = 48
private const val DATA_HORIZONTAL_MARGIN = 24
private const val DATA_TOP_MARGIN = 10
private const val BUTTON_HORIZONTAL_MARGIN = 12
private const val BUTTON_TOP_MARGIN = 16