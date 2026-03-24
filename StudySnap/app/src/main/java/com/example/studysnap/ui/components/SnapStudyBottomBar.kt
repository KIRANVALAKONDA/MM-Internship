package com.example.studysnap.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysnap.model.Screen
import com.example.studysnap.ui.theme.*

data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

val navItems = listOf(
    NavItem(Screen.Home, "HOME", Icons.Default.Home),
    NavItem(Screen.Flashcards, "FLASHCARDS", Icons.Default.Style),
    NavItem(Screen.Scanner, "SCAN", Icons.Default.CameraAlt),
    NavItem(Screen.Quiz, "QUIZ", Icons.Default.Psychology),
)

@Composable
fun SnapStudyBottomBar(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(80.dp),
        containerColor = White,
        tonalElevation = 0.dp
    ) {
        navItems.forEach { item ->
            val isSelected = currentRoute == item.screen.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(item.screen) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        item.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        letterSpacing = 0.5.sp
                    )
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Teal,
                    unselectedIconColor = GraySubtle,
                    selectedTextColor = Teal,
                    unselectedTextColor = GraySubtle,
                    indicatorColor = White
                )
            )
        }
    }
}

@Preview(
    name = "Bottom Navigation",
    showBackground = true,
    widthDp = 400
)
@Composable
fun SnapStudyBottomBarPreview() {
    StudySnapTheme {
        SnapStudyBottomBar(
            currentRoute = Screen.Home.route,
            onNavigate = {}
        )
    }
}
