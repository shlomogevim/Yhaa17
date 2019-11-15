package com.example.yhaa17

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

typealias CurrentConv=(Conversation)->Unit

class ConvListAtapter (
    private val convList:ArrayList<Conversation>
,private val onClickListener:(Conversation)->Unit
): RecyclerView.Adapter<ConvListAtapter.ConvViewHolder>(){

    fun updateConvList(newList:ArrayList<Conversation>){
        convList.clear()
        convList.addAll(newList)
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConvViewHolder {
        val inftater=LayoutInflater.from(parent.context)
        val view=inftater.inflate(R.layout.item_row_layout,parent,false)
        return ConvViewHolder(view)

    }

    override fun getItemCount()=convList.size

    override fun onBindViewHolder(holder: ConvViewHolder, position: Int) {
        val conv=convList[position]
        holder.view.setOnClickListener { v ->
            onClickListener(conv)
        }

        var st1="שיחה מספר :"
        var st2="${st1} ${convList[position].numC}"

        holder.view.tv_conNum.text=st2
        holder.view.tv_ConvTitle.text=convList[position].title
        holder.view.tv_ConvExplantion.text=convList[position].explanation
    }

    class ConvViewHolder(var view:View):RecyclerView.ViewHolder(view)
}