package com.example.cvmaker

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CvViewModel : ViewModel() {
    // الخطوة 1: البيانات الشخصية والصورة
    var fullName by mutableStateOf("")
    var phone by mutableStateOf("")
    var nationalId by mutableStateOf("")
    var selectedImageUri by mutableStateOf<Uri?>(null)

    // الخطوة 2: المؤهلات الأكاديمية
    var university by mutableStateOf("")
    var specialty by mutableStateOf("")
    var gradYear by mutableStateOf("")

    // الخطوة 3: المهارات واللغات
    var skills by mutableStateOf("")
    var languages by mutableStateOf("")

    // الخطوة 4: المشاريع والإنجازات
    var projects by mutableStateOf("")
    var experience by mutableStateOf("")
}