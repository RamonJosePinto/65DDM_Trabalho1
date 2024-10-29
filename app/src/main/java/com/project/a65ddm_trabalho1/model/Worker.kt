package com.project.a65ddm_trabalho1.model

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.project.a65ddm_trabalho1.R
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class LembreteWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val lembreteId = inputData.getInt("LEMBRETE_ID", 0)
        val lembreteMensagem = inputData.getString("LEMBRETE_MENSAGEM") ?: "Lembrete"

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return Result.failure()
        }

        val builder = NotificationCompat.Builder(applicationContext, "LEMBRETE_CHANNEL_ID")
            .setSmallIcon(R.drawable.icone_edit)
            .setContentTitle("Hora do Lembrete!")
            .setContentText(lembreteMensagem)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(lembreteId, builder.build())
        }

        return Result.success()
    }
}
