package com.example.eldroid_teacher_side.ui.data

data class FAQItem(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)