package com.capgemini.roomjetpackandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.capgemini.roomjetpackandroid.databinding.ActivityAboutActivtyBinding
import com.capgemini.roomjetpackandroid.model.Repository
import com.capgemini.roomjetpackandroid.model.Student
import com.capgemini.roomjetpackandroid.model.University
import com.capgemini.roomjetpackandroid.viewmodels.StudentViewModel
import com.capgemini.roomjetpackandroid.viewmodels.UnivViewModel

class AboutActivty : AppCompatActivity() {
    lateinit var model: UnivViewModel
    lateinit var  smodel: StudentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_about_activty)

        val vmProvider=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory(application))
        model=vmProvider.get(UnivViewModel::class.java)
        val binding=DataBindingUtil.setContentView<ActivityAboutActivtyBinding>(this,R.layout.activity_about_activty)

        binding.university= model.univ
        binding.univViewModel=model
        binding.lifecycleOwner=this
        model.updateCount()


//        model.studentCount.observe(this,{
//            model.studentCount.value=it
//        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Update Count")
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        model.updateCount()




//        val count=(Math.random() *1000).toInt()
//        Log.d("About","$count")
//        model.studentCount.value=count//as in same thread
        return super.onOptionsItemSelected(item)
    }
}