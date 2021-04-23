package com.capgemini.roomjetpackandroid.model

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//----WRAPPER CLASS : Threading ..Usually for multiple sources----
class Repository(context: Context) {

    private  val studentDao = StudentDatabase.getInstance(context).studentDao()

    fun addStudent(std :Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.insert(std)
        }
    }
    fun updateStudent(std: Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.update(std)
        }
    }
    fun deleteStudent(std: Student){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.delete(std)
        }
    }
    fun deleteAllStudent(){
        CoroutineScope(Dispatchers.Default).launch {
            studentDao.deleteAll()
        }
    }
    suspend fun allStudents(): List<Student> {
        var stdList:List<Student>?=null

        val response = CoroutineScope(Dispatchers.Default).async {
                studentDao.getStudents()
        }
        stdList = response.await()
        return stdList
    }

}