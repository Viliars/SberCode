package com.devian.sbercode.mobile.ui.reviews

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.domain.model.ReviewWrongClassEntity
import com.devian.sbercode.mobile.extensions.asyncIo
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.network.model.ApiResponse
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ReviewsViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val reviews = ObservableField<List<ReviewEntity>>()
    val errorPushed = ObservableField<ApiResponse>()

    val errorMessage = ObservableField<String>()
    val loading = ObservableBoolean(false)

    init {
        updateData()
    }

    fun updateData() {
        fetchReviews()
        fetchClasses()
    }

    fun fetchReviews(lastId: String = "0") {
        loading.set(true)
        errorMessage.set(null)

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getReviews(lastId).asyncIo()
                .doAfterTerminate {
                    loading.set(false)
                }
                .subscribe({
                    reviews.set(it)
                }, {
                    loading.set(false)
                    errorMessage.set(it.message)
                })
        )
    }

    private fun fetchClasses() {
        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getClasses().asyncIo()
                .doAfterTerminate {
                    loading.set(false)
                }
                .subscribe({
                    if (!it.isNullOrEmpty()) {
                        settingsPreferences.reviewClasses = it
                    }
                }, {
                    loading.set(false)
                    errorMessage.set(it.message)
                })
        )
    }

    fun pushError(wrongClassEntity: ReviewWrongClassEntity) {
        errorPushed.set(null)
        loading.set(true)
        errorMessage.set(null)

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.pushReviewError(wrongClassEntity).asyncIo()
                .doAfterTerminate {
                    loading.set(false)
                }
                .subscribe({
                    errorPushed.set(it)
                }, {
                    val test = it
                    loading.set(false)
                    errorMessage.set(it.message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}