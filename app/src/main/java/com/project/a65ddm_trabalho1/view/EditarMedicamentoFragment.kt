package com.project.a65ddm_trabalho1.view

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
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
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.project.a65ddm_trabalho1.R
import com.project.a65ddm_trabalho1.viewModel.MedicamentoViewModel
import java.io.File
import java.io.FileOutputStream

class EditarMedicamentoFragment : Fragment() {

    private lateinit var viewModel: MedicamentoViewModel
    private lateinit var nomeEditText: EditText
    private lateinit var dosagemEditText: EditText
    private lateinit var imageViewFoto: ImageView
    private lateinit var botaoTirarNovaFoto: Button
    private lateinit var botaoSalvar: Button
    private lateinit var previewView: PreviewView
    private var imageCapture: ImageCapture? = null
    private var currentPhotoPath: String = ""
    private val args: EditarMedicamentoFragmentArgs by navArgs()
    private var isCameraActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editar_medicamento, container, false)

        nomeEditText = view.findViewById(R.id.edit_text_nome_medicamento)
        dosagemEditText = view.findViewById(R.id.edit_text_dosagem_medicamento)
        imageViewFoto = view.findViewById(R.id.image_view_foto_medicamento)
        botaoTirarNovaFoto = view.findViewById(R.id.botao_tirar_nova_foto)
        botaoSalvar = view.findViewById(R.id.botao_salvar_medicamento)
        previewView = view.findViewById(R.id.preview_view)

        viewModel = ViewModelProvider(this).get(MedicamentoViewModel::class.java)

        val medicamento = args.medicamento
        nomeEditText.setText(medicamento.nome)
        dosagemEditText.setText(medicamento.dosagem)

        currentPhotoPath = medicamento.fotoCaminho ?: ""
        if (currentPhotoPath.isNotEmpty()) {
            val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            imageViewFoto.setImageBitmap(bitmap)
            previewView.visibility = View.GONE
        }

        botaoTirarNovaFoto.setOnClickListener {
            if (!isCameraActive) {
                previewView.visibility = View.VISIBLE
                startCamera()
                botaoTirarNovaFoto.text = "Capturar Foto"
                isCameraActive = true
            } else {
                takePhoto()
                botaoTirarNovaFoto.text = "Tirar Outra Foto"
                isCameraActive = false
            }
        }

        botaoSalvar.setOnClickListener {
            val nome = nomeEditText.text.toString()
            val dosagem = dosagemEditText.text.toString()
            if (nome.isNotEmpty() && dosagem.isNotEmpty()) {
                viewModel.atualizarMedicamento(medicamento.copy(nome = nome, dosagem = dosagem, fotoCaminho = currentPhotoPath))
                Toast.makeText(requireContext(), "Medicamento atualizado!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            imageCapture = ImageCapture.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e("EditarMedicamento", "Erro ao inicializar a câmera: ${exc.message}", exc)
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
                    Log.e("EditarMedicamento", "Erro ao capturar imagem: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)
                    val rotatedBitmap = rotateBitmapFixed(bitmap)
                    imageViewFoto.setImageBitmap(rotatedBitmap)
                    previewView.visibility = View.GONE
                    currentPhotoPath = saveBitmapToExternalStorage(bitmap) ?: ""
                }
            }
        )
    }

    private fun saveBitmapToExternalStorage(bitmap: Bitmap): String? {
        val photoDir = File(requireContext().getExternalFilesDir(null), "medicamento_fotos")
        if (!photoDir.exists()) photoDir.mkdirs()
        val photoFile = File(photoDir, "medicamento_${System.currentTimeMillis()}.jpg")
        return try {
            FileOutputStream(photoFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out)
                out.flush()
            }
            photoFile.absolutePath
        } catch (e: Exception) {
            Log.e("EditarMedicamento", "Erro ao salvar imagem", e)
            null
        }
    }

    private fun rotateBitmapFixed(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(90f)  // Aplica uma rotação fixa de -90 graus
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
