package com.devian.sbercode.mobile.ui.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private var disposable: CompositeDisposable? = null

    private val _reviews = MutableLiveData<List<ReviewEntity>>()
    private val _loadError = MutableLiveData<Boolean>(false)
    private val _loading = MutableLiveData<Boolean>(false)

    init {
        fetchReviews()
    }

    fun fetchReviews() {
        _loading.value = true

        disposable?.dispose()
        disposable = CompositeDisposable()
        disposable!!.add(
            reviewsRepository.getReviews().observeOnUi()
                .subscribe({
                    _loadError.value = false
                    _loading.value = false
                    _reviews.value = it
                }, {
                    _loadError.value = true
                    _loading.value = false
                })
        )
    }

    fun getReviews(): LiveData<List<ReviewEntity>> {
        return _reviews
    }

    fun getLoadError(): LiveData<Boolean> {
        return _loadError
    }

    fun getLoading(): LiveData<Boolean> {
        return _loading
    }
}