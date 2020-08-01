package com.devian.sbercode.mobile.ui.reviews

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.ReviewEntity
import com.devian.sbercode.mobile.extensions.observeOnUi
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
            reviewsRepository.getReviews(lastId).observeOnUi()
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
            reviewsRepository.getClasses().observeOnUi()
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}