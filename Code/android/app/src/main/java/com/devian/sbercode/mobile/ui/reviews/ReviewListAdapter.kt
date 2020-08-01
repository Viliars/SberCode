package com.devian.sbercode.mobile.ui.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.domain.model.ReviewEntity

class ReviewListAdapter(
    private var list: List<ReviewEntity>,
    private var listener: OnNewsItemClickedListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val review: ReviewEntity = list[position]
        holder.bind(review, listener)
    }

    override fun getItemCount(): Int = list.size

    fun setReviews(reviews : List<ReviewEntity>) {
        list = reviews
        notifyDataSetChanged()
    }

    interface OnNewsItemClickedListener {
        fun onItemClicked(review: ReviewEntity)
    }

}

class NewsViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view) {

    private var mDate: TextView? = null
    private var mTitle: TextView? = null
    private var mDescription: TextView? = null

    init {
        mDate = itemView.findViewById(R.id.tvDate)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mDescription = itemView.findViewById(R.id.tvDescription)
    }

    fun bind(review: ReviewEntity, clickedListener: ReviewListAdapter.OnNewsItemClickedListener) {
        mDate?.text = review.date
        mTitle?.text = review.text
        mDescription?.text = review.app_id
        view.setOnClickListener { clickedListener.onItemClicked(review) }
    }
}