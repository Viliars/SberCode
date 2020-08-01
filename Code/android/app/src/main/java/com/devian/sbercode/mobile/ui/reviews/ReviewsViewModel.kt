package com.devian.sbercode.mobile.ui.reviews

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.ReviewClassEntity
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val reviews = ObservableField<List<ReviewEntity>>()
    val classes = ObservableField<List<ReviewClassEntity>>()

    val errorMessage = ObservableField<String>()
    val loading = ObservableBoolean(false)

    init {
        fetchReviews()
    }

    fun updateNews() {
        fetchReviews("0")
    }

    fun fetchReviews(lastId: String = "0") {
        loading.set(true)
        errorMessage.set(null)

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getReviews(lastId).observeOnUi()
                .subscribe({
                    loading.set(false)
                    reviews.set(it)
                }, {
                    errorMessage.set(it.message)
                    loading.set(false)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}