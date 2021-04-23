package com.capgemini.roomjetpackandroid.model

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

//----WRAPPER CLASS : Threading ..Usually for multiple sources----
class RepositoryVM(context: Context) {

    private  val studentDao = StudentDatabase.getInstance(context).studentDao()

    suspend fun addStudent(std :Student)=studentDao.insert(std)

    suspend fun updateStudent(std: Student)= studentDao.update(std)

    suspend fun deleteStudent(std: Student)= studentDao.delete(std)

    suspend fun deleteAllStudent()=studentDao.deleteAll()

    suspend fun allStudents(): List<Student> {
        var stdList:List<Student>?=null

        val response = CoroutineScope(Dispatchers.Default).async {
                studentDao.getStudents()
        }
        stdList = response.await()
        return stdList
    }

}