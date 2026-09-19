package com.example.wastewar.ui.user

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Recycling
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

// --- Data Models matching API Structure ---

data class ReuseIdea(
    val title: String,
    val description: String,
    val difficulty: String,
    val materials: List<String>,
    val steps: List<String>
)

data class AiSuggestionResponse(
    val item: String,
    val material: String,
    val condition: String,
    val reuseIdeas: List<ReuseIdea>,
    val recyclingAdvice: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiSuggestionScreen(
    mainNavController: NavController
) {
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var apiResult by remember { mutableStateOf<AiSuggestionResponse?>(null) }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        apiResult = null // Reset previous result on new selection
    }

    val context= LocalContext.current


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("AI Reuse & Recycle Helper") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Image Picker & Preview Section
            item {
                ImagePickerSection(
                    selectedImageUri = selectedImageUri,
                    onSelectImage = { photoPickerLauncher.launch("image/*") }
                )
            }

            // 2. Action Button
            if (selectedImageUri != null) {
                item {
                    Button(
                        onClick = {
                            isLoading = true
                            // Simulated API Trigger
                            apiResult = getMockResponse()
                            isLoading = false
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Analyze with AI")
                        }
                    }
                }
            }

            // 3. API Response Section
            apiResult?.let { result ->
                // Summary Card
                item { ItemSummaryCard(result) }

                // Reuse Ideas Title
                item {
                    Text(
                        text = "Reuse & Upcycling Ideas",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // List of Ideas
                items(result.reuseIdeas) { idea ->
                    ReuseIdeaCard(idea)
                }

                // Recycling Advice Card
                item { RecyclingAdviceCard(advice = result.recyclingAdvice) }
            }
        }
    }
}

// --- 1. Image Picker Component ---
@Composable
private fun ImagePickerSection(
    selectedImageUri: Uri?,
    onSelectImage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onSelectImage() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        if (selectedImageUri != null) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = "Selected Item Preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(12.dp),
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f)
                ) {
                    Text(
                        text = "Change Image",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AddPhotoAlternate,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Tap to upload or take a photo",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// --- 2. Item Summary Card ---
@Composable
private fun ItemSummaryCard(response: AiSuggestionResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = response.item,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InfoChip(label = "Material", value = response.material)
                InfoChip(label = "Condition", value = response.condition)
            }
        }
    }
}

@Composable
private fun InfoChip(label: String, value: String) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f)
    ) {
        Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Text(
                text = "$label: ",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

// --- 3. Reuse Idea Card ---
@Composable
private fun ReuseIdeaCard(idea: ReuseIdea) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = idea.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Difficulty Badge
                Surface(
                    color = when (idea.difficulty.lowercase()) {
                        "easy" -> MaterialTheme.colorScheme.primaryContainer
                        "medium" -> MaterialTheme.colorScheme.tertiaryContainer
                        else -> MaterialTheme.colorScheme.errorContainer
                    },
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = idea.difficulty,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = idea.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Required Materials Section
            if (idea.materials.isNotEmpty()) {
                Text(
                    text = "Materials Needed (${idea.materials.size}):",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                idea.materials.forEach { item ->
                    Text(
                        text = "• $item",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Steps Section
            if (idea.steps.isNotEmpty()) {
                Text(
                    text = "Steps (${idea.steps.size}):",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(4.dp))
                idea.steps.forEachIndexed { index, step ->
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = "${index + 1}. ",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

// --- 4. Recycling Advice Component ---
@Composable
private fun RecyclingAdviceCard(advice: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.4f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.Default.Recycling,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Recycling & Disposal Advice",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = advice,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// --- Mock Data Generator ---
private fun getMockResponse() = AiSuggestionResponse(
    item = "Glass Wine Bottle",
    material = "Glass",
    condition = "Usable",
    reuseIdeas = listOf(
        ReuseIdea(
            title = "Self-Watering Planter",
            description = "Convert the bottle into a self-watering bulb for indoor house potted plants.",
            difficulty = "Easy",
            materials = listOf("Glass Bottle", "Cotton Rope", "Water", "Glass Cutter (Optional)"),
            steps = listOf(
                "Clean the glass bottle thoroughly with warm water.",
                "Fill the bottle with fresh water.",
                "Submerge one end of the cotton rope in the bottle and thread the other into the soil."
            )
        ),
        ReuseIdea(
            title = "Decorative Table Lamp",
            description = "Insert fairy LED string lights inside to build an ambient night lamp.",
            difficulty = "Easy",
            materials = listOf("Glass Bottle", "Battery-operated LED Fairy Lights"),
            steps = listOf(
                "Ensure the interior of the bottle is completely dry.",
                "Feed the string lights slowly into the bottleneck.",
                "Secure the battery pack to the back or neck of the bottle."
            )
        )
    ),
    recyclingAdvice = "If broken or unwanted, place in the clear glass recycling container at your local municipal waste station. Remove plastic lids first."
)

