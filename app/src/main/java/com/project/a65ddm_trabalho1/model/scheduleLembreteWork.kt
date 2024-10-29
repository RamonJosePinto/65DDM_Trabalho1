package com.project.a65ddm_trabalho1.model

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

fun scheduleLembreteWork(context: Context, lembrete: Lembrete) {
    val currentTime = System.currentTimeMillis()
    val delay = lembrete.dataLembrete - currentTime

    val lembreteData = Data.Builder()
        .putInt("LEMBRETE_ID", lembrete.id)
        .putString("LEMBRETE_MENSAGEM", lembrete.mensagem)
        .build()


    val workRequest = OneTimeWorkRequestBuilder<LembreteWorker>()
        .setInputData(lembreteData)
        .setInitialDelay(delay, TimeUnit.MILLISECONDS)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}
