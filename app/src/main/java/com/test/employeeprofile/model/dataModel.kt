package com.test.employeeprofile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
class dataModel{
    @Entity(tableName = "table_employee_details")
    data class employeeDetailsModel(@ColumnInfo(name="_id")var _id:Int,@ColumnInfo(name = "name") var name:String, @ColumnInfo(name ="user_name")var userName:String,
                                    @ColumnInfo(name ="email_address")var email_address:String, @ColumnInfo(name ="address")var adress:String, @ColumnInfo(name ="phone")var phone:String, @ColumnInfo(name ="website")var website:String, @ColumnInfo(name ="companydetails")var compantdetails:String, @ColumnInfo(name="profile_image")var profile_image:String)
    {
        @PrimaryKey(autoGenerate = true)
        var id: Long? = null
    }
    data class companyInfo(@SerializedName("name")var name:String,@SerializedName("catchPhrase")var catchPhrase:String,@SerializedName("bs")var bs:String)
    data class  addressInfo(@SerializedName("street")var street:String,@SerializedName("suite")var suite:String,@SerializedName("city")var city:String,@SerializedName("zipcode")var zipcode:String)
}

