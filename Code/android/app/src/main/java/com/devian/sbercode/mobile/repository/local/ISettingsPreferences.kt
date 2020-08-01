package com.devian.sbercode.mobile.repository.local

import com.devian.sbercode.mobile.domain.model.ReviewClassEntity

interface ISettingsPreferences {
    var authToken: String
    var reviewClasses: List<ReviewClassEntity>
}