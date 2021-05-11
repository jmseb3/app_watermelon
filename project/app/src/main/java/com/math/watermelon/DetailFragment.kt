package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.math.watermelon.databinding.FragmentDetailBinding
import com.math.watermelon.room.AppDatabase
import com.math.watermelon.room.mathdata
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    internal var mainActivity: MainActivity? = null

    private lateinit var binding: FragmentDetailBinding

    val iddata by lazy { requireArguments().getInt("id") }
    lateinit var data:mathdata


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)

        val db = AppDatabase.getInstance(requireContext())

        GlobalScope.launch(Dispatchers.IO) {
            data = db.DataDao().getmathdatabyid(iddata)
            launch(Dispatchers.Main) {
                Glide.with(requireActivity())
                    .load(data.imgsrc+data.imgsrcend)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.detailimg)

                if(data.favorite != null){
                    Glide.with(requireContext())
                        .load(R.drawable.ic_baseline_favorite_24)
                        .into(binding.detailfavorite)
                } else {
                    Glide.with(requireContext())
                        .load(R.drawable.ic_baseline_favorite_border_24)
                        .into(binding.detailfavorite)
                }
            }
        }

        binding.detailback.setOnClickListener {
            mainActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()
        }

        binding.detaihome.setOnClickListener {
            for (i in 0 until mainActivity!!.supportFragmentManager.backStackEntryCount) {
                mainActivity!!.supportFragmentManager.popBackStack()
            }

        }
        binding.detailcheck.setOnClickListener {
            var getid = iddata

            mainActivity!!.supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragcontainer, DetailcheckFragment()
                    .apply {
                        arguments = Bundle().apply {
                            putInt("id",getid)
                        }
                    }).commit()

        }



        binding.detailfavorite.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                data = db.DataDao().getmathdatabyid(iddata)
                launch(Dispatchers.Main) {
                    if(data.favorite != null){
                        launch(Dispatchers.IO) {
                            db.DataDao().changefavorite(data.id!!,null)
                        }
                        Glide.with(requireContext())
                                .load(R.drawable.ic_baseline_favorite_border_24)
                                .into(binding.detailfavorite)

                        Toast.makeText(requireContext(),"즐겨찾기가 제거되었습니다.",Toast.LENGTH_SHORT).show()
                    } else {
                        val edittextbox = EditText(mainActivity)
                        val builder: AlertDialog.Builder = AlertDialog.Builder(mainActivity!!)
                        builder.setTitle("즐겨찾기 추가")
                        builder.setMessage("즐겨찾기 제목을 지정해주세요.")
                        builder.setView(edittextbox)
                        GlobalScope.launch(Dispatchers.IO) {
                            data = db.DataDao().getmathdatabyid(iddata)
                            GlobalScope.launch(Dispatchers.Main) {
                                edittextbox.setText(data.topic)
                            }
                        }
                        builder.setPositiveButton(
                                "입력"
                        ) { _, _ ->
                            //제목 입력, DB추가
                            if (edittextbox.text.toString().isNotEmpty()) {
                                GlobalScope.launch() {
                                    db.DataDao().changefavorite(iddata,edittextbox.text.toString())
                                }
                                Toast.makeText(mainActivity, "즐겨찾기에 추가되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                            Glide.with(requireContext())
                                    .load(R.drawable.ic_baseline_favorite_24)
                                    .into(binding.detailfavorite)
                        }
                        builder.setNegativeButton(
                                "취소"
                        ) { _, _ ->
                            //취소
                        }
                        builder.show()


                    }
                    }
                }
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

