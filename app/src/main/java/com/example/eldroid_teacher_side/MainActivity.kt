package com.example.eldroid_teacher_side

import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.telecom.Call
import android.view.RoundedCorner
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eldroid_teacher_side.ui.theme.EldroidteachersideTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.eldroid_teacher_side.ui.screens.DashboardScreen
import com.example.eldroid_teacher_side.util.DateModel
import com.example.eldroid_teacher_side.util.getCurrentWeekDays
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Place
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import com.example.eldroid_teacher_side.ui.components.BottomBar
import com.example.eldroid_teacher_side.ui.screens.AttendanceScreen
import com.example.eldroid_teacher_side.ui.screens.ScheduleScreen
import com.example.eldroid_teacher_side.util.generateCurrentWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EldroidteachersideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        GradeScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun GradeDropDown(){
    var expanded by remember { mutableStateOf(false) }
    var selectedCourse by remember { mutableStateOf("CS202 - Data Structures")}
    
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 14.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text (
                text = selectedCourse,
                color = Color(0xFF004020),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
            Icon(
                painter = painterResource(R.drawable.k_arrow_down),
                contentDescription = null,
                tint = Color(0xFF5A6B81)
            )
        }
    }

}

@Composable
fun GradeSummary(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ){
        SummaryCard(
            label = "AVG",
            value = "88.5%",
            containerColor = Color(0xFF004020),
            contentColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            label = "PENDING",
            value = "12",
            containerColor = Color.White,
            contentColor = Color(0xFF004020),
            hasBorder = true,
            modifier = Modifier.weight(1f)
        )
        SummaryCard(
            label = "STATUS",
            value = "DRAFT",
            containerColor = Color(0xFFF2EFE4),
            contentColor = Color(0xFFC5A347),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SummaryCard(
    label: String,
    value: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    hasBorder: Boolean = false
) {
    Card(
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = if (hasBorder) BorderStroke(1.dp, Color.LightGray.copy(0.5f)) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.ExtraBold,
                color = contentColor.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
        }
    }
}
@Composable
fun StudentSearch(query: String, onQueryChange: (String) -> Unit){
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = { Text("Search students...")},
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF1B3D2F))
        },
        trailingIcon = {
            if(query.isNotEmpty()){
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF1B3D2F),
            unfocusedBorderColor = Color.LightGray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Composable
fun StudentGradeCard(
    name: String,
    id: String,
    midtermValue: String,
    onMidtermChange: (String) -> Unit,
    finalsValue: String,
    onFinalsChange: (String) -> Unit,
    currentValue: String,
    status: String
){
    val (textColor, bgColor) = when (status.uppercase()) {
        "ON TRACK" -> Color(0xFF2E7D32) to Color(0xFFE8F5E9)
        "NEEDS REVIEW" -> Color(0xFFF57F17) to Color(0xFFFFF8E1)
        else -> Color.Gray to Color(0xFFF5F5F5)
    }

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header (Name & Status)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF004020))
                    Text("ID: $id", fontSize = 12.sp, color = Color.Gray)
                }
                Surface(color = bgColor, shape = RoundedCornerShape(12.dp)) {
                    Text(status, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold, color = textColor))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // INPUT ROW
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                GradeInputField("MIDTERM", midtermValue, onMidtermChange, Modifier.weight(1f))
                GradeInputField("FINALS", finalsValue, onFinalsChange, Modifier.weight(1f))
                // Keep Current as static or calculated
                GradeBox("CURRENT", currentValue, isPrimary = true, modifier = Modifier.weight(1f))
            }
        }
    }
}
@Composable
fun GradeInputField(label: String, value: String, onValueChange: (String) -> Unit, modifier: Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB0BEC5))
        Surface(
            modifier = Modifier.padding(top = 4.dp).fillMaxWidth().height(44.dp),
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFF8F9FA),
            border = BorderStroke(1.dp, Color.LightGray.copy(0.3f))
        ) {
            Box(contentAlignment = Alignment.Center) {
                 BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    textStyle = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF004020),
                        textAlign = TextAlign.Center
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) // Opens number pad
                )
            }
        }
    }
}
@Composable
fun GradeBox(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB0BEC5)

        )
        Surface(
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(44.dp),
            shape = RoundedCornerShape(8.dp),
            color = if (isPrimary) Color(0xFFF1F4F2) else Color(0xFFF8F9FA)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = value,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004020)
                )
            }
        }
    }
}
@Composable
fun FinalizeGradesSection(onFinalizeClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onFinalizeClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004020))
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Finalize Grades",
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "Finalizing will lock all input fields for the current term and notify students of their final standings.",
            style = TextStyle(
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}
@Composable
fun GradeScreen(){
    var searchQuery by remember { mutableStateOf("") }
    var midtermJulianne by remember { mutableStateOf("92") }
    var finalsJulianne by remember { mutableStateOf("") }

    var midtermLorenzo by remember { mutableStateOf("78") }
    var finalsLorenzo by remember { mutableStateOf("") }
    BaseScreen(
        title = "Grades",
        subtitle = "Faculty Portal",
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(R.drawable.arrow_left),
                    contentDescription = "Back",
                    tint = Color(0xFF004020),
                    modifier = Modifier.size(24.dp)
                )
            }
        },


       bottomBar = {
           BottomBar()
       }
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


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EldroidteachersideTheme {
        GradeScreen()
    }
}