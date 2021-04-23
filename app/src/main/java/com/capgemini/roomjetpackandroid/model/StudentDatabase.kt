package com.capgemini.roomjetpackandroid.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*----3. DATABASE----(abstract class)*/
@Database(entities = [Student::class],version = 1)
abstract class StudentDatabase: RoomDatabase() {

    abstract fun studentDao():StudentDao

    companion object{
        private var instance:StudentDatabase? =null

        /*fun getInstance(context: Context): StudentDatabase{
            if(instance == null)
               instance= buildDatabase(context)
            return instance!!
        }*/
        //alternative
        fun getInstance(context: Context)= instance?:buildDatabase(context).also { instance=it}

        private fun buildDatabase(context: Context): StudentDatabase {
            val builder = Room.databaseBuilder(context, StudentDatabase::class.java, "student.db")
            return builder.build()
        }
    }

}