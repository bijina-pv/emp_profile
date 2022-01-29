package com.test.employeeprofile.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.employeeprofile.model.dataModel

@Dao
interface DataBaseInterface{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserEmployeeDetails(empDetails:List<dataModel.employeeDetailsModel>)
    @Query("SELECT * FROM table_employee_details")
    fun getEmployeeDetails():List<dataModel.employeeDetailsModel>
    @Query("SELECT * FROM table_employee_details WHERE _id =:id")
    fun getEmpInfo(id:Int):dataModel.employeeDetailsModel
}