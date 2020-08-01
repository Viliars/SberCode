package com.devian.sbercode.mobile.ui.dashboard

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.DailyInfoEntity
import com.devian.sbercode.mobile.extensions.observeOnUi
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val showLoading = ObservableBoolean(false)
    val errorMessage = ObservableField<String>()

    val dailyInfo = ObservableField<DailyInfoEntity>()

    fun updateDailyInfo() {
        errorMessage.set(null)
        showLoading.set(true)

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getDailyInfo().observeOnUi()
                .doAfterTerminate {
                    showLoading.set(false)
                }
                .subscribe({
                    dailyInfo.set(it)
                }, {
                    showLoading.set(false)
                    errorMessage.set(it.message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}