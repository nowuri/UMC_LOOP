package com.example.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.HomeItemBinding

class HomeAdapter1(val context: Home, private var dataList: ArrayList<Homedata>)
    : RecyclerView.Adapter<HomeAdapter1.DataViewHolder>() {

    inner class DataViewHolder(private val viewBinding: HomeItemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: Homedata, context: Context){
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = HomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }
    override fun getItemCount(): Int = dataList.size
}