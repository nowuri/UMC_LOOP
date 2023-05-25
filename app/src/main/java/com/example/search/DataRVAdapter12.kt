package com.example.search
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interested.databinding.ItemView3Binding

class DataRVAdapter12(val context: Context, private val dataList2: ArrayList<RVdata2>): RecyclerView.Adapter<DataRVAdapter12.DataViewHolder>() {
    var i = true

    interface ItemClickListener{
        fun onClick(view: View, position: Int)
    }
    private lateinit var itemClickListener: ItemClickListener
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    inner class DataViewHolder(private val viewBinding: ItemView3Binding): RecyclerView.ViewHolder(viewBinding.root){
        fun bind(data: RVdata2, context: Context){
            viewBinding.name.text = data.name
            viewBinding.publicName.text = data.publicName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val viewBinding = ItemView3Binding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DataViewHolder(viewBinding)

    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(dataList2[position],context)
    }

    override fun getItemCount():  Int = dataList2.size

}