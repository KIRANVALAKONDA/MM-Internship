package com.example.studysnap.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studysnap.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToScanner: () -> Unit,
    onNavigateToFlashcards: () -> Unit,
    onNavigateToQuiz: () -> Unit,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isCompact = maxWidth < 600.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(if (isCompact) 16.dp else 24.dp),
            verticalArrangement = Arrangement.spacedBy(if (isCompact) 20.dp else 24.dp)
        ) {
            WelcomeHeroCard(onGetStarted = onNavigateToScanner)

            LibrarySection(onNavigateToScanner = onNavigateToScanner)
        }
    }
}

@Composable
private fun LibrarySection(
    onNavigateToScanner: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "RECENT ACTIVITY",
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = GraySubtle,
            letterSpacing = 1.5.sp
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Your Library",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = DarkText
            )
            TextButton(
                onClick = { /* Placeholder: View all notes */ },
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    "VIEW ALL",
                    style = MaterialTheme.typography.labelMedium,
                    color = GraySubtle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = GraySubtle
                )
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 280.dp),
            shape = RoundedCornerShape(20.dp),
            color = Color(0xFFF8F9FB),
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFF1F3F8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(28.dp),
                        tint = GraySubtle
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Your library is empty",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = DarkText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Start by uploading your first study\nmaterial to see it here.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = GrayText,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onNavigateToScanner,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyDark,
                        contentColor = White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Add First Note", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun WelcomeHeroCard(
    onGetStarted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = NavyDark
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Teal),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "SnapStudy",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Text(
                text = buildAnnotatedString {
                    append("Your AI-powered study companion. ")
                    withStyle(SpanStyle(color = Teal)) {
                        append("Snap")
                    }
                    append(" your notes, and let AI generate ")
                    withStyle(SpanStyle(color = Teal)) {
                        append("flashcards")
                    }
                    append(" & ")
                    withStyle(SpanStyle(color = Teal)) {
                        append("quizzes")
                    }
                    append(" instantly.")
                },
                style = MaterialTheme.typography.bodyLarge,
                color = SidebarText,
                lineHeight = 24.sp
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WorkflowStep(
                    icon = Icons.Default.CameraAlt,
                    label = "Snap",
                    iconColor = Teal,
                    backgroundColor = Color(0xFF2A3042)
                )
                Text("→", color = SidebarText, fontWeight = FontWeight.Bold)
                WorkflowStep(
                    icon = Icons.Default.AutoAwesome,
                    label = "Process",
                    iconColor = NavyDark,
                    backgroundColor = QuizAmber
                )
                Text("→", color = SidebarText, fontWeight = FontWeight.Bold)
                WorkflowStep(
                    icon = Icons.Default.Style,
                    label = "Learn",
                    iconColor = Teal,
                    backgroundColor = Color(0xFF2A3042)
                )
            }

            Button(
                onClick = onGetStarted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Teal,
                    contentColor = White
                ),
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    "Get Started — Scan Your Notes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun WorkflowStep(
    icon: ImageVector,
    label: String,
    iconColor: Color,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = White
        )
    }
}

@Preview(
    name = "Home Screen — Mobile",
    showBackground = true,
    backgroundColor = 0xFFF8F9FC,
    widthDp = 380,
    heightDp = 800
)
@Composable
fun HomeScreenMobilePreview() {
    StudySnapTheme {
        HomeScreen(
            onNavigateToScanner = {},
            onNavigateToFlashcards = {},
            onNavigateToQuiz = {}
        )
    }
}
