package com.example.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.FragmentWorkBinding
import com.example.network.PolicyFieldRequestBody
import com.example.network.RetrofitClient
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewbinding: FragmentWorkBinding

    lateinit var mainActivity: Search
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Search
    }
    //var dataList: ArrayList<RVdata> = arrayListOf(
        //RVdata("정책이름","부서이름","지역","나이","정책한줄설명","#해시태그"),
    //)

    var dataList: ArrayList<RVdata> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        val field = "일자리"
        RetrofitWork(field).work()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewbinding = FragmentWorkBinding.inflate(layoutInflater)
        //setOnClickListener()

        val DataRVAdapter = DataRVAdapter(mainActivity, dataList)
        return viewbinding.root
    }


    inner class RetrofitWork(private val field: String){
        fun work(){
            Log.e("정책 불러오기","시작")
            val service = RetrofitClient.emgMedService

            service.PolicyFieldGet(field)
                .enqueue(object : retrofit2.Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if(response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("정책 불러오기 성공","$responsebody")

                            val jsonObject = response.body()?.getAsJsonObject("result")
                            val jsonObject2 = jsonObject?.getAsJsonObject("empsInfo")
                            val jsonArray = jsonObject2?.getAsJsonArray("emp")

                            //2.정책 result array 형식으로 뽑아옴
                            //val jsonArray = response.body()?.getAsJsonArray("result")
                            Log.e("정책 검색 화면 jsonobject:","$jsonObject")
                            Log.e("정책 검색 화면 jsonobject2:","$jsonObject2")
                            Log.e("정책 검색 화면 array:","$jsonArray")

                            //3. 정책 array에서 정책 다섯개 뽑아옴
                            if (jsonArray != null) {
                                var a: Int = 0
                                while(a <= jsonArray.size()-1){
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val name = Jsonfor.get("polyBizSjnm").getAsString()
                                    val publicName = Jsonfor.get("cnsgNmor").getAsString()
                                    val field = Jsonfor.get("plcyTpNm").getAsString()
                                    val age = Jsonfor.get("ageInfo").getAsString()
                                    val explain = Jsonfor.get("polyItcnCn").getAsString()
                                    val date = Jsonfor.get("rqutPrdCn").getAsString()

                                    //Log.e("정책 이름, 정책 부서:", "$policyname $department")

                                    dataList.add(
                                        RVdata(
                                            name,
                                            publicName,
                                            field,
                                            age,
                                            explain,
                                            date
                                        )
                                    )
                                    a+=1
                                }
                                val DataRVAdapter = DataRVAdapter(mainActivity, dataList)
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

//    private fun setOnClickListener(){
//        val endDate = viewbinding.endDate
//        val spelling = viewbinding.spelling
//
//        endDate.setOnClickListener(){
//            viewbinding.endDate.setTypeface(null,Typeface.BOLD)
//            viewbinding.spelling.setTypeface(null,Typeface.NORMAL)
//        }
//
//        spelling.setOnClickListener(){
//            viewbinding.endDate.setTypeface(null,Typeface.NORMAL)
//            viewbinding.spelling.setTypeface(null,Typeface.BOLD)
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
         * @return A new instance of fragment WorkFragment.
         */
        // TODO: Rename and change types and number of parameters
//        @JvmStatic
////        fun newInstance(param1: String, param2: String) =
////            WorkFragment().apply {
////                arguments = Bundle().apply {
////                    putString(ARG_PARAM1, param1)
////                    putString(ARG_PARAM2, param2)
////                }
////            }
    }
}