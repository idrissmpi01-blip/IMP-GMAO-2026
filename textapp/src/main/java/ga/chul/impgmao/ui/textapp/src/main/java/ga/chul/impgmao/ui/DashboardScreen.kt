kotlinpackage ga.chul.impgmao.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun DashboardScreen(currentThemeColorHex: String = "#008080", userRole: String, agentName: String, onNavigateToScanner: () -> Unit) {
    val chulColor = Color(android.graphics.Color.parseColor(currentThemeColorHex))
    Scaffold(
        topBar = { TopAppBar(title = { Text("IMP-GMAO CHUL") }, backgroundColor = chulColor, contentColor = Color.White) },
        floatingActionButton = { FloatingActionButton(onClick = onNavigateToScanner, backgroundColor = Color(0xFFFF9800)) { Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = Color.White) } }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(Color(0xFFF5F5F5)).padding(16.dp)) {
            Card(modifier = Modifier.fillMaxWidth()) { Column(modifier = Modifier.padding(16.dp)) { Text("Bienvenue, $agentName", fontWeight = FontWeight.Bold); Text("Rôle : $userRole", color = Color.Gray) } }
        }
    }
}
