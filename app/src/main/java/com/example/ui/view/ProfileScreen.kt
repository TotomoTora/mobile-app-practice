package com.example.ui.view

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.practice.R
import com.example.data.RetrofitInstance
import com.example.practice.data.service.ProfileDto
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userId: String,          // uuid из Supabase auth.users
    accessToken: String      // access_token из signIn/signUp
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var isEditing by remember { mutableStateOf(false) }

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }

    var isLoading by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }
    // ---------- камера ----------
    val tmpImageUri = remember {
        val file = File(context.cacheDir, "profile_photo.jpg")
        FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) avatarUri = tmpImageUri
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) cameraLauncher.launch(tmpImageUri)
        else Toast.makeText(context, "Нужен доступ к камере", Toast.LENGTH_SHORT).show()
    }
    fun launchCamera() {
        val ok = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED
        if (ok) cameraLauncher.launch(tmpImageUri) else permissionLauncher.launch(Manifest.permission.CAMERA)
    }
    // ---------- загрузка профиля ----------
    LaunchedEffect(userId, accessToken) {
        isLoading = true
        try {
            val service = RetrofitInstance.userManagementService
            val list: List<ProfileDto> = service.getProfile(
                authHeader = "Bearer $accessToken",
                userIdFilter = "eq.$userId"
            )
            val profile = list.firstOrNull()
            if (profile != null) {
                firstName = profile.firstname.orEmpty()
                lastName = profile.lastname.orEmpty()
                address = profile.address.orEmpty()
                phone = profile.phone.orEmpty()
            } else {
                errorText = "Профиль не найден"
            }
        } catch (e: Exception) {
            errorText = "Не удалось загрузить профиль: ${e.localizedMessage}"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        containerColor = Color.White,
        bottomBar = { BottomBar(navController = navController, currentRoute = "profile") }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                TopHeader(isEditing = isEditing, onEditClick = { isEditing = !isEditing })

                Spacer(modifier = Modifier.height(24.dp))

                AvatarSection(
                    avatarUri = avatarUri,
                    onClick = { if (isEditing) launchCamera() }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "$firstName $lastName",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )

                Spacer(modifier = Modifier.height(24.dp))

                BarcodeCard()

                Spacer(modifier = Modifier.height(24.dp))

                ProfileField("Имя", firstName, { firstName = it }, isEditing)
                ProfileField("Фамилия", lastName, { lastName = it }, isEditing)
                ProfileField("Адрес", address, { address = it }, isEditing)
                ProfileField("Телефон", phone, { phone = it }, isEditing)



                Spacer(modifier = Modifier.height(80.dp))
            }

        }
    }

}
@Composable
fun TopHeader(isEditing: Boolean, onEditClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Профиль",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333),
            modifier = Modifier.align(Alignment.Center)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(32.dp)
                .clip(CircleShape)
                .background(if (isEditing) Color.Transparent else Color(0xFF48B2E7))
                .clickable { onEditClick() },
            contentAlignment = Alignment.Center
        ) {
            if (isEditing) {
                Text("Готово", fontSize = 12.sp, color = Color(0xFF48B2E7), fontWeight = FontWeight.Bold)
            } else {
                Icon(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = "Edit",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
