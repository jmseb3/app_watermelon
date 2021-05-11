package com.math.watermelon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.play.core.review.ReviewManagerFactory
import com.math.watermelon.databinding.FragmentEtcBinding


class etcFragment : Fragment() {

    private var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentEtcBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEtcBinding.inflate(inflater, container, false)

        binding.helpPerson.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragcontainer, HelpPeopleFragment())
                .commit()
        }

        binding.appRate.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("market://details?id=com.math.watermelon")
            startActivity(intent)
        }


        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = getActivity() as MainActivity

    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

}