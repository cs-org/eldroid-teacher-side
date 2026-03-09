package com.example.eldroid_teacher_side.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.FinalizeGradesSection
import com.example.eldroid_teacher_side.ui.components.GradeDropDown
import com.example.eldroid_teacher_side.ui.components.GradeSummary
import com.example.eldroid_teacher_side.R
import com.example.eldroid_teacher_side.ui.components.StudentGradeCard
import com.example.eldroid_teacher_side.ui.components.StudentSearch
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.BottomBar

@Composable
fun GradeScreen(navController: NavController){
    var searchQuery by remember { mutableStateOf("") }
    var midtermJulianne by remember { mutableStateOf("92") }
    var finalsJulianne by remember { mutableStateOf("") }

    var midtermLorenzo by remember { mutableStateOf("78") }
    var finalsLorenzo by remember { mutableStateOf("") }
    BaseScreen(
        title = "Grades",
        subtitle = "Faculty Portal",
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color(0xFF004020),
                    modifier = Modifier.size(24.dp)
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                GradeDropDown()
            }

            item {
                GradeSummary()
            }

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
                    midtermValue = midtermJulianne, // Pass the variable
                    onMidtermChange = { midtermJulianne = it }, // Update the variable
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
                FinalizeGradesSection(onFinalizeClick = {

                })
            }
        }
    }
}