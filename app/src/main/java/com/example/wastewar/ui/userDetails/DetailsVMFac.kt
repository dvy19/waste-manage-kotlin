package com.example.wastewar.ui.userDetails


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class DetailsVmFac(
    private val repository: DetailsRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsVM::class.java)) {
            return DetailsVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}