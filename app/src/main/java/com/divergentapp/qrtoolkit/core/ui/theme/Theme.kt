package com.divergentapp.qrtoolkit.core.ui.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.divergentapp.qrtoolkit.domain.model.Settings

private val DarkColorScheme = darkColorScheme(

    primary = Color(0xFF71C7A4),
    onPrimary = Color(0xFF083126),
    primaryContainer = Color(0xFF174539),
    onPrimaryContainer = Color(0xFFCBEFDF),
    inversePrimary = Color(0xFF2F6B5D),

    secondary = Color(0xFF9BCFB8),
    onSecondary = Color(0xFF17362C),
    secondaryContainer = Color(0xFF2A4A3F),
    onSecondaryContainer = Color(0xFFD0EBDD),

    tertiary = Color(0xFFA8C8DA),
    onTertiary = Color(0xFF18313C),
    tertiaryContainer = Color(0xFF304956),
    onTertiaryContainer = Color(0xFFD7EAF6),

    background = Color(0xFF0F1715),
    onBackground = Color(0xFFEAF4ED),

    surface = Color(0xFF182C26),
    onSurface = Color(0xFFF4FCF4),

    surfaceVariant = Color(0xFF2C3A35),
    onSurfaceVariant = Color(0xFFC3D0C9),

    surfaceTint = Color(0xFF71C7A4),

    inverseSurface = Color(0xFFEAF4ED),
    inverseOnSurface = Color(0xFF1B2522),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),

    outline = Color(0xFF8B9C95),
    outlineVariant = Color(0xFF44514C),

    scrim = Color(0xFF000000),

    surfaceBright = Color(0xFF32403A),
    surfaceDim = Color(0xFF0D1412),

    surfaceContainerLowest = Color(0xFF0A1110),
    surfaceContainerLow = Color(0xFF111917),
    surfaceContainer = Color(0xFF17211E),
    surfaceContainerHigh = Color(0xFF1D2925),
    surfaceContainerHighest = Color(0xFF24332E),

    primaryFixed = Color(0xFFCBEFDF),
    primaryFixedDim = Color(0xFF71C7A4),
    onPrimaryFixed = Color(0xFF001E16),
    onPrimaryFixedVariant = Color(0xFF174539),

    secondaryFixed = Color(0xFFD0EBDD),
    secondaryFixedDim = Color(0xFF9BCFB8),
    onSecondaryFixed = Color(0xFF071914),
    onSecondaryFixedVariant = Color(0xFF344B43),

    tertiaryFixed = Color(0xFFD7EAF6),
    tertiaryFixedDim = Color(0xFFA8C8DA),
    onTertiaryFixed = Color(0xFF081A24),
    onTertiaryFixedVariant = Color(0xFF3C5562),
)

private val LightColorScheme = lightColorScheme(

    primary = Color(0xFF2F6B5D),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFCBEFDF),
    onPrimaryContainer = Color(0xFF0D2B22),
    inversePrimary = Color(0xFF71C7A4),

    secondary = Color(0xFF4C665D),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFD0EBDD),
    onSecondaryContainer = Color(0xFF12271F),

    tertiary = Color(0xFF556D7A),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFD7EAF6),
    onTertiaryContainer = Color(0xFF15242F),

    background = Color(0xFFF4FCF4),
    onBackground = Color(0xFF15211D),

    surface = Color(0xFF235347),
    onSurface = Color(0xFFFFFFFF),

    surfaceVariant = Color(0xFFE3EEE8),
    onSurfaceVariant = Color(0xFF52605A),

    surfaceTint = Color(0xFF2F6B5D),

    inverseSurface = Color(0xFF2D3733),
    inverseOnSurface = Color(0xFFF0F4F1),

    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),

    outline = Color(0xFF7B8D85),
    outlineVariant = Color(0xFFC7D4CD),

    scrim = Color(0xFF000000),

    surfaceBright = Color(0xFFFFFFFF),
    surfaceDim = Color(0xFFE7F0E8),

    surfaceContainerLowest = Color(0xFFFFFFFF),
    surfaceContainerLow = Color(0xFFF8FDF8),
    surfaceContainer = Color(0xFFF2F9F3),
    surfaceContainerHigh = Color(0xFFEBF4ED),
    surfaceContainerHighest = Color(0xFFEAF4FA),

    primaryFixed = Color(0xFFCBEFDF),
    primaryFixedDim = Color(0xFF8FD9B8),
    onPrimaryFixed = Color(0xFF001E16),
    onPrimaryFixedVariant = Color(0xFF174539),

    secondaryFixed = Color(0xFFD0EBDD),
    secondaryFixedDim = Color(0xFFBFE9D8),
    onSecondaryFixed = Color(0xFF071914),
    onSecondaryFixedVariant = Color(0xFF344B43),

    tertiaryFixed = Color(0xFF235347),
    tertiaryFixedDim = Color(0xFFB8D2E2),
    onTertiaryFixed = Color(0xFF081A24),
    onTertiaryFixedVariant = Color(0xFF3C5562),
)

@Composable
fun QRToolkitTheme(
    settings: Settings,
    content: @Composable BoxScope.() -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    val systemDarkTheme = isSystemInDarkTheme()
    val darkTheme =
        if (settings.useSystemTheme) {
            systemDarkTheme
        } else {
            settings.darkTheme
        }

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                CompositionLocalProvider(
                    LocalLayoutDirection provides
                            LayoutDirection.Ltr
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = innerPadding.calculateBottomPadding())
                            .background(MaterialTheme.colorScheme.background)
                            .padding(
                                top = innerPadding.calculateTopPadding(),
                                start = innerPadding.calculateStartPadding(layoutDirection),
                                end = innerPadding.calculateEndPadding(layoutDirection)
                            ),
                        contentAlignment = Alignment.Center,
                        content = content
                    )
                }
            }
        }
    )
}