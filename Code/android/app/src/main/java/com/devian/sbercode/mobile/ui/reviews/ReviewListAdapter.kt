package com.devian.sbercode.mobile.ui.reviews

import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.domain.model.ReviewEntity

class ReviewListAdapter(
    private var list: ArrayList<ReviewEntity>,
    private var listener: OnNewsItemClickedListener
) : RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val review: ReviewEntity = list[position]
        holder.bind(review, listener)
    }

    override fun getItemCount(): Int = list.size

    fun getLastItemId(): String {
        return if (list.isEmpty())
            "0"
        else
            list[list.size-1].id
    }

    fun getItemIdByPosition(position: Int): String {
        if (list.isNullOrEmpty() || list.size - 1 < position) return ""
        return list[position].id
    }

    fun getList(): List<ReviewEntity> = list

    fun removeItem(position: Int) {
        if (list.isNullOrEmpty() || list.size - 1 < position) return
        list.removeAt(position)
        notifyDataSetChanged()
    }

    fun setReviews(reviews : ArrayList<ReviewEntity>) {
        list = reviews
        notifyDataSetChanged()
    }

    interface OnNewsItemClickedListener {
        fun onItemClicked(review: ReviewEntity)
    }

}

class NewsViewHolder(private val view: View) :
    RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

    private var mCardView: CardView
    private var mDate: TextView? = null
    private var mTitle: TextView? = null
    private var mDescription: TextView? = null

    init {
        mCardView = itemView.findViewById(R.id.cardView)
        mDate = itemView.findViewById(R.id.tvDate)
        mTitle = itemView.findViewById(R.id.tvTitle)
        mDescription = itemView.findViewById(R.id.tvDescription)
    }

    fun bind(review: ReviewEntity, clickedListener: ReviewListAdapter.OnNewsItemClickedListener) {
        mDate?.text = review.date
        mTitle?.text = review.text
        mDescription?.text = review.app_id
        view.setOnClickListener { clickedListener.onItemClicked(review)}
        mCardView.setOnCreateContextMenuListener(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        menu?.setHeaderTitle("Выберите опцию");
        menu?.add(this.adapterPosition, 1000, 0, "Неверная категория")
        menu?.add(this.adapterPosition, 1001, 1, "Ответить на отзыв")
    }

}