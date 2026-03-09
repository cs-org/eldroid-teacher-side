    package com.example.eldroid_teacher_side.ui.components

    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.RowScope
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.WindowInsets
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.statusBarsPadding
    import androidx.compose.foundation.layout.width
    import androidx.compose.material.icons.filled.Menu
    import androidx.compose.material3.DrawerValue
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.ModalNavigationDrawer
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Surface
    import androidx.compose.material3.Text
    import androidx.compose.material3.rememberDrawerState
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import kotlinx.coroutines.launch

    @Composable
    fun BaseScreen(
        title: String,
        subtitle: String,
        navigationIcon: @Composable (() -> Unit)? = null,
        actions: @Composable (RowScope.() -> Unit)? = null,
        bottomBar: @Composable (() -> Unit)? = null,
        //Added te side navigation drawer
        navController: NavController? = null,
        content: @Composable (PaddingValues) -> Unit
    ) {
        //side navigation functionality
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                NavigationDrawerContent(onDestinationClicked = { route ->
                    scope.launch { drawerState.close() }
                    navController?.navigate(route)
                })
            }
        ) {
            Scaffold(
                topBar = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (navigationIcon != null) {
                                    navigationIcon()
                                } else {
                                    IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                        Icon(
                                            imageVector = androidx.compose.material.icons.Icons.Default.Menu,
                                            contentDescription = "Menu",
                                            tint = Color(0xFF1B3D2F),
                                            modifier = Modifier.size(28.dp)
                                        )
                                    }
                                }
                                Spacer(Modifier.width(16.dp))

                                Column {
                                    Text(
                                        text = title,
                                        color = Color(0xFF1B3D2F),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = subtitle,
                                        color = Color(0xFF5A6B81),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        letterSpacing = 1.sp
                                    )
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                actions?.invoke(this)
                            }

                        }
                    }
                },
                bottomBar = { bottomBar?.invoke() },
                content = { padding -> content(padding) }
            )
        }
    }