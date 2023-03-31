package com.example.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.description.Description
import com.example.interested.databinding.FragmentHomeSeoulBinding
import com.example.network.RetrofitClient
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeSeoul.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeSeoul : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewbinding: FragmentHomeSeoulBinding

    lateinit var mainActivity: Home

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Home
    }

    var dataList: ArrayList<Homedata> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }



        val region = "서울"
        RetrofitWork(region).work()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragmentHomeSeoulBinding.inflate(layoutInflater)
        //setOnClickListener()

        val DataRVAdapter = HomeAdapter1(mainActivity, dataList)
        Log.e("homeseoul.kt의 datalist:","$dataList")

        return viewbinding.root

    }

    inner class RetrofitWork(private val region: String){
        fun work(){
            Log.e("정책 불러오기","시작")
            val service = RetrofitClient.emgMedService

            service.HomeDataGet(region)
                .enqueue(object : retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("정책 불러오기 성공","$responsebody")

                            //2.정책 result array 형식으로 뽑아옴
                            val jsonArray = response.body()?.getAsJsonArray("result")
                            Log.e("정책 array:","$jsonArray")

                            //3. 정책 array에서 정책 다섯개 뽑아옴
                            if (jsonArray != null) {
                                var a: Int = 0
                                while(a <= 4){
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val policyname = Jsonfor.get("policyName").getAsString()
                                    val department = Jsonfor.get("department").getAsString()
                                    Log.e("정책 이름, 정책 부서:", "$policyname $department")

                                    dataList.add(
                                        Homedata(
                                            policyname,
                                            department
                                        )
                                    )
                                    a+=1
                                }




                                Log.e("Homeseoul 정책 datalist","$dataList")
                                val DataRVAdapter = HomeAdapter1(mainActivity, dataList)
                                viewbinding.rvRefrigerator.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)
                                viewbinding.rvRefrigerator.adapter = DataRVAdapter

                            }
                        }
                        else {
                            val code = response.code()
                            Log.e("정책 불러오기 상태","$code")
                        }
                    }
                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.e("정책 불러오기 실패",t.message.toString())
                    }
                })
        }


    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeSeoul.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeSeoul().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }



}
