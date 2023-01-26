package com.example.find

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.interested.R
import com.example.interested.databinding.FragmentIdfindBinding
import com.example.interested.databinding.FragmentPwfindBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Pwfind.newInstance] factory method to
 * create an instance of this fragment.
 */
class Pwfind : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewBinding: FragmentPwfindBinding

    lateinit var mainActivity: Find
    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = context as Find
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentPwfindBinding.inflate(layoutInflater)

        viewBinding.endbutton.setOnClickListener(){
            if(viewBinding.nameinput.getText().length == 0){
                Toast.makeText(mainActivity,"이름을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else if(viewBinding.emailinput.getText().length == 0){
                Toast.makeText(mainActivity,"이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                viewBinding.result.setBackgroundColor(Color.parseColor("#EEF2FF"))
                viewBinding.resulttext.setText("abc@abc.com으로 \n 메일을 보냈습니다.")
            }
        }

        return viewBinding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Pwfind.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Pwfind().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}