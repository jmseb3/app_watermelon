package com.math.watermelon

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.math.watermelon.databinding.FragmentEtcBinding
import com.math.watermelon.databinding.FragmentHelpPeopleBinding

class HelpPeopleFragment : Fragment() {

    internal var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentHelpPeopleBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHelpPeopleBinding.inflate(inflater, container, false)

        binding.checkdetailback.setOnClickListener {
            mainActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.helpPersonbtn1.setOnClickListener {
            val webIntent: Intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://blog.naver.com/mathfreedom")
            )
            startActivity(webIntent)
        }

        binding.helpPersonbtn2.setOnClickListener {
            val webIntent: Intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.kice.re.kr/")
            )
            startActivity(webIntent)
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