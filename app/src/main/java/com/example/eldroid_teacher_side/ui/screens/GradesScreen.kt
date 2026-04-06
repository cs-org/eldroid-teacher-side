package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.*
import com.example.eldroid_teacher_side.viewmodels.CourseStudentsViewModel

@Composable
fun GradeScreen(
    navController: NavController,
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    onOpenDrawer: () -> Unit,
    viewModel: CourseStudentsViewModel // <-- Inject ViewModel
){
    var searchQuery by remember { mutableStateOf("") }

    // Collect the StateFlows from the ViewModel
    val courses by viewModel.courses.collectAsState()
    val selectedCourse by viewModel.selectedCourse.collectAsState()
    val studentGrades by viewModel.studentGrades.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // ... (Keep your existing Header Row with the Drawer/Theme/Notification icons here!) ...

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            // 1. The Dropdown
            item {
                GradeDropDown(
                    courses = courses,
                    selectedCourse = selectedCourse,
                    onCourseSelected = { viewModel.selectCourse(it) }
                )
            }

            // 2. The Summary (Now dynamic!)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    SummaryCard(
                        label = "AVG",
                        value = viewModel.getClassAverage(),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        label = "PENDING",
                        value = viewModel.getPendingCount(),
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.primary,
                        hasBorder = true,
                        modifier = Modifier.weight(1f)
                    )
                    SummaryCard(
                        label = "STATUS",
                        value = "DRAFT",
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // 3. Search Bar
            item {
                StudentSearch(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it }
                )
            }

            // 4. The Students! (Dynamically generated from the database)
            val filteredStudents = studentGrades.filter {
                it.students.last_name.contains(searchQuery, ignoreCase = true) ||
                        it.students.first_name.contains(searchQuery, ignoreCase = true)
            }

            if (filteredStudents.isEmpty() && courses.isNotEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }
            } else {
                items(filteredStudents) { gradeRecord ->
                    // We use remember(gradeRecord.id) so if the teacher switches courses, these clear out
                    var localMidterm by remember(gradeRecord.id) { mutableStateOf(gradeRecord.midterm_grade?.toString() ?: "") }
                    var localFinals by remember(gradeRecord.id) { mutableStateOf(gradeRecord.finals_grade?.toString() ?: "") }

                    StudentGradeCard(
                        name = "${gradeRecord.students.last_name}, ${gradeRecord.students.first_name}",
                        id = gradeRecord.students.student_id_number,
                        midtermValue = localMidterm,
                        onMidtermChange = { localMidterm = it },
                        finalsValue = localFinals,
                        onFinalsChange = { localFinals = it },
                        currentValue = gradeRecord.current_grade?.toString() ?: "0.0",
                        status = gradeRecord.status ?: "PENDING"
                    )
                }
            }

            item {
                Spacer(Modifier.height(16.dp))
                FinalizeGradesSection(onFinalizeClick = { })
            }
        }
    }
}