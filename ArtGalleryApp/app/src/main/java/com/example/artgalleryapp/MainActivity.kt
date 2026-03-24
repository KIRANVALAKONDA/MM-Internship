package com.example.artgalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artgalleryapp.ui.theme.ArtGalleryAppTheme

data class ArtWork(
    val id: Int,
    val imageResId: Int,
    val title: String,
    val artist: String,
    val isFavorite: Boolean = false
)

enum class Screen {
    Gallery,
    Detail,
    Favorites
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtGalleryAppTheme {
                ArtGalleryApp()
            }
        }
    }
}

@Composable
fun ArtGalleryApp() {
    var currentScreen by remember { mutableStateOf(Screen.Gallery) }
    var selectedArtWorkId by remember { mutableStateOf<Int?>(null) }
    
    val artWorks = remember {
        mutableStateListOf(
            ArtWork(1, R.drawable.art1, "Car", "Machine"),
            ArtWork(2, R.drawable.art2, "Deer", "Animal"),
            ArtWork(3, R.drawable.art3, "Nature", "Nature")
        )
    }

    val selectedArtWork = artWorks.find { it.id == selectedArtWorkId }

    Scaffold(
        bottomBar = {
            if (currentScreen != Screen.Detail) {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentScreen == Screen.Gallery,
                        onClick = { currentScreen = Screen.Gallery },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Gallery") },
                        label = { Text("Gallery") }
                    )
                    NavigationBarItem(
                        selected = currentScreen == Screen.Favorites,
                        onClick = { currentScreen = Screen.Favorites },
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Favorites") }
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (currentScreen) {
                Screen.Gallery -> GalleryScreen(artWorks) { art ->
                    selectedArtWorkId = art.id
                    currentScreen = Screen.Detail
                }
                Screen.Detail -> selectedArtWork?.let { art ->
                    DetailScreen(
                        artWork = art,
                        onBack = { 
                            currentScreen = Screen.Gallery 
                            selectedArtWorkId = null
                        },
                        onToggleFavorite = {
                            val index = artWorks.indexOfFirst { it.id == art.id }
                            if (index != -1) {
                                artWorks[index] = artWorks[index].copy(isFavorite = !artWorks[index].isFavorite)
                            }
                        }
                    )
                }
                Screen.Favorites -> FavoriteScreen(
                    favoriteArtWorks = artWorks.filter { it.isFavorite },
                    onArtWorkClick = { art ->
                        selectedArtWorkId = art.id
                        currentScreen = Screen.Detail
                    }
                )
            }
        }
    }
}

@Composable
fun GalleryScreen(artWorks: List<ArtWork>, onArtWorkClick: (ArtWork) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize().padding(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(artWorks) { art ->
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onArtWorkClick(art) }
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(art.imageResId),
                        contentDescription = art.title,
                        modifier = Modifier.size(150.dp).padding(8.dp)
                    )
                    Text(
                        text = art.title,
                        modifier = Modifier.padding(bottom = 8.dp),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun DetailScreen(artWork: ArtWork, onBack: () -> Unit, onToggleFavorite: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(onClick = onBack, modifier = Modifier.align(Alignment.Start)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }
        
        Card(
            modifier = Modifier
                .shadow(8.dp, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(artWork.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .padding(24.dp)
                    .size(300.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .background(Color(0xFFEDE7F6))
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = artWork.title,
                fontSize = 24.sp
            )
            Text(
                text = artWork.artist,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        IconButton(onClick = onToggleFavorite) {
            Icon(
                imageVector = if (artWork.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Toggle Favorite",
                tint = if (artWork.isFavorite) Color.Red else Color.Gray,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun FavoriteScreen(favoriteArtWorks: List<ArtWork>, onArtWorkClick: (ArtWork) -> Unit) {
    if (favoriteArtWorks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No favorites yet", fontSize = 18.sp, color = Color.Gray)
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            items(favoriteArtWorks) { art ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onArtWorkClick(art) }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(art.imageResId),
                            contentDescription = null,
                            modifier = Modifier.size(80.dp).padding(8.dp)
                        )
                        Column(modifier = Modifier.padding(start = 16.dp)) {
                            Text(text = art.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = art.artist, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Gallery Screen")
@Composable
fun GalleryScreenPreview() {
    val sampleArt = remember {
        listOf(
            ArtWork(1, R.drawable.art1, "Car", "Machine"),
            ArtWork(2, R.drawable.art2, "Deer", "Animal")
        )
    }
    ArtGalleryAppTheme {
        GalleryScreen(artWorks = sampleArt, onArtWorkClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    val sampleArt = ArtWork(1, R.drawable.art1, "Car", "Machine", isFavorite = true)
    ArtGalleryAppTheme {
        DetailScreen(artWork = sampleArt, onBack = {}, onToggleFavorite = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    val sampleArt = listOf(
        ArtWork(1, R.drawable.art1, "Car", "Machine", isFavorite = true)
    )
    ArtGalleryAppTheme {
        FavoriteScreen(favoriteArtWorks = sampleArt, onArtWorkClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun ArtGalleryAppPreview() {
    ArtGalleryAppTheme {
        ArtGalleryApp()
    }
}
