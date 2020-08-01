package com.devian.sbercode.mobile.ui.reviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.databinding.FragmentReviewsBinding
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.extensions.addOnPropertyChanged
import com.devian.sbercode.mobile.ui.BaseFragment
import com.devian.sbercode.mobile.ui.reviews.ReviewListAdapter.*
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : BaseFragment(), OnNewsItemClickedListener {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        createViewModel<ReviewsViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentReviewsBinding>(
            inflater,
            R.layout.fragment_reviews,
            container,
            false
        )
        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerReviews.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ReviewListAdapter(listOf(), this@ReviewsFragment)
        }
        observableViewModel()
        setupView()
    }

    private fun observableViewModel() {
        viewModel.reviews.addOnPropertyChanged {
            val newList = it.get()
            if (!newList.isNullOrEmpty()) {
                println("KEKW list update: size = ${newList.size}")
                val adapter = recyclerReviews.adapter as ReviewListAdapter
                if (adapter.getLastItemId().toInt() > newList[newList.size-1].id.toInt()) {
                    // add new elements to existing list
                    adapter.setReviews(listOf(adapter.getList(), newList).flatten())
                } else {
                    // replace list
                    adapter.setReviews(newList)
                }
            }
        }
        viewModel.errorMessage.addOnPropertyChanged {
            if (!it.get().isNullOrEmpty()) {
                Toast.makeText(context, it.get(), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onItemClicked(review: ReviewEntity) {
        Toast.makeText(context, review.text, Toast.LENGTH_LONG).show()
    }

    private fun setupView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerReviews.layoutManager = layoutManager
        recyclerReviews.addOnScrollListener ( object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val totalItemsCount = layoutManager.itemCount
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (lastVisibleItemPosition + 1 == totalItemsCount) {
                        val lastId = (recyclerView.adapter as ReviewListAdapter).getLastItemId()
                        viewModel.fetchReviews(lastId)
                    }
                }
            }
        })
    }
}