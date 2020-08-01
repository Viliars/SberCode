package com.devian.sbercode.mobile.ui.reviews

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.databinding.FragmentReviewsBinding
import com.devian.sbercode.mobile.domain.model.ReviewEntity
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
    }

    private fun observableViewModel() {
        viewModel.getReviews().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerReviews.visibility = View.VISIBLE
                (recyclerReviews.adapter as ReviewListAdapter).setNews(it)
            }
        })

        viewModel.getLoadError().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it) {
                    Toast.makeText(context, resources.getText(R.string.error_loading), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun onItemClicked(review: ReviewEntity) {
        Toast.makeText(context, review.text, Toast.LENGTH_LONG).show()
    }
}