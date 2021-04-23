package com.capgemini.roomjetpackandroid.model

import androidx.room.*

/*----2. DATA ACCESS OBJECT----(define methods for queries)*/
@Dao
interface StudentDao {
    @Insert
    suspend fun insert(std:Student)

    @Update
    suspend fun update(std:Student)

    @Delete
    suspend fun delete(std: Student)

    @Query("DELETE FROM student_table")
    suspend fun deleteAll()

    @Query("select * from student_table order by marks desc")
    suspend fun getStudents() :List<Student>
}