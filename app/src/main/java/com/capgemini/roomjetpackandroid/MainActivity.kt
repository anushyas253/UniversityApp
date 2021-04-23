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
import androidx.recyclerview.widget.LinearLayoutManager
import com.capgemini.roomjetpackandroid.model.Repository
import com.capgemini.roomjetpackandroid.model.Student
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var repository: Repository
    lateinit var studentList: List<Student>
    lateinit var adapter:MyAdapter
    var checkedStudentList = mutableListOf<Student>()
    var count:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository= Repository(this)
        studentList = mutableListOf()
    }


    override fun onResume() {
        super.onResume()
        updateList()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.add("Add")
        menu?.add("Update")
        menu?.add("Delete")
        menu?.add("Delete All")
        menu?.add("Refresh")
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.title)
        {
            "Add" -> {
                startActivity(Intent(this,ActivityStudent::class.java))
            }
            "Update" -> {
                if(checkedStudentList.size==1)
                    updateStudent(checkedStudentList[0])
                else
                    Toast.makeText(this,"Select only one",Toast.LENGTH_LONG).show()
            }
            "Delete" -> {
                deleteCheckedStudents()
            }
            "Delete All" -> {
                deleteAllStudents()
            }
            "Refresh" -> {
                updateList()
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
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
            for(std in checkedStudentList)
                repository.deleteStudent(std)
            updateList()
        })
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss()}//trailing lambda
        val dlg: AlertDialog =builder.create()
        dlg.show()
    }


    private fun deleteAllStudents() {
        val builder= AlertDialog.Builder(this)
        builder.setTitle("DELETE ENTRY")
        builder.setMessage("Do you want  to delete all?")
        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
            repository.deleteAllStudent()
            updateList()
        })
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss()}//trailing lambda
        val dlg: AlertDialog =builder.create()
        dlg.show()
    }


    private fun updateList(){
        CoroutineScope(Dispatchers.Default).launch {
            val response = CoroutineScope(Dispatchers.Default).async {
                repository.allStudents()
            }
            studentList = response.await()

            //update in main thread
            CoroutineScope(Dispatchers.Main).launch {
                Log.d("MainActivity","List: $studentList")

                rView.layoutManager = LinearLayoutManager(this@MainActivity)
                adapter=MyAdapter(studentList){student: Student, b: Boolean ->
                    if(b==true)
                        checkedStudentList.add(student)
                    else
                        checkedStudentList.remove(student)
                    Log.d("MainActivity","CheckedList: $checkedStudentList")
                }
                rView.adapter=adapter
                adapter.notifyDataSetChanged()
            }
        }
    }


}