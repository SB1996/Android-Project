package com.santanu.android.application.ui.adapter.recycler;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.santanu.android.application.R
import com.santanu.android.application.databinding.RecyclerLayoutBinding

class RecyclerAdapter internal constructor(
    private val dataList: ArrayList<String>,
    private val callback: RecyclerAdapterListener?
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var mLayout: RecyclerLayoutBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ViewHolder {
        /** inflate layout by ViewBinding **/
        // mLayout = RecyclerLayoutBinding.inflate(LayoutInflater.from(viewGroup.context))

        /** inflate layout by DataBinding **/
        mLayout = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.recycler_layout,viewGroup, false)

        return ViewHolder(mLayout, callback)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.onBindData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(
        mLayout: RecyclerLayoutBinding, mListener: RecyclerAdapterListener?
    ) : RecyclerView.ViewHolder(mLayout.root), View.OnClickListener {

        init {
            mLayout.root.setOnClickListener {
                mListener?.onRecyclerItemSelectListener(adapterPosition)
            }
        }

        internal fun onBindData(data: String) {
           /** bind your data with view **/


            mLayout.executePendingBindings()
        }
        override fun onClick(view: View?) {

        }
    }

    interface RecyclerAdapterListener {
        fun onRecyclerItemSelectListener(position: Int)
    }

}