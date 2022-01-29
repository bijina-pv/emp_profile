package com.test.employeeprofile.database

import com.test.employeeprofile.model.dataModel


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [dataModel.employeeDetailsModel::class],
    version = 1 ,exportSchema = false)

abstract class DataBaseInstance : RoomDatabase(){
    abstract fun DataBaseInterface(): DataBaseInterface
    companion object {
        @Volatile private var instance: DataBaseInstance? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            DataBaseInstance::class.java, "employeedb.db")
            .allowMainThreadQueries()
            .setJournalMode(JournalMode.TRUNCATE)
            .build()
    }


}