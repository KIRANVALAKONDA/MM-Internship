package com.example.studysnap.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studysnap.ui.theme.*

@Composable
fun NoteScannerScreen(
    modifier: Modifier = Modifier
) {
    var isProcessing by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Scan Notes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = DarkText
        )

        // ── Camera Preview Placeholder ──
        // Placeholder for CameraX integration
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
            color = GrayCard,
            shape = RoundedCornerShape(20.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(GrayBorder),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            modifier = Modifier.size(28.dp),
                            tint = GraySubtle
                        )
                    }
                    Text(
                        text = "[Camera Preview Area]",
                        style = MaterialTheme.typography.titleMedium,
                        color = GraySubtle
                    )
                }
            }
        }

        // ── Input Action Buttons ──
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionButton(
                    text = "Capture Photo",
                    icon = Icons.Default.CameraAlt,
                    onClick = {
                        isProcessing = true
                        // Placeholder for camera capture logic
                    },
                    modifier = Modifier.weight(1f)
                )
                ActionButton(
                    text = "Upload Image",
                    icon = Icons.Default.Image,
                    onClick = {
                        isProcessing = true
                        // Placeholder for image picker logic
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ActionButton(
                    text = "Upload PDF",
                    icon = Icons.Default.PictureAsPdf,
                    onClick = {
                        isProcessing = true
                        // Placeholder for PDF picker logic
                    },
                    modifier = Modifier.weight(1f)
                )
                ActionButton(
                    text = "Paste Text",
                    icon = Icons.Default.ContentPaste,
                    onClick = {
                        isProcessing = true
                        // Placeholder for text input dialog
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // ── Processing Status ──
        AnimatedVisibility(
            visible = isProcessing,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            ProcessingStatusCard()
        }
    }
}

@Composable
private fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = DarkText
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, GrayBorder)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(18.dp),
            tint = Teal
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun ProcessingStatusCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = TealContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(28.dp),
                color = Teal,
                strokeWidth = 3.dp
            )
            // Placeholder for OCR text extraction
            Text("Extracting text...", style = MaterialTheme.typography.bodyMedium, color = TealDark)
            // Placeholder for AI processing notes
            Text("AI processing notes...", style = MaterialTheme.typography.bodySmall, color = TealDark.copy(alpha = 0.7f))
            // Placeholder for AI flashcard generation
            Text("Generating flashcards...", style = MaterialTheme.typography.bodySmall, color = TealDark.copy(alpha = 0.7f))
            // Placeholder for quiz generation
            Text("Creating quizzes...", style = MaterialTheme.typography.bodySmall, color = TealDark.copy(alpha = 0.7f))
        }
    }
}

// ── Previews ──

@Preview(
    name = "Note Scanner",
    showBackground = true,
    backgroundColor = 0xFFF8F9FC,
    widthDp = 400,
    heightDp = 800
)
@Composable
fun NoteScannerScreenPreview() {
    StudySnapTheme {
        NoteScannerScreen()
    }
}
