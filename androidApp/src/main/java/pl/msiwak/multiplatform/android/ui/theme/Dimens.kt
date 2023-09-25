package pl.msiwak.multiplatform.android.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LocalDim = staticCompositionLocalOf { Dimensions() }

data class Dimensions(
    val space_0: Dp = 0.dp,
    val space_1: Dp = 1.dp,
    val space_2: Dp = 2.dp,
    val space_4: Dp = 4.dp,
    val space_8: Dp = 8.dp,
    val space_12: Dp = 12.dp,
    val space_16: Dp = 16.dp,
    val space_24: Dp = 24.dp,
    val space_32: Dp = 32.dp,
    val space_36: Dp = 36.dp,
    val space_64: Dp = 64.dp,
    val space_80: Dp = 80.dp,
    val space_96: Dp = 96.dp,
    val space_132: Dp = 132.dp,
    val space_164: Dp = 164.dp,
    val space_264: Dp = 264.dp,
    val first_list_item_size: Dp = 116.dp,
    val second_list_item_size: Dp = 132.dp,
    val bottom_navigation_elevation: Dp = 10.dp,
    val bottom_navigation_divider_width: Dp = 2.dp,
    val border_width: Dp = 2.dp,
    val requirements_icon_size: Dp = 16.dp,
)

val MaterialTheme.dimens: Dimensions
    @Composable
    @ReadOnlyComposable
    get() = LocalDim.current

val LocalFont = staticCompositionLocalOf { Font() }

data class Font(
    val font_8: TextUnit = 8.sp,
    val font_12: TextUnit = 12.sp,
    val font_14: TextUnit = 14.sp,
    val font_16: TextUnit = 16.sp,
    val font_20: TextUnit = 20.sp,
    val font_24: TextUnit = 24.sp,
)

val MaterialTheme.font: Font
    @Composable
    @ReadOnlyComposable
    get() = LocalFont.current
