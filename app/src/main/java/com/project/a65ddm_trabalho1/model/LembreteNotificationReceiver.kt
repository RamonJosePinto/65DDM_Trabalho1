package com.project.a65ddm_trabalho1.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.util.Log
import com.project.a65ddm_trabalho1.R

class LembreteNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val lembreteId = intent.getIntExtra("LEMBRETE_ID", 0)
        val lembreteMensagem = intent.getStringExtra("LEMBRETE_MENSAGEM") ?: "Lembrete"
        Log.d("LembreteNotificationReceiver", "Recebendo o alarme para o lembrete: $lembreteId")
        val builder = NotificationCompat.Builder(context, "LEMBRETE_CHANNEL_ID")
            .setSmallIcon(R.drawable.icone_edit)
            .setContentTitle("Hora do Lembrete!")
            .setContentText(lembreteMensagem)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(context)) {
            notify(lembreteId, builder.build())
        }
    }
}
