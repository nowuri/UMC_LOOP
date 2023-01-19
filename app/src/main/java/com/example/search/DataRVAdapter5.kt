package com.example.search

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.R
import com.example.interested.databinding.ItemViewBinding

//건강
class DataRVAdapter5(val context: Context, private val dataList: ArrayList<RVdata>): RecyclerView.Adapter<DataRVAdapter5.DataViewHolder>() {
    var i = true

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    inner class DataViewHolder(private val viewBinding: ItemViewBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data:RVdata, context: Context){
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName
            viewBinding.field.text = data.field
            viewBinding.age.text = data.age
            viewBinding.explain.text = data.explain
            viewBinding.hashtag.text = data.hashtag
            viewBinding.share.setOnClickListener(){
                Toast.makeText(context,"share",Toast.LENGTH_SHORT).show()

            }
            viewBinding.heart.setOnClickListener(){
                if(i==true){
                    viewBinding.heart.setImageResource(R.drawable.filledheart)
                    i = false
                }
                else {
                    viewBinding.heart.setImageResource(R.drawable.heart)
                    i = true
                }
            }
            viewBinding.firstLine.setOnClickListener(){
                Toast.makeText(context,"건강 "+adapterPosition.toString()+"번째 정책입니다.",Toast.LENGTH_SHORT).show()
                Log.d("정책","누름")
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int = dataList.size

}