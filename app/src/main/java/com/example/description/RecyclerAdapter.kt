package com.example.description

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.R
import com.example.interested.databinding.FragmentDescriptionBinding

class RecyclerAdapter(private var dataList: List<DescriptionData>, private val context: Description2) : RecyclerView.Adapter<RecyclerAdapter.DataViewHolder>() {
    init {
        Log.e("RecyclerAdapter", "Adapter created") // 생성 확인용 로그
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_description, parent, false)
        return DataViewHolder(FragmentDescriptionBinding.bind(view))
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position], context)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class DataViewHolder(private val viewBinding: FragmentDescriptionBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        init {
            Log.e("DataViewHolder", "ViewHolder created") // 생성 확인용 로그
        }

        fun bind(data: DescriptionData, context: Context) {
            Log.e("Data", data.toString()) // 데이터 확인용 로그

            viewBinding.policyUrl.text = data.url
            viewBinding.policyProcess.text = data.process
            viewBinding.policyRequirements.text = data.requirements
            viewBinding.policyIntroduce.text = data.introduce
            viewBinding.policyAssist.text = data.assist
        }
    }

    fun setData(dataList: List<DescriptionData>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }
}
