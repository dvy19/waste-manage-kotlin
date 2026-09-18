package com.example.wastewar.ui.user.admin


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wastewar.ui.user.item.AddItemRepo
import com.example.wastewar.ui.user.item.AddItemVM


class AdminVmFac(
    private val repository: AdminRepo
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminVM::class.java)) {
            return AdminVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}