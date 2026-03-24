package com.example.studysnap.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysnap.R
import com.example.studysnap.model.Screen
import com.example.studysnap.ui.theme.*

@Composable
fun SnapStudyNavRail(
    currentRoute: String?,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .width(240.dp), // Increased width to 240dp to prevent layout issues (Issue 1 & 2)
        color = White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 20.dp, horizontal = 16.dp)
        ) {
            // ── Logo / Brand ──
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
            ) {
                // App Logo - Transparent background, adjusted size
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "App Logo",
                        modifier = Modifier.size(48.dp) // Adjusted to fit better
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "StudySnap",
                    style = MaterialTheme.typography.titleLarge, // Increased style for better visibility
                    fontWeight = FontWeight.ExtraBold,
                    color = BrandPurple,
                    letterSpacing = (-0.5).sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── MAIN MENU label ──
            Text(
                text = "MAIN MENU",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = SidebarText.copy(alpha = 0.8f),
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── Nav Items ──
            navItems.forEach { item ->
                val isSelected = currentRoute == item.screen.route
                val isAddNotes = item.screen == Screen.Scanner

                SidebarNavItem(
                    label = item.label,
                    icon = item.icon,
                    isSelected = isSelected,
                    showDot = isSelected && !isAddNotes,
                    onClick = { onNavigate(item.screen) }
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            // ── Bottom actions: Logout + Settings ──
            HorizontalDivider(
                color = NavySurface,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* Placeholder: logout */ }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Logout",
                        tint = SidebarText,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = { /* Placeholder: settings */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = SidebarText,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SidebarNavItem(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    showDot: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isSelected) NavySurface else Color.Transparent
    val textColor = if (isSelected) SidebarTextActive else SidebarText
    val iconTint = if (isSelected) SidebarTextActive else SidebarText

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        color = bgColor,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge, // Slightly larger font for better legibility
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = textColor,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            // Active dot indicator
            if (showDot) {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(SidebarActiveIndicator)
                )
            }
        }
    }
}

@Preview(
    name = "Sidebar / NavRail",
    showBackground = true,
    backgroundColor = 0xFF1A1F2E,
    widthDp = 240,
    heightDp = 600
)
@Composable
fun SnapStudyNavRailPreview() {
    StudySnapTheme {
        SnapStudyNavRail(
            currentRoute = Screen.Home.route,
            onNavigate = {}
        )
    }
}
