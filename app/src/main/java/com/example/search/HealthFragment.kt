package com.example.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.FragmentHealthBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HealthFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HealthFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewbinding: FragmentHealthBinding

    lateinit var mainActivity: Search
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Search
    }
    var dataList: ArrayList<RVdata> = arrayListOf(
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
        RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragmentHealthBinding.inflate(layoutInflater)
        //setOnClickListener()

        var list: ArrayList<RVdata> = dataList
        val DataRVAdapter = DataRVAdapter5(mainActivity,list)
        viewbinding.rvRefrigerator.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        viewbinding.rvRefrigerator.adapter = DataRVAdapter

        return viewbinding.root
    }

//    private fun setOnClickListener(){
//        val endDate = viewbinding.endDate
//        val spelling = viewbinding.spelling
//
//        endDate.setOnClickListener(){
//            viewbinding.endDate.setTypeface(null, Typeface.BOLD)
//            viewbinding.spelling.setTypeface(null, Typeface.NORMAL)
//        }
//
//        spelling.setOnClickListener(){
//            viewbinding.endDate.setTypeface(null, Typeface.NORMAL)
//            viewbinding.spelling.setTypeface(null, Typeface.BOLD)
//        }
//
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HealthFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HealthFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}