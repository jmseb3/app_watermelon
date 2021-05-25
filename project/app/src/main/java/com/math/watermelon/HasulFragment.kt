package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.math.watermelon.databinding.FragmentHasulBinding
import com.math.watermelon.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HasulFragment : Fragment() {
    private var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentHasulBinding
    val hasulId by lazy { requireArguments().getInt("hasul_id") }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentHasulBinding.inflate(inflater, container, false)
        val db = AppDatabase.getInstance(mainActivity!!)

        binding.hasulBack.setOnClickListener {
            mainActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()

        }
        binding.hasukHome.setOnClickListener {
            for (i in 0 until mainActivity!!.supportFragmentManager.backStackEntryCount) {
                mainActivity!!.supportFragmentManager.popBackStack()
            }
        }
        binding.infoText.text = hasulId.toString()
        GlobalScope.launch(Dispatchers.IO) {
            var data = db.DataDao().getCollegeDataById(hasulId)
            var concept = db.DataDao().getmathdatabyid(data.conceptid)
            launch(Dispatchers.Main) {

                binding.infoText.text = data.year + data.qnumber
                binding.goConcept.text = concept.concept +"-"+ concept.topic

                binding.hasulBox.setOnClickListener{
                    if (binding.imgSol.visibility == View.INVISIBLE){
                        binding.imgSol.visibility = View.VISIBLE
                    } else {
                        binding.imgSol.visibility = View.INVISIBLE
                    }
                }

                binding.infoBox.setOnClickListener {
                    parentFragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .add(R.id.fragcontainer, DetailFragment()
                                    .apply {
                                        arguments = Bundle().apply {
                                            putInt("id", data.conceptid)
                                        }
                                    }).commit()
                }
                Glide.with(mainActivity!!)
                        .load(data.imgMainSrc + data.imhQueSrc)
                        .placeholder(R.drawable.loading)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.imgQ)

                Glide.with(mainActivity!!)
                        .load(data.imgMainSrc + "_SOL" + data.imhQueSrc)
                        .placeholder(R.drawable.loading)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(binding.imgSol)
            }

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