package com.capgemini.roomjetpackandroid.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capgemini.roomjetpackandroid.model.RepositoryVM
import com.capgemini.roomjetpackandroid.model.Student
import com.capgemini.roomjetpackandroid.model.University
import kotlinx.coroutines.launch

class UnivViewModel(app:Application):AndroidViewModel(app) {
//    init{
//        updateCount()
//    }
    private val repo= RepositoryVM(app)
    var count=0


    var univ= University("VIT","Vellore,TamilNadu","contact@vit.ac.in")

    var studentCount=MutableLiveData<Int>()

    fun updateCount(){
        //get the count from db
        viewModelScope.launch {
            var list=repo.allStudents()

            studentCount.postValue(list.size)

        }
        Log.d("count","$studentCount")


    }

}