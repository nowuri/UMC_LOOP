package com.example.mypage

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.MypageLikeItemBinding

class MyLikeAdapter(val context: Context, private val dataList: ArrayList<LikeData>): RecyclerView.Adapter<MyLikeAdapter.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: MypageLikeItemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: LikeData, context: Context){
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName
            viewBinding.Dday.text = data.Dday

            val name = viewBinding.name
            viewBinding.layoutTouch.setOnClickListener(){
                Toast.makeText(context,name.toString()+"을 불러옵니다.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = MypageLikeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int = dataList.size
}