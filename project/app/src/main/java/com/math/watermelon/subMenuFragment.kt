package com.math.watermelon

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.math.watermelon.databinding.FragmentSubMenuBinding
import com.math.watermelon.room.AppDatabase


class subMenuFragment : Fragment() {

    internal var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentSubMenuBinding
    private var adapter: itemRecylcerAdapter? = null

    val title by lazy { requireArguments().getString("title") }
    val search by lazy { requireArguments().getString("search") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubMenuBinding.inflate(inflater, container, false)
        val db = AppDatabase.getInstance(requireContext())
        val divider_Vertical = DividerItemDecoration(mainActivity!!, LinearLayoutManager.VERTICAL)
        divider_Vertical.setDrawable(mainActivity!!.resources.getDrawable(R.drawable.divider_vertical,null))

        binding.mathlist.addItemDecoration(divider_Vertical)



        binding.sunmenutitle.text = title

        if (title != "검색결과") {
            db.DataDao().getmathdata(title!!)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    binding.listArea.visibility = View.VISIBLE
                    binding.searchtextArea.visibility = View.GONE
                    binding.searchtextInfo.visibility = View.GONE

                    adapter = itemRecylcerAdapter(it, parentFragmentManager, requireContext())
                    binding.mathlist.adapter = adapter
                })
        } else {
            db.DataDao().searchtopic(search!!)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it.isEmpty() || search == "") {
                        binding.listArea.visibility = View.GONE
                        binding.searchtextArea.visibility = View.VISIBLE

                    } else {
                        adapter = itemRecylcerAdapter(it, parentFragmentManager, requireContext())
                        binding.searchtextInfo.visibility = View.VISIBLE
                        binding.searchtextInfo.text = """""" + search + """""" + "에 대한 검색결과입니다."
                        binding.mathlist.adapter = adapter
                    }
                })
        }


        binding.mathback.setOnClickListener {
            parentFragmentManager.beginTransaction().remove(this).commit()
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