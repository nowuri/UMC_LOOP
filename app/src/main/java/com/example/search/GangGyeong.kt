package com.example.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.FragmentChungJunBinding
import com.example.interested.databinding.FragmentGangGyeongBinding
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
 * Use the [GangGyeong.newInstance] factory method to
 * create an instance of this fragment.
 */
class GangGyeong : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewbinding: FragmentGangGyeongBinding

    lateinit var mainActivity: Search
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Search
    }
    var dataList: ArrayList<RVdata> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        val region = "강원경상"
        RetrofitWork(region).work()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewbinding = FragmentGangGyeongBinding.inflate(layoutInflater)

        val DataRVAdapter10 = DataRVAdapter10(mainActivity, dataList)
        return viewbinding.root
    }

    inner class RetrofitWork(private val field: String) {
        fun work() {
            Log.e("정책 불러오기", "시작")
            val service = RetrofitClient.emgMedService

            service.PolicyRegionGet(field)
                .enqueue(object : retrofit2.Callback<JsonObject> {
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>,
                    ) {
                        if (response.isSuccessful()) {
                            val responsebody = response.body().toString()
                            Log.e("정책 불러오기 성공", "$responsebody")

                            val jsonObject = response.body()?.getAsJsonObject("result")
                            val jsonObject2 = jsonObject?.getAsJsonObject("empsInfo")
                            val jsonArray = jsonObject2?.getAsJsonArray("emp")

                            if (jsonArray != null) {
                                var a: Int = 0
                                while (a <= jsonArray.size() - 1) {
                                    val Jsonfor = jsonArray[a].getAsJsonObject()
                                    val name = Jsonfor.get("polyBizSjnm").getAsString()
                                    val publicName = Jsonfor.get("cnsgNmor").getAsString()
                                    val field = Jsonfor.get("plcyTpNm").getAsString()
                                    val age = Jsonfor.get("ageInfo").getAsString()
                                    val explain = Jsonfor.get("polyItcnCn").getAsString()
                                    val date = Jsonfor.get("rqutPrdCn").getAsString()

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
                                    a += 1
                                }
                                val DataRVAdapter10 = DataRVAdapter10(mainActivity, dataList)
                                viewbinding.rvRefrigerator.layoutManager =
                                    LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                                viewbinding.rvRefrigerator.adapter = DataRVAdapter10
                            }
                        } else {
                            val code = response.code()
                            Log.e("정책 불러오기 상태", "$code")
                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.e("정책 불러오기 실패", t.message.toString())
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
         * @return A new instance of fragment GangGyeong.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GangGyeong().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}