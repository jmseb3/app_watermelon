package com.math.watermelon


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.math.watermelon.databinding.FragmentConceptBinding
import com.math.watermelon.room.AppDatabase


class conceptFragment : Fragment() {

    internal var mainActivity: MainActivity? = null
    private lateinit var binding: FragmentConceptBinding
    private var adapter: itemRecylcerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConceptBinding.inflate(inflater, container, false)
        val db = AppDatabase.getInstance(requireContext())

        binding.btnmath1.setOnClickListener {
            change("수학1")
        }
        binding.btnmath2.setOnClickListener {
            change("수학2")
        }
        binding.btnrate.setOnClickListener {
            change("확률과 통계")
        }
        binding.btncalculus.setOnClickListener {
            change("미적분")
        }

        binding.search.setOnClickListener {
            if (binding.searchtext.visibility == View.GONE) {
                binding.searchtext.visibility = View.VISIBLE
                binding.searchtext.requestFocus()
            } else {
                binding.searchtext.visibility = View.GONE
                binding.searchtext.setText("")
                mainActivity!!.hideKeyboard(binding.searchtext)
            }
        }

        binding.searchtext.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                parentFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.fragcontainer, subMenuFragment()
                        .apply {
                            arguments = Bundle().apply {
                                putString("title", "검색결과")
                                putString("search", binding.searchtext.text.toString())
                            }
                        }).commit()
                binding.searchtext.setText("")
                binding.searchtext.visibility = View.GONE
                mainActivity!!.hideKeyboard(binding.searchtext)
            }

            return@setOnEditorActionListener true

        }


        return binding.root
    }

    fun change(title: String) {
        parentFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .add(R.id.fragcontainer, subMenuFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString("title", title)
                    }
                }).commit()
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