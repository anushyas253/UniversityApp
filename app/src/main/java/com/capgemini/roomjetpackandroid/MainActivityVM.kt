package com.capgemini.roomjetpackandroid
/*University App:-
    -List of students : MainActivity ->Recycler view
    -Add new student : StudentActivity
    -update student : StudentActivity
    -delete
Student:-
    -firstName
    -id(primary key)
    -lastName
    -marks
*/

/*1. Dependency and plugin*/

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capgemini.roomjetpackandroid.model.Repository
import com.capgemini.roomjetpackandroid.model.Student
import com.capgemini.roomjetpackandroid.viewmodels.StudentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivityVM : AppCompatActivity() {

    lateinit var  model: StudentViewModel
    var adapter:MyAdapter?=null
    var checkedStudentList = mutableListOf<Student>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        rView.layoutManager = LinearLayoutManager(this)
//        rView.adapter=adapter

        //----VIEW MODEL PROVIDER----(to prevent multiple creation, memory leak)
        val vmProvider=ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory(application))
        model=vmProvider.get(StudentViewModel::class.java)

        rView.layoutManager = LinearLayoutManager(this)
        rView.adapter=adapter

        model.studentList.observe(this, Observer {
            val stdList=it
            Log.d("MainActivity","Observer:$stdList")
            //setup adapter
            adapter= MyAdapter(model.studentList.value!!){ student: Student, b: Boolean ->
            if (b == true)
                checkedStudentList.add(student)
            else
                checkedStudentList.remove(student)
            Log.d("MainActivity", "CheckedList: $checkedStudentList")
        }
            rView.adapter=adapter
            adapter?.notifyDataSetChanged()

        })
    }


    override fun onResume() {
        super.onResume()
        //live data occurs only when min activity in running
        model.getStudents()
        //updateList()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add")
        menu?.add("Update")
        menu?.add("Delete")
        menu?.add("Delete All")
        menu?.add("About Us")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title)
        {
            "Add" -> {
                startActivity(Intent(this,ActivityStudent::class.java))
            }
            "Update" -> {
//                if(checkedStudentList.size==1)
//                    updateStudent(checkedStudentList[0])
//                else
//                    Toast.makeText(this,"Select only one",Toast.LENGTH_LONG).show()
                val i=Intent(this,ActivityStudent::class.java)
                i.putExtra("isUpdate",true)
                i.putExtra("fname",checkedStudentList.get(0).firstName)
                i.putExtra("lname",checkedStudentList.get(0).lastName)
                i.putExtra("marks",checkedStudentList.get(0).marks)
                i.putExtra("id",checkedStudentList.get(0).id)
                startActivity(i)

            }
            "Delete" -> {
                deleteCheckedStudents()
            }
            "Delete All" -> {
                deleteAllStudents()
            }
            "About Us" -> {
                //updateList()
                val i=Intent(this,AboutActivty::class.java)
                startActivity(i)

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateStudent(student: Student) {
        Toast.makeText(this,"Edit $student",Toast.LENGTH_LONG).show()
    }


    private fun deleteCheckedStudents() {
        if(checkedStudentList.isEmpty()) {
            Toast.makeText(this,"No items checked",Toast.LENGTH_LONG).show()
            return
        }
        val builder= AlertDialog.Builder(this)
        builder.setTitle("DELETE CHECKED")
        builder.setMessage("Do you want  to delete all checked?\n$checkedStudentList")
        builder.setPositiveButton("Yes") { _, _ ->
            for (std in checkedStudentList)
                model.deleteStudent(std)
            //updateList()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss()}//trailing lambda
        val dlg: AlertDialog =builder.create()
        dlg.show()
    }


    private fun deleteAllStudents() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("DELETE ENTRY")
        builder.setMessage("Do you want  to delete all?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
            model.deleteAll()
            //updateList()
        })
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss()}//trailing lambda
        val dlg: AlertDialog =builder.create()
        dlg.show()
    }


//    private fun updateList(){
//        val stdList = model.getStudents()
//
//        rView.layoutManager = LinearLayoutManager(this@MainActivityVM)
//        adapter = MyAdapter(stdList) { student: Student, b: Boolean ->
//            if (b == true)
//                checkedStudentList.add(student)
//            else
//                checkedStudentList.remove(student)
//            Log.d("MainActivity", "CheckedList: $checkedStudentList")
//        }
//        rView.adapter = adapter
//        adapter?.notifyDataSetChanged()
//        Log.d("MainActivity", "updateList(): $stdList")
//
//
//    }


}