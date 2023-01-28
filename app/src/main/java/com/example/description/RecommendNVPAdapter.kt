package com.example.description

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.RecommendItemBinding

class RecommendNVPAdapter(val context: Context, private val dataList: ArrayList<RecommendData>): RecyclerView.Adapter<RecommendNVPAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: RecommendItemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: RecommendData, context: Context){
            viewBinding.id.text = data.id
            viewBinding.date.text = data.date
            viewBinding.reason.text = data.reason

            viewBinding.personalinfo.setOnClickListener(){
                Toast.makeText(context, "클릭", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = RecommendItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int = dataList.size
}