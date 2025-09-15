package com.example.wifimonitor.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.wifimonitor.model.UsageLog
import com.example.wifimonitor.viewmodel.UsageLogsViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Screen for viewing usage logs.
 * Displays historical data of child device activity.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsageLogsScreen(
    viewModel: UsageLogsViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val logs by viewModel.usageLogs.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usage Logs") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        // Add back icon
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            items(logs) { log ->
                UsageLogItem(log = log)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UsageLogItem(log: UsageLog) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Domain: ${log.domain}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "App: ${log.packageName}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Duration: ${log.duration / 60} min, Data: ${log.dataUsed / (1024 * 1024)} MB",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Time: ${formatTimestamp(log.timestamp)}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun formatTimestamp(timestamp: com.google.firebase.Timestamp): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(Date(timestamp.seconds * 1000))
}