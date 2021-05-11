package com.math.watermelon

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.math.watermelon.databinding.FragmentFavoriteBinding
import com.math.watermelon.room.AppDatabase


class FavoriteFragment : Fragment() {

    internal var mainActivity: MainActivity? = null
    private var adapter_one: favoriteitemRecylcerAdapter? = null
    private var adapter_two: favoriteitemRecylcerAdapter? = null
    private var adapter_three: favoriteitemRecylcerAdapter? = null
    private var adapter_four: favoriteitemRecylcerAdapter? = null

    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        val db = AppDatabase.getInstance(requireContext())

        val manager = ReviewManagerFactory.create(requireContext())
        val request = manager.requestReviewFlow()
        val divider_Vertical = DividerItemDecoration(mainActivity!!, LinearLayoutManager.VERTICAL)
        divider_Vertical.setDrawable(mainActivity!!.resources.getDrawable(R.drawable.divider_vertical,null))

        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                val flow = manager.launchReviewFlow(requireActivity(), reviewInfo)
                flow.addOnCompleteListener { _ ->
                    // The flow has finished. The API does not indicate whether the user
                    // reviewed or not, or even whether the review dialog was shown. Thus, no
                    // matter the result, we continue our app flow.
                }
            } else {
                // There was some problem, log or handle the error code.
//                    @ReviewErrorCode val reviewErrorCode = (task.getException() as TaskException).errorCode
            }
        }
        val conceptList = listOf<String>("수학1", "수학2", "미적분", "확률과 통계")
        val adapterList = mutableListOf(adapter_one, adapter_two, adapter_three, adapter_four)
        var checkList = mutableListOf<Boolean>(false, false, false, false)
        val viewList = listOf(binding.favoriteMath1, binding.favoriteMath2, binding.favoriteCalculus, binding.favoriteRate)

        conceptList.forEachIndexed { index, s ->
            db.DataDao().getfavoritdataWithConcept(s).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                if (it.isEmpty()) {
                    viewList[index].visibility = View.GONE
                    checkList[index] = false

                } else {
                    viewList[index].visibility = View.VISIBLE
                    viewList[index].addItemDecoration(divider_Vertical)
                    checkList[index] = true

                    adapterList[index] = favoriteitemRecylcerAdapter(it, parentFragmentManager, requireContext(), conceptList[index])
                    viewList[index].adapter = adapterList[index]
                }

                if (true in checkList) {
                    binding.noFavoriteArea.visibility = View.GONE
                } else {
                    binding.noFavoriteArea.visibility = View.VISIBLE
                }

            })

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



