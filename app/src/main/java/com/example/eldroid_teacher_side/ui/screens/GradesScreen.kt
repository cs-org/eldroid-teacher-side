package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.*
import androidx.compose.ui.text.font.FontWeight
import com.example.eldroid_teacher_side.ui.data.MockDataProvider

@Composable
fun GradeScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit
){
    var searchQuery by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf("CS202 - Data Structures") }
    
    val allStudents = MockDataProvider.courseStudents[selectedCourse] ?: emptyList()
    val filteredStudents = allStudents.filter {
        it.name.contains(searchQuery, ignoreCase = true) || it.id.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onThemeToggle) {
                    Icon(
                        imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = "Toggle Dark/Light Mode",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = { navController.navigate("notification") }) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item { 
                GradeDropDown(
                    selectedCourse = selectedCourse,
                    onCourseSelected = { selectedCourse = it }
                ) 
            }
            item { GradeSummary() }
            item {
                StudentSearch(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
            }
            
            items(filteredStudents) { student ->
                var midterm by remember(student.id, selectedCourse) { mutableStateOf(student.midterm) }
                var finals by remember(student.id, selectedCourse) { mutableStateOf(student.finals) }
                
                StudentGradeCard(
                    name = student.name,
                    id = student.id,
                    midtermValue = midterm,
                    onMidtermChange = { midterm = it },
                    finalsValue = finals,
                    onFinalsChange = { finals = it },
                    currentValue = if (midterm.isNotEmpty()) midterm else "0.0",
                    status = student.status
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                FinalizeGradesSection(onFinalizeClick = { })
            }
        }
    }
}
