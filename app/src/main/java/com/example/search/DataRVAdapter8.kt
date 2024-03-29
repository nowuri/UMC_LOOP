package com.example.search

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.R
import com.example.interested.databinding.ItemViewBinding

//경기
class DataRVAdapter8(val context: Context, private val dataList: ArrayList<RVdata>): RecyclerView.Adapter<DataRVAdapter8.DataViewHolder>() {
    var i = true

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    inner class DataViewHolder(private val viewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(data: RVdata, context: Context) {
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName
            viewBinding.field.text = data.field
            viewBinding.age.text = data.age
            viewBinding.explain.text = data.explain
            viewBinding.date.text = data.date
            viewBinding.share.setOnClickListener() {
                val shareIntent = Intent().apply{
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        "URL 공유합니다"
                    )
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent,"Share"))

            }
            viewBinding.heart.setOnClickListener() {
                if (i == true) {
                    viewBinding.heart.setImageResource(R.drawable.filledheart)
                    i = false
                } else {
                    viewBinding.heart.setImageResource(R.drawable.heart)
                    i = true
                }
            }
            viewBinding.firstLine.setOnClickListener() {
                Toast.makeText(
                    context,
                    "경기 " + bindingAdapterPosition.toString() + "번째 정책입니다.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("정책", "누름")
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataRVAdapter8.DataViewHolder {
        val viewBinding = ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)

    }

    override fun onBindViewHolder(holder: DataRVAdapter8.DataViewHolder, position: Int) {
        holder.bind(dataList[position],context)
    }

    override fun getItemCount(): Int = dataList.size
}