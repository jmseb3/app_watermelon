package com.math.watermelon

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.math.watermelon.databinding.FragmentDetailcheckBinding
import com.math.watermelon.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


class DetailcheckFragment : Fragment() {

    private var mainActivity: MainActivity? = null

    val iddata by lazy { requireArguments().getInt("id") }

    private lateinit var binding: FragmentDetailcheckBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailcheckBinding.inflate(inflater, container, false)

        val db = AppDatabase.getInstance(requireContext())

        binding.checkdetaihome.setOnClickListener {
            for (i in 0 until mainActivity!!.supportFragmentManager.backStackEntryCount) {
                mainActivity!!.supportFragmentManager.popBackStack()
            }
        }

        binding.checkdetailback.setOnClickListener {
            mainActivity!!.supportFragmentManager.beginTransaction().remove(this).commit()

        }
        GlobalScope.launch(Dispatchers.IO) {
            var nowdata =db.DataDao().getmathdatabyid(iddata)
            GlobalScope.launch(Dispatchers.Main) {
                anscheck(nowdata.ans)
                Glide.with(requireActivity())
                    .load(nowdata.qimgsrc+"Q"+nowdata.imgsrcend)
                    .placeholder(R.drawable.loading)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(binding.detailimg)

                Glide.with(requireActivity())
                    .load(nowdata.qimgsrc+"A1"+nowdata.imgsrcend)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .fitCenter()
                    .into(binding.ans1img)

                Glide.with(requireActivity())
                    .load(nowdata.qimgsrc+"A2"+nowdata.imgsrcend)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .fitCenter()
                    .into(binding.ans2img)

                Glide.with(requireActivity())
                    .load(nowdata.qimgsrc+"A3"+nowdata.imgsrcend)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .fitCenter()
                    .into(binding.ans3img)

                Glide.with(requireActivity())
                    .load(nowdata.qimgsrc+"A4"+nowdata.imgsrcend)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .fitCenter()
                    .into(binding.ans4img)
            }

        }



        return (binding.root)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity

    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    private fun anscheck(ans: Int) {
        val dialog = makeDialog(requireContext())


        when (ans) {
            1 -> {
                binding.ans1img.setOnClickListener {
                    dialog.successhow()
                }
                binding.ans2img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans3img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans4img.setOnClickListener {
                    dialog.failshow()
                }

            }
            2 -> {
                binding.ans1img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans2img.setOnClickListener {
                    dialog.successhow()
                }
                binding.ans3img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans4img.setOnClickListener {
                    dialog.failshow()
                }

            }
            3 -> {
                binding.ans1img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans2img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans3img.setOnClickListener {
                    dialog.successhow()
                }
                binding.ans4img.setOnClickListener {
                    dialog.failshow()
                }

            }
            4 -> {
                binding.ans1img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans2img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans3img.setOnClickListener {
                    dialog.failshow()
                }
                binding.ans4img.setOnClickListener {
                    dialog.successhow()
                }
            }
        }
    }

    class makeDialog(context: Context) {
        private val dialog = Dialog(context)
        val successsnd = MediaPlayer.create(context, R.raw.success)
        val failsnd = MediaPlayer.create(context, R.raw.fail)

        fun successhow() {
            dialog.setContentView(R.layout.dialog_success)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            successsnd.start()
        }
        fun failshow(){
            dialog.setContentView(R.layout.dialog_fail)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(true)
            dialog.show()
            failsnd.start()
        }


    }

}