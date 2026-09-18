package com.example.wastewar.ui.userDetails


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wastewar.ui.user.item.AddItemRepo
import com.example.wastewar.ui.user.item.AddItemVM


class AddItemFac(
    private val repository: AddItemRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddItemVM::class.java)) {
            return AddItemVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}