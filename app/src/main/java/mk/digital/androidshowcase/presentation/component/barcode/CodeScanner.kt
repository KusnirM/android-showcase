package mk.digital.androidshowcase.presentation.component.barcode

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

@Suppress("CognitiveComplexMethod")
@Composable
 fun CodeScanner(
    onScanned: (String) -> Unit,
    onError: (String) -> Unit,
    modifier: Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val options = createBarcodeScannerOptions()
    val scanner: BarcodeScanner = remember { createBarcodeScanning(options) }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            val previewView: PreviewView = createPreviewView(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview: Preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                val analysis: ImageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .apply {
                        setAnalyzer(ContextCompat.getMainExecutor(ctx)) { imageProxy ->
                            val mediaImage = imageProxy.image
                            if (mediaImage != null) {
                                val image = InputImage.fromMediaImage(
                                    /* image = */ mediaImage,
                                    /* rotationDegrees = */ imageProxy.imageInfo.rotationDegrees
                                )
                                scanner.process(image)
                                    .addOnSuccessListener { barcodes ->
                                        for (barcode in barcodes) {
                                            barcode.rawValue?.let {
                                                onScanned(it)
                                            }
                                        }
                                    }
                                    .addOnFailureListener {
                                        onError(it.message ?: "Scan failed")
                                    }
                                    .addOnCompleteListener {
                                        imageProxy.close()
                                    }
                            } else {
                                imageProxy.close()
                            }
                        }
                    }
                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner = lifecycleOwner,
                        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
                        preview, analysis
                    )
                } catch (exc: IllegalStateException) {
                    onError(exc.message ?: "Camera binding failed")
                } catch (exc: IllegalArgumentException) {
                    onError(exc.message ?: "Camera configuration invalid")
                }
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        }
    )
}

private fun createBarcodeScanning(options: BarcodeScannerOptions) =
    BarcodeScanning.getClient(options)

private fun createBarcodeScannerOptions(): BarcodeScannerOptions = BarcodeScannerOptions.Builder()
    .setBarcodeFormats(
        Barcode.FORMAT_QR_CODE,
        Barcode.FORMAT_EAN_8,
        Barcode.FORMAT_EAN_13,
        Barcode.FORMAT_CODE_39,
        Barcode.FORMAT_CODE_93,
        Barcode.FORMAT_CODE_128,
        Barcode.FORMAT_ITF,
        Barcode.FORMAT_CODABAR
    )
    .build()

private fun createPreviewView(context: Context): PreviewView {
    return PreviewView(context).apply {
        scaleType = PreviewView.ScaleType.FILL_CENTER
        implementationMode = PreviewView.ImplementationMode.COMPATIBLE
    }
}
