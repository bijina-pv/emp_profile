package com.test.employeeprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.test.employeeprofile.database.DataBaseInstance
import com.test.employeeprofile.databinding.FragmentSecondBinding
import com.test.employeeprofile.model.dataModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class EmployeeDetailsFragment : Fragment() {

    private var _bindingTwo: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _bindingTwo!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _bindingTwo = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let { parentActivity ->
            if(MainActivity.selectecId>0)
            {
               var info =  DataBaseInstance.invoke(parentActivity).DataBaseInterface().getEmpInfo(MainActivity.selectecId)
                Glide.with(parentActivity)
                    .load(info.profile_image)
                    .override(300, 200)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding?.idProfileIcon!!)
                binding?.idName?.text = info.userName
                binding?.idUname?.text = info.name
                binding?.idEmail?.text = info.email_address
                binding?.idPhone?.text = info.phone
                binding?.idWebsite?.text = info.website

                if(info.compantdetails!=null&&info.compantdetails.isNotEmpty())
                {
                    val gson = Gson()
                    var cInfoDetails: dataModel.companyInfo = gson.fromJson(info.compantdetails,
                        dataModel.companyInfo::class.java)
                    binding.idCompanydetails.text = cInfoDetails.name+","+cInfoDetails.catchPhrase+","+cInfoDetails.bs
                }

                if(info.adress!=null&&info.adress.isNotEmpty())
                {
                    val gson = Gson()
                    var cInfoDetails: dataModel.addressInfo = gson.fromJson(info.adress,
                        dataModel.addressInfo::class.java)
                    binding.idAddress.text = cInfoDetails.street+","+cInfoDetails.suite+","+cInfoDetails.city+","+cInfoDetails.zipcode
                }

            }
        }

//        binding.buttonSecond.setOnClickListener {
//            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingTwo = null
    }
}