package com.project.a65ddm_trabalho1.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Medicamento::class, Lembrete::class], version = 4)
abstract class AppDataBase : RoomDatabase() {
    abstract fun medicamentoDAO(): MedicamentoDAO
    abstract fun lembreteDAO(): LembreteDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "medicamento_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
