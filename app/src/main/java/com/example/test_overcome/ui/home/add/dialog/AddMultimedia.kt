package com.example.test_overcome.ui.home.add.dialog

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.test_overcome.R

@Composable
fun AddMultimedia(viewModel: AddTicketDialogFormViewModel) {
    Spacer(modifier = Modifier.height(20.dp))
    val owner = LocalLifecycleOwner.current
    var bitmapList by remember { mutableStateOf<List<Bitmap>>(emptyList()) }
    viewModel.images.observe(owner) { bitmapList = it }

    var showDialog by remember { mutableStateOf(false) }
    var showVideo by remember { mutableStateOf( false)}

    val uIState by viewModel.uIState.collectAsState()
    showDialog = uIState.imageDialog
    showVideo = uIState.videoDialog

    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 40.dp),
        onClick = { viewModel.setImageDialog(true) }) {
        Text(text = stringResource(id = R.string.home_dialog_image_button))
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_gallery),
            contentDescription = "",
            modifier = Modifier.size(30.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        onClick = { viewModel.setVideoDialog(true) }
    ) {
        Text(text = stringResource(id = R.string.home_dialog_media_button))
        Spacer(modifier = Modifier.width(10.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_video),
            contentDescription = "",
            modifier = Modifier.size(25.dp)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))
    if (showDialog) {
        AddImage(viewModel) {
            viewModel.setImageDialog(false)
        }
    }

    if (showVideo) {
        AddMedia(viewModel) {
            viewModel.setVideoDialog(false)
        }
    }


    if (bitmapList.isNotEmpty()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            ImagePreview(images = bitmapList)
        }
    }
}

@Composable
fun AddImage(viewModel: AddTicketDialogFormViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current

    val bitmapResult = remember { mutableStateOf<Bitmap?>(null) }

    var cameraPreview by remember { mutableStateOf<Bitmap?>(null) }
    val launcherCamera =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
            cameraPreview = it
        }

    var galeryUri by remember { mutableStateOf<Uri?>(null) }
    val launcherGalery =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            galeryUri = uri
        }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(modifier = Modifier) {
            Row(
                modifier = Modifier.padding(30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { launcherCamera.launch() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxWidth()
                    )
                    LaunchedEffect(cameraPreview) {
                        cameraPreview?.let { image ->
                            viewModel.addImageBitmap(image)
                            onDismiss()
                            cameraPreview = null
                        }
                    }
                }

                Spacer(modifier = Modifier.width(20.dp))

                IconButton(onClick = { launcherGalery.launch("image/*") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxWidth()
                    )
                    LaunchedEffect(galeryUri) {
                        galeryUri?.let {
                            if (Build.VERSION.SDK_INT < 28) {
                                bitmapResult.value = MediaStore.Images
                                    .Media.getBitmap(context.contentResolver, it)

                            } else {
                                val source = ImageDecoder
                                    .createSource(context.contentResolver, it)
                                bitmapResult.value = ImageDecoder.decodeBitmap(source)
                            }

                            bitmapResult.value?.let { bitmap ->
                                viewModel.addImageBitmap(bitmap)
                                onDismiss()
                                galeryUri = null
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddMedia(viewModel: AddTicketDialogFormViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current

    var videoUri by remember { mutableStateOf<Uri?>(null) }

    val launcherVideo =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            videoUri = uri
        }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(modifier = Modifier) {
            Column(
                modifier = Modifier.padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        launcherVideo.launch("video/*")
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_video),
                        contentDescription = "",
                        modifier = Modifier
                            .size(100.dp)
                            .fillMaxWidth()
                    )
                    LaunchedEffect(videoUri) {
                        videoUri?.let { uri ->
                            // Use Firebase Storage SDK to upload video to storage and Room database
                            viewModel.addVideo(uri)
                            onDismiss()
                            videoUri = null
                        }
                    }
                }
                Text(
                    text = "Select Video",
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}


@Composable
fun ImagePreview(images: List<Bitmap>) {
    images.forEach {
        Image(
            modifier = Modifier
                .size(80.dp),
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = it)
                    .apply(block = fun ImageRequest.Builder.() {
                        error(R.drawable.no_image)
                            .memoryCachePolicy(
                                CachePolicy.DISABLED
                            )
                            .diskCachePolicy(CachePolicy.DISABLED)
                    }).build()
            ),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Spacer(modifier = Modifier.width(10.dp))
    }
}