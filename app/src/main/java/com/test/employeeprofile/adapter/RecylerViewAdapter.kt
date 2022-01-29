package com.test.employeeprofile.adapter

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.test.employeeprofile.R
import com.test.employeeprofile.model.dataModel
import java.io.File

class RecylerViewAdapter (val employeeList: ArrayList<dataModel.employeeDetailsModel>,val ctx:Context) : RecyclerView.Adapter<RecylerViewAdapter.RecylerViewHolder>() {
    var onItemClick: ((dataModel.employeeDetailsModel) -> Unit)? = null
    //lateinit var context: Context


    override fun onBindViewHolder(holder: RecylerViewHolder, position: Int) {
        var employeeItem = employeeList.get(position)
        holder.empName.text = employeeItem.name
        Log.d("TAG",employeeItem.compantdetails)
        if(employeeItem.compantdetails!=null&&employeeItem.compantdetails.isNotEmpty())
        {
            val gson = Gson()
            var cInfoDetails: dataModel.companyInfo = gson.fromJson(employeeItem.compantdetails,dataModel.companyInfo::class.java)
            holder.cmpName.text = cInfoDetails.name
        }else
        {
            holder.cmpName.text = ""
        }

        Glide.with(ctx)
            .load(employeeItem.profile_image)
            .placeholder(R.drawable.ic_launcher_background)
            .override(300, 200)
            .into(holder.empImage)

    }

    override fun getItemCount(): Int {
        if (employeeList != null) {
            return employeeList.size
        }
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecylerViewHolder {


        return RecylerViewHolder(
            LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_employeeitem, parent, false)
        );


    }

    inner class RecylerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var empName = itemView.findViewById<View>(R.id.id_empname) as TextView
        var cmpName = itemView.findViewById<View>(R.id.id_cmpname) as TextView
        var empImage = itemView.findViewById<View>(R.id.id_profile_image) as ImageView
        init{
            itemView.setOnClickListener {
                onItemClick?.invoke(employeeList[adapterPosition])
            }
        }


    }
}
