package com.math.watermelon

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.math.watermelon.databinding.FragmentFavoriteBinding
import com.math.watermelon.databinding.FragmentTestBinding
import com.math.watermelon.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TestFragment : Fragment() {

    private var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentTestBinding
    private var adapter: TestRecylcerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false)
        val layoutManager = GridLayoutManager(requireContext(), 3)
        val db = AppDatabase.getInstance(mainActivity!!)

        val divider_VERTICAL = DividerItemDecoration(mainActivity!!, GridLayoutManager.VERTICAL)
        val divider_HORIZONTAL = DividerItemDecoration(mainActivity!!, GridLayoutManager.HORIZONTAL)
        divider_VERTICAL.setDrawable(mainActivity!!.resources.getDrawable(R.drawable.divider_vertical,null))
        divider_HORIZONTAL.setDrawable(mainActivity!!.resources.getDrawable(R.drawable.divider_vertical,null))

        GlobalScope.launch(Dispatchers.IO) {
            var list =db.DataDao().getCollegeqnumber()
            adapter = TestRecylcerAdapter(list,parentFragmentManager,mainActivity!!)
            launch(Dispatchers.Main) {
                binding.testRecycle.addItemDecoration(divider_VERTICAL)
                binding.testRecycle.addItemDecoration(divider_HORIZONTAL)
                binding.testRecycle.layoutManager = layoutManager
                binding.testRecycle.adapter = adapter
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