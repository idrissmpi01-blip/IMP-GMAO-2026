kotlinpackage ga.chul.impgmao.ui

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import java.util.concurrent.Executor

data class ChulVisualTheme(val primaryColorHex: String = "#008080", val secondaryColorHex: String = "#FF9800")

@Composable
fun LoginScreen(activity: FragmentActivity, themeConfig: ChulVisualTheme = ChulVisualTheme(), onLoginSuccess: (String, Int, String) -> Unit, onShowMessage: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val chulPrimaryColor = Color(android.graphics.Color.parseColor(themeConfig.primaryColorHex))
    val biometricManager = remember { BiometricManager.from(context) }
    val isBiometricAvailable = remember { biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS }
    val executor: Executor = remember { ContextCompat.getMainExecutor(context) }
    val biometricPrompt = remember { BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
            super.onAuthenticationSucceeded(result)
            onLoginSuccess("TECHNICIEN_BIOMED", 2, "BIGNUMBA NZIENGUI")
            onShowMessage("✅ Connexion biométrique réussie !")
        }
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
            super.onAuthenticationError(errorCode, errString)
            onShowMessage("❌ Erreur Empreinte")
        }
    })}
    val promptInfo = remember { BiometricPrompt.PromptInfo.Builder().setTitle("Authentification CHUL").setSubtitle("Posez votre doigt sur le capteur").setNegativeButtonText("Utiliser mon mot de passe").build() }
    Column(modifier = Modifier.fillMaxSize().background(Color.White).padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(100.dp).background(chulPrimaryColor.copy(alpha = 0.1f), shape = CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Lock, contentDescription = null, modifier = Modifier.size(50.dp), tint = chulPrimaryColor) }
        Spacer(modifier = Modifier.height(16.dp))
        Text("🏥 CHUL — IMP-GMAO", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold, color = chulPrimaryColor)
        Text("Direction Technique", style = MaterialTheme.typography.subtitle1, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Identifiant Agent") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = password, onValueChange = { if (it.length <= 8) password = it }, label = { Text("Mot de passe (8 caractères)") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = { if (username.isNotEmpty() && password.length == 8) { if (username == "i.mboumba" && password == "A7x9P2mK") onLoginSuccess("ADMIN_GENERAL", 1, "MBOUMBA PAMBOU Idriss") else onLoginSuccess("TECHNICIEN", 2, "Agent") } }, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(backgroundColor = chulPrimaryColor)) { Text("Se connecter", color = Color.White) }
            if (isBiometricAvailable) { Button(onClick = { biometricPrompt.authenticate(promptInfo) }, modifier = Modifier.size(50.dp), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFFF3E0))) { Icon(Icons.Default.Fingerprint, contentDescription = null, tint = Color(0xFFFF9800)) } }
        }
    }
}
