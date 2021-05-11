package com.math.watermelon

import android.content.Context

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.math.watermelon.databinding.FragmentMakePeopleBinding

class MakePeopleFragment : Fragment() {

    internal var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentMakePeopleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMakePeopleBinding.inflate(inflater, container, false)

        binding.checkdetailback.setOnClickListener {
            mainActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }


        return binding.root
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity

    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

}