package com.devian.sbercode.mobile.ui.reviews

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.devian.sbercode.mobile.R
import com.devian.sbercode.mobile.databinding.FragmentReviewsBinding
import com.devian.sbercode.mobile.di.Scopes
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.domain.model.ReviewWrongClassEntity
import com.devian.sbercode.mobile.extensions.addOnPropertyChanged
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.ui.BaseFragment
import com.devian.sbercode.mobile.ui.reviews.ReviewListAdapter.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_reviews.*
import kotlinx.android.synthetic.main.review_item.*
import toothpick.Toothpick

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
            adapter = ReviewListAdapter(ArrayList(), this@ReviewsFragment)
        }
        observableViewModel()
        setupView()
    }

    private fun observableViewModel() {
        viewModel.reviews.addOnPropertyChanged {
            val newList = it.get()
            if (!newList.isNullOrEmpty()) {
                val adapter = recyclerReviews.adapter as ReviewListAdapter
                if (adapter.getLastItemId().toInt() > newList[newList.size-1].id.toInt()) {
                    // add new elements to existing list
                    adapter.setReviews(ArrayList(listOf(adapter.getList(), newList).flatten()))
                } else {
                    // replace list
                    adapter.setReviews(ArrayList(newList))
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

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val settingsPreferences = Toothpick.openScope(Scopes.APP).getInstance(SettingsPreferences::class.java)
        val adapter = recyclerReviews.adapter as ReviewListAdapter
        when (item.itemId) {
            1000 -> {
                val popupMenu = PopupMenu(context, cardView)
                for (c in settingsPreferences.reviewClasses) {
                    popupMenu.menu.add(1, c.id.toInt(), c.id.toInt(), c.name)
                }
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener { it ->
                    val rightClass = ReviewWrongClassEntity(
                        reviewId = adapter.getItemIdByPosition(item.groupId).toInt(),
                        rightClassId = it.itemId.toString()
                    )
                    viewModel.pushError(rightClass)
                    viewModel.errorPushed.addOnPropertyChanged {
                        if (it.get() != null) {
                            if (it.get()!!.success) {
                                showSnackbar("Категория изменена")
                            } else {
                                showSnackbar("Ошибка, попробуйте позже")
                            }
                        }
                    }
                    return@setOnMenuItemClickListener true
                }
                return true
            }
            1001 -> {
                //todo answer to review
                return true
            }
        }
        return true
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }
}