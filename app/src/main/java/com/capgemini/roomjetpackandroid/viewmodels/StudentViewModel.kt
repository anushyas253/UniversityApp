package com.capgemini.roomjetpackandroid.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.roomjetpackandroid.model.RepositoryVM
import com.capgemini.roomjetpackandroid.model.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(app:Application) :AndroidViewModel(app) {
    //reference to the repository

    private val repo=RepositoryVM(app)
    var studentList= MutableLiveData<List<Student>>()

    init{
        getStudents()
    }

    fun addStudent(std:Student){
        viewModelScope.launch {
            repo.addStudent(std)
            getStudents()
        }
    }
    fun updateStudent(std:Student){
        viewModelScope.launch {
            repo.updateStudent(std)
            getStudents()
        }
    }
    fun deleteStudent(std:Student){
        viewModelScope.launch {
            repo.deleteStudent(std)
            getStudents()
        }
    }
    fun deleteAll(){
        viewModelScope.launch {
            repo.deleteAllStudent()
            getStudents()
        }
    }

    fun getStudents(){
        viewModelScope.launch {
             var list=repo.allStudents()
            studentList.postValue(list)
        }
        //return studentList

    }

}