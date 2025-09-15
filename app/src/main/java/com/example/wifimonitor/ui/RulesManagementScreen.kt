package com.example.wifimonitor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wifimonitor.model.Rule
import com.example.wifimonitor.model.RuleType
import com.example.wifimonitor.viewmodel.RulesViewModel
import java.text.SimpleDateFormat
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*

/**
 * Screen for managing parental control rules.
 * Displays list of rules and allows adding/editing/deleting them.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RulesManagementScreen(
    viewModel: RulesViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val rules by viewModel.rules.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Manage Rules") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        // Add back icon if needed
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Rule")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(rules) { rule ->
                RuleItem(
                    rule = rule,
                    onDelete = { viewModel.deleteRule(rule) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (showAddDialog) {
        AddRuleDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { rule ->
                viewModel.addRule(rule)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun RuleItem(rule: Rule, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${rule.type.name}: ${rule.value}",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Active: ${formatTimestamp(rule.activeFrom)} - ${formatTimestamp(rule.activeTo)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = onDelete) {
                // Add delete icon
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRuleDialog(
    onDismiss: () -> Unit,
    onConfirm: (Rule) -> Unit
) {
    var selectedType by remember { mutableStateOf(RuleType.BLOCK_DOMAIN) }
    var value by remember { mutableStateOf("") }
    var activeFrom by remember { mutableStateOf("") }
    var activeTo by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Rule") },
        text = {
            Column {
                // Rule type selector
                Text("Rule Type:")
                Row {
                    RuleType.values().forEach { type ->
                        RadioButton(
                            selected = selectedType == type,
                            onClick = { selectedType = type }
                        )
                        Text(type.name, modifier = Modifier.padding(start = 8.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Value (domain/package/time)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = activeFrom,
                    onValueChange = { activeFrom = it },
                    label = { Text("Active From (YYYY-MM-DD HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = activeTo,
                    onValueChange = { activeTo = it },
                    label = { Text("Active To (YYYY-MM-DD HH:mm)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Parse timestamps and create rule
                    val rule = Rule(
                        type = selectedType,
                        value = value,
                        activeFrom = parseTimestamp(activeFrom),
                        activeTo = parseTimestamp(activeTo)
                    )
                    onConfirm(rule)
                },
                enabled = value.isNotBlank()
            ) {
                Text("Add Rule")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun formatTimestamp(timestamp: com.google.firebase.Timestamp): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp.seconds * 1000))
}

private fun parseTimestamp(dateString: String): com.google.firebase.Timestamp {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = sdf.parse(dateString) ?: Date()
    return com.google.firebase.Timestamp(date.time / 1000, 0)
}