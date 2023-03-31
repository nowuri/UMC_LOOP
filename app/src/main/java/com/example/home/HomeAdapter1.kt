package com.example.home

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.description.Description
import com.example.interested.databinding.HomeItemBinding

class HomeAdapter1(val context: Home, private var dataList: ArrayList<Homedata>) : RecyclerView.Adapter<HomeAdapter1.DataViewHolder>() {
    interface onDataPassListener {
        fun onDataPass(data:String?)
    }
    val intent = Intent(context, Description::class.java)

    //lateinit var dataPassListener: onDataPassListener

    inner class DataViewHolder(private val viewBinding: HomeItemBinding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: Homedata, context: Context){
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName

            viewBinding.name.setOnClickListener{
                //dataPassListener.onDataPass(data.name)
                intent.putExtra("name", data.name)
                context.startActivity(intent)
            }
            viewBinding.publicName.setOnClickListener{
                //dataPassListener.onDataPass(data.name)
                intent.putExtra("name", data.name)
                context.startActivity(intent)
            }
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