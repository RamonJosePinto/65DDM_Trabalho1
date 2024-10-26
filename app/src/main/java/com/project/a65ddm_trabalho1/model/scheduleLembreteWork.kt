package com.project.a65ddm_trabalho1.model

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleLembreteWork(context: Context, lembrete: Lembrete) {
    val currentTime = System.currentTimeMillis()
    val delay = lembrete.dataLembrete - currentTime

    // Prepare os dados a serem enviados ao Worker
    val lembreteData = Data.Builder()
        .putInt("LEMBRETE_ID", lembrete.id)
        .putString("LEMBRETE_MENSAGEM", lembrete.mensagem)
        .build()

    // Configura o WorkRequest com o atraso calculado
    val workRequest = OneTimeWorkRequestBuilder<LembreteWorker>()
        .setInputData(lembreteData)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    // Envia o WorkRequest para o WorkManager
    WorkManager.getInstance(context).enqueue(workRequest)
}
