package com.capgemini.roomjetpackandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.capgemini.roomjetpackandroid.model.Repository
import com.capgemini.roomjetpackandroid.model.Student
import com.capgemini.roomjetpackandroid.viewmodels.StudentViewModel
import kotlinx.android.synthetic.main.activity_student.*

class ActivityStudent : AppCompatActivity() {
    var id:Int=0
    //lateinit var repository: Repository
    lateinit var model: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        ///repository= Repository(this)
        val vmProvider =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
        model = vmProvider.get(StudentViewModel::class.java)
        updateB.visibility=View.INVISIBLE


        var intent=intent
        var isupdate=intent.getBooleanExtra("isUpdate",false)
        if(isupdate) {

            id = intent.getIntExtra("id",0)
            var fname = intent.getStringExtra("fname")
            var lname = intent.getStringExtra("lname")
            var marks = intent.getIntExtra("marks",0)
            fNameE.setText(fname)
            lNameE.setText(lname)
            marksE.setText(marks.toString())
            confrimB.visibility=View.INVISIBLE
            updateB.visibility=View.VISIBLE
        }


    }

    fun onButtonClicked(view: View) {
        when (view.id) {
            R.id.confrimB -> {
                val firstName = fNameE.text.toString()
                val lastName = lNameE.text.toString()
                val marks = marksE.text.toString().toInt()
                if (firstName != "" || lastName != "" || marks != null) {
                    val std = Student(firstName, lastName, marks)
                    //repository.addStudent(std)}
                    model.addStudent(std)
                    finish()
                }
            }
            R.id.updateB->{
                val efirstName = fNameE.text.toString()
                val elastName = lNameE.text.toString()
                val emarks = marksE.text.toString().toInt()
                if (efirstName != "" || elastName != "" || emarks != null) {
                    val std = Student(efirstName, elastName, emarks,id)
                    //repository.addStudent(std)}
                    model.updateStudent(std)
                    finish()
                }

            }

        }
    }
}