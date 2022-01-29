package com.test.employeeprofile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.test.employeeprofile.adapter.RecylerViewAdapter
import com.test.employeeprofile.api.NetworkInstance
import com.test.employeeprofile.database.DataBaseInstance
import com.test.employeeprofile.databinding.FragmentFirstBinding
import com.test.employeeprofile.model.dataModel
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.NetworkInterface
import org.json.JSONObject




/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class EmployeeListFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { parentActivity ->
            binding.progressbar.visibility = View.VISIBLE
            var empList:ArrayList<dataModel.employeeDetailsModel> = arrayListOf()
            empList = DataBaseInstance.invoke(parentActivity).DataBaseInterface().getEmployeeDetails() as ArrayList<dataModel.employeeDetailsModel>
            var layoutManager: LinearLayoutManager = LinearLayoutManager(parentActivity)
            var empListAdapter: RecylerViewAdapter = RecylerViewAdapter(empList,parentActivity)
            _binding?.idEmployeelist?.layoutManager = layoutManager
            _binding?.idEmployeelist?.adapter = empListAdapter
            empListAdapter.onItemClick= {it ->
                Log.d("TAG",it.name)
                MainActivity.selectecId = it._id
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
            if(empList!=null&&empList.size>0)
            {
                empListAdapter.notifyDataSetChanged()
                binding.progressbar.visibility = View.GONE
            }else
            {
                empList.clear()
                try {
                    var networkInstance = NetworkInstance()
                    val callback = object : Callback<JsonArray>{
                        override fun onResponse(
                            call: Call<JsonArray>,
                            response: Response<JsonArray>
                        ) {
                            binding.progressbar.visibility = View.GONE
                           if(response!=null&&response.body()!=null)
                           {
                               var empArray = response.body()
                               empList.clear()
                               if (empArray != null) {
                                   for (i in 0 until empArray.size()) {
                                       val empItem = empArray.get(i).asJsonObject

                                       var name = ""
                                       var phone = ""
                                       var website = ""
                                       var userName =""
                                       var profileImage =""
                                       var email =""
                                       var address =""
                                       var company =""
                                       var _id =0
                                       if(!empItem.get("id").isJsonNull)
                                       {
                                           _id = empItem.get("id").asInt
                                       }
                                       if(!empItem.get("name").isJsonNull)
                                       {
                                           name = empItem.get("name").asString
                                       }
                                       if(!empItem.get("username").isJsonNull)
                                       {
                                           userName=  empItem.get("username").asString
                                       }
                                       if(!empItem.get("profile_image").isJsonNull) {
                                           profileImage = empItem.get("profile_image").asString
                                       }
                                       if(!empItem.get("email").isJsonNull)
                                       {
                                           email = empItem.get("email").asString
                                       }

                                       if(!empItem.get("address").isJsonNull)
                                       {
                                           address = empItem.get("address").toString()
                                       }

                                       if(!empItem.get("phone").isJsonNull)
                                       {
                                          phone = empItem.get("phone").asString
                                       }

                                       if(!empItem.get("website").isJsonNull)
                                       {
                                           website = empItem.get("website").asString
                                       }
                                       if(!empItem.get("company").isJsonNull)
                                       {
                                           company = empItem.get("company").toString()
                                       }
                                       var eDetails = dataModel.employeeDetailsModel(_id,name,userName,email,address,phone,website,company,profileImage)
                                       empList.add(eDetails)
                                   }
                               }
                               empListAdapter.notifyDataSetChanged()
                               DataBaseInstance.invoke(parentActivity).DataBaseInterface().inserEmployeeDetails(empList)

                           }
                        }

                        override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                           Toast.makeText(parentActivity,"Please try again later",Toast.LENGTH_LONG).show()
                            binding.progressbar.visibility = View.GONE
                        }


                    }
                    networkInstance.fetchEmployeeDetails(callback)

                }catch (ex:Exception)
                {
                    ex.printStackTrace()
                }
            }


        }
        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}