package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.*
import androidx.compose.ui.text.font.FontWeight

@Composable
fun GradeScreen(navController: NavController){
    var searchQuery by remember { mutableStateOf("") }
    var midtermJulianne by remember { mutableStateOf("92") }
    var finalsJulianne by remember { mutableStateOf("") }

    var midtermLorenzo by remember { mutableStateOf("78") }
    var finalsLorenzo by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header without back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Grades",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Faculty Portal",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { GradeDropDown() }
            item { GradeSummary() }
            item {
                StudentSearch(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
            }
            item {
                StudentGradeCard(
                    name = "Abad, Julianne Marie",
                    id = "2021-00124",
                    midtermValue = midtermJulianne,
                    onMidtermChange = { midtermJulianne = it },
                    finalsValue = finalsJulianne,
                    onFinalsChange = { finalsJulianne = it },
                    currentValue = "92.0",
                    status = "ON TRACK"
                )
            }
            item {
                StudentGradeCard(
                    name = "Bautista, Lorenzo",
                    id = "2021-00156",
                    midtermValue = midtermLorenzo,
                    onMidtermChange = { midtermLorenzo = it },
                    finalsValue = finalsLorenzo,
                    onFinalsChange = { finalsLorenzo = it },
                    currentValue = "78.0",
                    status = "NEEDS REVIEW"
                )
            }
            item {
                Spacer(Modifier.height(16.dp))
                FinalizeGradesSection(onFinalizeClick = { })
            }
        }
    }
}
