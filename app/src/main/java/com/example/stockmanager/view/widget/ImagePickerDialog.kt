package com.example.stockmanager.view.widget

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.stockmanager.R
import com.example.stockmanager.ext.createTempImageFile

@Composable
fun ImagePickerDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onTakePicture: (Uri?) -> Unit,
    onSelectImage: (Uri?) -> Unit,
) {
    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = TakePictureWithUri(),
        onResult = onTakePicture,
    )
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
        ) { isGranted ->
            if (isGranted) {
                val (uri, _) = context.createTempImageFile()
                cameraLauncher.launch(uri)
            }
        }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = onSelectImage,
    )

    val onCameraButtonClick = {
        onDismissRequest()
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }
    val onLibraryButtonClick = {
        onDismissRequest()
        imagePickerLauncher.launch(
            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = stringResource(id = R.string.select_image)) },
            text = {
                Column {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onCameraButtonClick,
                    ) {
                        Text(text = stringResource(id = R.string.take_picture))
                    }
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onLibraryButtonClick,
                    ) {
                        Text(text = stringResource(id = R.string.open_library))
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = stringResource(R.string.cancel))
                }
            },
        )
    }
}

class TakePictureWithUri : ActivityResultContract<Uri, Uri?>() {
    private lateinit var imageUri: Uri

    override fun createIntent(context: Context, input: Uri): Intent {
        imageUri = input
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, input)
    }

    override fun getSynchronousResult(context: Context, input: Uri): SynchronousResult<Uri?>? = null

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        return imageUri.takeIf { resultCode == Activity.RESULT_OK }
    }
}