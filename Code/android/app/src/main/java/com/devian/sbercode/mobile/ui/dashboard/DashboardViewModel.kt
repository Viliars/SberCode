package com.devian.sbercode.mobile.ui.dashboard

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.extensions.DateFormat
import com.devian.sbercode.mobile.extensions.format
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import org.threeten.bp.ZonedDateTime
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val showLoading = ObservableBoolean(false)
    val showError = ObservableBoolean(false)
    val date = ObservableField<String>("")

    val reviews = ObservableField<String>("")

    init {
        updateData()
    }

    fun updateData() {
        showError.set(false)
        showLoading.set(true)

        date.set(ZonedDateTime.now().format(DateFormat.DD_MMMM_HH_MM))

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getReviews().observeOnUi()
                .doAfterTerminate {
                    showLoading.set(false)
                }
                .subscribe({
                    reviews.set("Отзывов: " + it.size.toString())
                }, {
                    showLoading.set(false)
                    showError.set(true)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}