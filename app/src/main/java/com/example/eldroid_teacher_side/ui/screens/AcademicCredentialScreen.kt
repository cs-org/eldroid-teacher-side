package com.example.eldroid_teacher_side.ui.screens

import android.widget.Toast // Added for Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // Added to get context
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eldroid_teacher_side.ui.components.BaseScreen
import java.util.Calendar

data class Credential(
    val id: Int,
    val title: String,
    val institution: String,
    val year: String,
    val isDegree: Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcademicCredentialScreen(navController: NavController) {
    val darkGreen = Color(0xFF1B3D2F)
    val lightGreyBg = Color(0xFFF8F9FA)
    val goldAccent = Color(0xFFD4AF37)
    val degreeIconBg = Color(0xFFE8EDE5)
    val certIconBg = Color(0xFFFDF5E6)

    // Context for Toast messages
    val context = LocalContext.current

    // LIST STATE
    var credentials by remember {
        mutableStateOf(
            listOf(
                Credential(1, "Ph.D. in Computer Science", "Stanford University", "2018", true),
                Credential(2, "M.Sc. in Information Technology", "MIT", "2014", true),
                Credential(3, "Certified Ethical Hacker (CEH)", "EC-Council", "2021", false)
            )
        )
    }

    // ACADEMIC QUOTE STATE
    var academicQuote by remember { mutableStateOf("\"Dedicated to advancing the frontiers of cybersecurity through research and education.\"") }
    var showQuoteDialog by remember { mutableStateOf(false) }

    // DIALOG STATE (Used for both Adding and Editing)
    var showCredentialDialog by remember { mutableStateOf(false) }
    var editingCredential by remember { mutableStateOf<Credential?>(null) }

    var editTitle by remember { mutableStateOf("") }
    var editInstitution by remember { mutableStateOf("") }
    var editYear by remember { mutableStateOf("") }
    var editIsDegree by remember { mutableStateOf(true) }

    // Dropdown State for the Year Picker
    var yearDropdownExpanded by remember { mutableStateOf(false) }
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val yearsList = (1970..currentYear).toList().reversed().map { it.toString() }

    BaseScreen(
        title = "Personal Information",
        subtitle = "Settings",
        navController = navController,
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = darkGreen)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGreyBg)
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // HEADER SECTION
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Academic Credentials", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = darkGreen)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Manage your professional certifications and degrees", fontSize = 13.sp, color = Color.Gray, lineHeight = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Box(
                        modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(certIconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Outlined.School, contentDescription = null, tint = darkGreen)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // CREDENTIALS LIST
                credentials.forEach { cred ->
                    CredentialCard(
                        credential = cred,
                        iconBgColor = if (cred.isDegree) degreeIconBg else certIconBg,
                        iconColor = if (cred.isDegree) darkGreen else goldAccent,
                        onDelete = {
                            credentials = credentials.filter { it.id != cred.id }
                            Toast.makeText(context, "Credential removed", Toast.LENGTH_SHORT).show()
                        },
                        onEdit = {
                            editTitle = cred.title
                            editInstitution = cred.institution
                            editYear = cred.year
                            editIsDegree = cred.isDegree
                            editingCredential = cred
                            showCredentialDialog = true
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                // ACADEMIC PROFILE QUOTE CARD
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showQuoteDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = darkGreen)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ACADEMIC PROFILE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = goldAccent, letterSpacing = 1.sp)
                            Icon(Icons.Default.Edit, contentDescription = "Edit Quote", tint = goldAccent, modifier = Modifier.size(14.dp))
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = academicQuote,
                            fontSize = 15.sp,
                            color = Color(0xFFE0E0E0),
                            fontStyle = FontStyle.Italic,
                            lineHeight = 22.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
            }

            // FLOATING "ADD NEW" BUTTON
            ExtendedFloatingActionButton(
                onClick = {
                    editingCredential = null
                    editTitle = ""
                    editInstitution = ""
                    editYear = currentYear.toString()
                    editIsDegree = true
                    showCredentialDialog = true
                },
                modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
                containerColor = goldAccent,
                contentColor = darkGreen,
                shape = RoundedCornerShape(24.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add New", fontWeight = FontWeight.Bold)
            }
        }
    }

    // --- DIALOGS ---

    // 1. Edit Quote Dialog
    if (showQuoteDialog) {
        var tempQuote by remember { mutableStateOf(academicQuote) }
        AlertDialog(
            onDismissRequest = { showQuoteDialog = false },
            title = { Text("Edit Academic Profile") },
            text = {
                OutlinedTextField(
                    value = tempQuote,
                    onValueChange = { tempQuote = it },
                    label = { Text("Quote") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 4
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    academicQuote = tempQuote
                    showQuoteDialog = false
                    Toast.makeText(context, "Academic profile updated!", Toast.LENGTH_SHORT).show()
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showQuoteDialog = false }) { Text("Cancel") }
            }
        )
    }

    // 2. Add/Edit Credential Dialog
    if (showCredentialDialog) {
        AlertDialog(
            onDismissRequest = { showCredentialDialog = false },
            title = { Text(if (editingCredential == null) "Add Credential" else "Edit Credential") },
            text = {
                Column {
                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = { editTitle = it },
                        label = { Text("Degree / Certification Title") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editInstitution,
                        onValueChange = { editInstitution = it },
                        label = { Text("Institution") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = yearDropdownExpanded,
                        onExpandedChange = { yearDropdownExpanded = !yearDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = editYear,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Year") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearDropdownExpanded) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier.menuAnchor().fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = yearDropdownExpanded,
                            onDismissRequest = { yearDropdownExpanded = false }
                        ) {
                            yearsList.forEach { year ->
                                DropdownMenuItem(
                                    text = { Text(year) },
                                    onClick = {
                                        editYear = year
                                        yearDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = editIsDegree, onCheckedChange = { editIsDegree = it })
                        Text("This is a University Degree")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (editingCredential == null) {
                        // Adding new
                        val newId = (credentials.maxOfOrNull { it.id } ?: 0) + 1
                        credentials = credentials + Credential(newId, editTitle, editInstitution, editYear, editIsDegree)
                        Toast.makeText(context, "Credential added successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Editing existing
                        credentials = credentials.map {
                            if (it.id == editingCredential!!.id) {
                                it.copy(title = editTitle, institution = editInstitution, year = editYear, isDegree = editIsDegree)
                            } else it
                        }
                        Toast.makeText(context, "Credential updated successfully!", Toast.LENGTH_SHORT).show()
                    }
                    showCredentialDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showCredentialDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun CredentialCard(
    credential: Credential,
    iconBgColor: Color,
    iconColor: Color,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(RoundedCornerShape(12.dp)).background(iconBgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = if (credential.isDegree) Icons.Outlined.School else Icons.Outlined.WorkspacePremium, contentDescription = null, tint = iconColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = credential.title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "${credential.institution} • ${credential.year}", fontSize = 12.sp, color = Color.Gray)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(48.dp)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.Gray, modifier = Modifier.size(18.dp).clickable { onEdit() })
                Icon(Icons.Default.DeleteOutline, contentDescription = "Delete", tint = Color.Gray, modifier = Modifier.size(18.dp).clickable { onDelete() })
            }
        }
    }
}