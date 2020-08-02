package com.devian.sbercode.mobile.ui.dashboard

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.devian.sbercode.mobile.domain.model.DailyInfoEntity
import com.devian.sbercode.mobile.extensions.asyncIo
import com.devian.sbercode.mobile.repository.local.SettingsPreferences
import com.devian.sbercode.mobile.repository.network.ReviewsRepository
import io.reactivex.disposables.CompositeDisposable
import java.lang.StringBuilder
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val reviewsRepository: ReviewsRepository,
    private val settingsPreferences: SettingsPreferences
) : ViewModel() {

    private var compositeDisposable: CompositeDisposable? = null

    val showLoading = ObservableBoolean(false)
    val errorMessage = ObservableField<String>()

    val dailyInfo = ObservableField<DailyInfoEntity>()

    val showDemoReview = ObservableInt(View.VISIBLE)
    val demoReview1 = ObservableField<String>()
    val demoReview2 = ObservableField<String>()
    val demoReview3 = ObservableField<String>()

    val topClasses = ObservableField<String>()

    fun updateDailyInfo() {
        errorMessage.set(null)
        showLoading.set(true)

        compositeDisposable = CompositeDisposable()
        compositeDisposable!!.add(
            reviewsRepository.getDailyInfo().asyncIo()
                .doAfterTerminate {
                    showLoading.set(false)
                }
                .subscribe({
                    processDailyInfo(it)
                }, {
                    showLoading.set(false)
                    errorMessage.set(it.message)
                })
        )
    }

    private fun processDailyInfo(it: DailyInfoEntity) {
        dailyInfo.set(it)
        if (it.highlights.size >= 3) {
            showDemoReview.set(View.VISIBLE)
            demoReview1.set(it.highlights[0].text)
            demoReview2.set(it.highlights[1].text)
            demoReview3.set(it.highlights[2].text)
        } else {
            showDemoReview.set(View.GONE)
        }
        val top = StringBuilder()
        var i = 1
        if (!it.topClasses.isNullOrEmpty()) {
            for (c in it.topClasses) {
                top.append("$i. ")
                top.append(getNameByClassId(c._class))
                top.append("\n")
                i++
            }
        }
        topClasses.set(top.toString())
    }

    private fun getNameByClassId(id: String): String {
        val list = settingsPreferences.reviewClasses
        for (c in list) {
            if (id == c.id)
                return c.name
        }
        return id
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose()
    }
}