package com.capgemini.roomjetpackandroid

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capgemini.roomjetpackandroid.model.Student

class MyAdapter(val data:List<Student>, val listener: (Student,Boolean)->Unit): RecyclerView.Adapter<MyAdapter.ViewHolder>() {


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view) {
        val fName = view.findViewById<TextView>(R.id.listFNameT)
        val lName = view.findViewById<TextView>(R.id.listLNameT)
        val marks = view.findViewById<TextView>(R.id.listMarksT)
        val cBox = view.findViewById<CheckBox>(R.id.listCheckBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val v=inflater.inflate(R.layout.student_list,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val std = data[position]
        Log.d("MyAdapter","data: $std")
        holder.cBox.isChecked=false
        holder.fName.text = std.firstName
        holder.lName.text = std.lastName
        holder.marks.text = std.marks.toString()

        holder.cBox.setOnCheckedChangeListener { buttonView, isChecked ->
            listener(std,isChecked)
        }
    }

    override fun getItemCount()=data.size
}