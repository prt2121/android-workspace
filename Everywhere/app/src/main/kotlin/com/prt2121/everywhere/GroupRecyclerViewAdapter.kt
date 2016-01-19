package com.prt2121.everywhere

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.prt2121.everywhere.GroupFragment.OnListFragmentInteractionListener
import com.prt2121.everywhere.model.DummyContent.Group

/**
 * Created by pt2121 on 1/18/16.
 *
 * [RecyclerView.Adapter] that can display a [Group] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class GroupRecyclerViewAdapter(private val mValues: List<Group>, private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<GroupRecyclerViewAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_group, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.mItem = mValues[position]
    holder.mIdView.text = mValues[position].id
    holder.mContentView.text = mValues[position].content

    holder.mView.setOnClickListener {
      mListener?.onListFragmentInteraction(holder.mItem!!)
    }
  }

  override fun getItemCount(): Int {
    return mValues.size
  }

  inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    val mIdView: TextView
    val mContentView: TextView
    var mItem: Group? = null

    init {
      mIdView = mView.findViewById(R.id.id) as TextView
      mContentView = mView.findViewById(R.id.content) as TextView
    }

    override fun toString(): String {
      return super.toString() + " '" + mContentView.text + "'"
    }
  }
}
