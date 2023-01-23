package com.example.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.R
import com.example.interested.databinding.FragmentHomeGangBinding
import com.example.interested.databinding.FragmentHomeGyeonggiBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeGang.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeGang : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewbinding: FragmentHomeGangBinding

    lateinit var mainActivity: Home
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Home
    }
    var dataList: ArrayList<Homedata> = arrayListOf(
        Homedata("정책이름","부서이름"),
        Homedata("정책이름","부서이름"),
        Homedata("정책이름","부서이름"),
        Homedata("정책이름","부서이름"),
        Homedata("정책이름","부서이름")
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
        viewbinding = FragmentHomeGangBinding.inflate(layoutInflater)
        //setOnClickListener()

        var list: ArrayList<Homedata> = dataList
        val DataRVAdapter = HomeAdapter4(mainActivity,list)
        viewbinding.rvRefrigerator.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
        viewbinding.rvRefrigerator.adapter = DataRVAdapter

        return viewbinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeGang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeGang().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}