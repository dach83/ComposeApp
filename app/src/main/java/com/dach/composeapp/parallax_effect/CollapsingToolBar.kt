package com.dach.composeapp.parallax_effect

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.dach.composeapp.R


@Preview(showBackground = true)
@Composable
fun CollapsingToolBarPreview() {
    CollapsingToolBar()
}


@Composable
fun CollapsingToolBar() {
    val scroll: ScrollState = rememberScrollState(0)

    val headerHeight: Dp = 275.dp
    val headerHeightPx = with(LocalDensity.current) {
        headerHeight.toPx()
    }

    val toolbarHeight = 56.dp
    val toolbarHeightPx = with(LocalDensity.current) {
        toolbarHeight.toPx()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Header(scroll, headerHeight, headerHeightPx)
        Body(scroll, headerHeight)
        Toolbar(scroll, headerHeightPx, toolbarHeightPx)
        Title(scroll, headerHeight, headerHeightPx, toolbarHeight, toolbarHeightPx)
    }
}


@Composable
private fun Header(
    scroll: ScrollState,
    headerHeight: Dp,
    headerHeightPx: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight)
            .graphicsLayer {
                translationY = -scroll.value.toFloat() / 2f
                alpha = 1 - scroll.value / headerHeightPx
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.city),
            contentScale = ContentScale.FillBounds,
            contentDescription = "Background image",
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 3 * headerHeightPx / 4 // to wrap the title only
                    )
                )
        )
    }
}

@Composable
fun Body(
    scroll: ScrollState,
    headerHeight: Dp
) {
    Column(
        modifier = Modifier
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(headerHeight))

        repeat(5) {
            Text(
                text = stringResource(id = R.string.lorem_ipsum),
                style = MaterialTheme.typography.body1,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .background(Color(0XFF161616))
                    .padding(16.dp)
            )
        }
    }
}


@Composable
private fun Toolbar(
    scroll: ScrollState,
    headerHeightPx: Float,
    toolbarHeightPx: Float
) {
    val toolbarBottom = headerHeightPx - toolbarHeightPx
    val showToolbar by remember {
        derivedStateOf { scroll.value > toolbarBottom }
    }

    AnimatedVisibility(
        visible = showToolbar,
        enter = fadeIn(animationSpec = tween(durationMillis = 300)),
        exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        TopAppBar(
            modifier = Modifier.background(
                brush = Brush.horizontalGradient(
                    listOf(Color(0xff026586), Color(0xff032C45))
                )
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            },
            title = {},
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        )
    }
}

@Composable
private fun Title(
    scroll: ScrollState,
    headerHeight: Dp,
    headerHeightPx: Float,
    toolbarHeight: Dp,
    toolbarHeightPx: Float,
) {
    var titleHeightPx by remember {
        mutableStateOf(0f)
    }
    var titleWidthPx by remember {
        mutableStateOf(0f)
    }
    val titleHeightDp = with(LocalDensity.current) { titleHeightPx.toDp() }
    val titleWidthDp = with(LocalDensity.current) { titleWidthPx.toDp() }

    val collapseRange: Float = (headerHeightPx - toolbarHeightPx)
    val collapseFraction: Float = (scroll.value / collapseRange).coerceIn(0f, 1f)

    val titleFontScaleStart = 1f
    val titleFontScaleStop = 0.66f

    val scaleXY = lerp(
        titleFontScaleStart.dp,
        titleFontScaleStop.dp,
        collapseFraction
    )

    val titleExtraStartPadding = titleWidthDp * (1 - scaleXY.value) / 2

    val titleYStart = headerHeight - titleHeightDp - 16.dp
    val titleYStop = toolbarHeight / 2 - titleHeightDp / 2
    val titleYMiddle = headerHeight / 2

    val titleXStart = 16.dp
    val titleXStop = 72.dp - titleExtraStartPadding
    val titleXMiddle = titleXStop * 5 / 4

    Text(
        text = "New York",
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .graphicsLayer {
                translationY =
                    bezier(titleYStart, titleYMiddle, titleYStop, collapseFraction).toPx()
                translationX =
                    bezier(titleXStart, titleXMiddle, titleXStop, collapseFraction).toPx()

                scaleX = scaleXY.value
                scaleY = scaleXY.value
            }
            .onGloballyPositioned {
                titleHeightPx = it.size.height.toFloat()
                titleWidthPx = it.size.width.toFloat()
            }
    )
}

fun bezier(start: Dp, middle: Dp, stop: Dp, fraction: Float): Dp {
    val first = lerp(start, middle, fraction)
    val second = lerp(middle, stop, fraction)
    return lerp(first, second, fraction)
}