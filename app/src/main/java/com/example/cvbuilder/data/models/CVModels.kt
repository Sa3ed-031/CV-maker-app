package com.example.cvbuilder.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CVData(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val summary: String = "",
    val degree: String = "",
    val institution: String = "",
    val jobTitle: String = "",
    val company: String = "",
    val skills: List<String> = emptyList()
) : Parcelable

// Mock database provider for demonstration local state persistence
object MockCVDatabase {
    val savedCVs = mutableListOf<CVData>()
}