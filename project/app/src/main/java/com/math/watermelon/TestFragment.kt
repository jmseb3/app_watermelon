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

        val grid_divider_Vertical = DividerItemDecoration(mainActivity!!, GridLayoutManager.VERTICAL)
        grid_divider_Vertical.setDrawable(mainActivity!!.resources.getDrawable(R.drawable.divider_vertical,null))


        adapter = TestRecylcerAdapter(arrayListOf("#1","#2","#3","#4"),parentFragmentManager,mainActivity!!,"수능",layoutManager)

        binding.testRecycle.addItemDecoration(grid_divider_Vertical)
        binding.testRecycle.layoutManager = layoutManager
        binding.testRecycle.adapter = adapter
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