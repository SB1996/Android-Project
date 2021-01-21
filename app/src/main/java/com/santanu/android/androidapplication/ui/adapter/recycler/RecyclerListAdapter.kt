package com.santanu.android.application.ui.adapter.recycler;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.santanu.android.application.R
import com.santanu.android.application.databinding.RecyclerLayoutBinding

class RecyclerListAdapter(
    private val mListener: RecyclerListAdapterCallback?
) : ListAdapter<String, RecyclerListAdapter.ViewHolder>(mDiffCallback) {
    private val TAG: String = RecyclerListAdapter::class.java.simpleName

    companion object {
        private val mDiffCallback: DiffUtil.ItemCallback<String> = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    private lateinit var mLayout: RecyclerLayoutBinding

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        /** inflate layout by ViewBinding **/
        // mLayout = RecyclerLayoutBinding.inflate(LayoutInflater.from(viewGroup.context))

        /** inflate layout by DataBinding **/
        mLayout = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.recycler_layout,viewGroup, false)

        return ViewHolder(mLayout, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindData(getItem(position))

    }


    inner class ViewHolder(
        mLayout: RecyclerLayoutBinding, mListener: RecyclerListAdapterCallback?
    ) : RecyclerView.ViewHolder(mLayout.root), View.OnClickListener {

        init {
            mLayout.root.setOnClickListener {
                mListener?.onRecyclerItemSelect(adapterPosition)
            }
        }

        internal fun onBindData(data: String) {
            /** bind your data with view **/


            mLayout.executePendingBindings()
        }
        override fun onClick(view: View?) {

        }
    }


    interface RecyclerListAdapterCallback {
        fun onRecyclerItemSelect(position: Int)
    }
}