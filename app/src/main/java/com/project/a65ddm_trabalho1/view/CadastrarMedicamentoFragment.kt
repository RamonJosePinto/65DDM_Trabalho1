package com.project.a65ddm_trabalho1.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Handler
import android.os.Looper
import android.view.Surface
import androidx.camera.core.AspectRatio
import androidx.core.app.ActivityCompat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CadastrarMedicamentoFragment : Fragment() {

    private lateinit var viewModel: MedicamentoViewModel
    private lateinit var nomeEditText: EditText
    private lateinit var dosagemEditText: EditText
    private lateinit var botaoCadastrar: Button
    private lateinit var botaoAbrirCamera: Button
    private lateinit var imageViewFoto: ImageView
    private var currentPhoto: Bitmap? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var previewView: PreviewView  // Declaração do PreviewView
    private lateinit var currentPhotoPath: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastrar_medicamento, container, false)

        // Inicialize os campos de texto e botão
        nomeEditText = view.findViewById(R.id.nome_medicamento)
        dosagemEditText = view.findViewById(R.id.dosagem_medicamento)
        botaoCadastrar = view.findViewById(R.id.botao_cadastrar)
        botaoAbrirCamera = view.findViewById(R.id.botao_abrir_camera)
        imageViewFoto = view.findViewById(R.id.image_view_foto)
        previewView = view.findViewById(R.id.preview_view)


        startCamera()

        // Conecte o ViewModel
        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        if (!allPermissionsGranted()) {
            requestPermissions()
        }

        botaoAbrirCamera.setOnClickListener {
            takePhoto()
        }

        // Configura a ação do botão
        botaoCadastrar.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val dosagem = dosagemEditText.text.toString()

            // Chama a função de cadastrar medicamento do ViewModel
            if (nome.isNotEmpty() && dosagem.isNotEmpty()) {
                viewModel.cadastrarMedicamento(nome, dosagem, currentPhotoPath)
                Toast.makeText(requireContext(), "Medicamento cadastrado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private var isCameraReady = false

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder()
                .setTargetAspectRatio(AspectRatio.RATIO_4_3)  // Define a proporção 4:3, que é próxima a 2:3
                .build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
                previewView.visibility = View.VISIBLE  // Torna a visualização da câmera visível
                imageViewFoto.visibility = View.GONE  // Oculta a imagem capturada
            } catch (exc: Exception) {
                Log.e("CameraXApp", "Erro ao inicializar a câmera: ${exc.message}", exc)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = File(requireContext().externalMediaDirs.firstOrNull(), "${System.currentTimeMillis()}.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraApp", "Erro ao capturar imagem: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                    val rotatedBitmap = rotateBitmapFixed(bitmap)
                    imageViewFoto.setImageBitmap(rotatedBitmap)
                    imageViewFoto.visibility = View.VISIBLE
                    previewView.visibility = View.GONE

                    // Salva o bitmap no armazenamento externo e armazena o caminho
                    currentPhotoPath = saveBitmapToExternalStorage(rotatedBitmap) ?: ""
                }
            }
        )
    }

    // Função para rotacionar o bitmap em -90 graus
    private fun rotateBitmapFixed(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)  // Aplica uma rotação fixa de -90 graus
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun allPermissionsGranted() = arrayOf(Manifest.permission.CAMERA).all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 10)
    }

    private fun saveBitmapToExternalStorage(bitmap: Bitmap): String? {
        val context = requireContext()
        val photoDir = File(context.getExternalFilesDir(null), "medicamento_fotos")

        if (!photoDir.exists()) {
            photoDir.mkdirs() // Cria o diretório se não existir
        }

        val photoFile = File(photoDir, "medicamento_${System.currentTimeMillis()}.jpg")
        return try {
            val outputStream = FileOutputStream(photoFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // Ajuste a qualidade conforme necessário
            outputStream.flush()
            outputStream.close()
            photoFile.absolutePath // Retorna o caminho completo do arquivo
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}

