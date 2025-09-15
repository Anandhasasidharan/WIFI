package com.example.wifimonitor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wifimonitor.model.Child
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wifimonitor.viewmodel.ParentDashboardViewModel

/**
 * Main dashboard screen for parents to monitor their children.
 * Displays children list, rules, usage logs, and analysis.
 */
@Composable
fun ParentDashboardScreen(
    viewModel: ParentDashboardViewModel = viewModel()
) {
    val children by viewModel.children.collectAsState(initial = emptyList())
    val selectedChild by viewModel.selectedChild.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Wi-Fi Monitor Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )

        // Children selector
        ChildrenSelector(
            children = children,
            selectedChild = selectedChild,
            onChildSelected = { viewModel.selectChild(it) }
        )

        selectedChild?.let { child ->
            ChildDashboard(child = child)
        }
    }
}

@Composable
fun ChildrenSelector(
    children: List<Child>,
    selectedChild: Child?,
    onChildSelected: (Child) -> Unit
) {
    Row(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Select Child:", modifier = Modifier.align(Alignment.CenterVertically))
        Spacer(modifier = Modifier.width(8.dp))

        var expanded by remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            TextField(
                value = selectedChild?.name ?: "Select...",
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                children.forEach { child ->
                    DropdownMenuItem(
                        text = { Text(child.name) },
                        onClick = {
                            onChildSelected(child)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ChildDashboard(child: Child) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Dashboard for ${child.name}",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp)
        )

        // Navigation buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { /* Navigate to rules */ }) {
                Text("Manage Rules")
            }
            Button(onClick = { /* Navigate to logs */ }) {
                Text("View Logs")
            }
            Button(onClick = { /* Navigate to analysis */ }) {
                Text("Analysis")
            }
        }

        // TODO: Add sections for rules, logs, analysis
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                Text("Rules Section - Coming Soon", modifier = Modifier.padding(16.dp))
            }
            item {
                Text("Usage Logs Section - Coming Soon", modifier = Modifier.padding(16.dp))
            }
            item {
                Text("Analysis Section - Coming Soon", modifier = Modifier.padding(16.dp))
            }
        }
    }
}