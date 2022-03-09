package ir.es.mohammad.cities

import androidx.lifecycle.ViewModel
import androidx.recyclerview.selection.MutableSelection
import androidx.recyclerview.selection.Selection

class MyViewModel: ViewModel() {
    var cities: ArrayList<String> =
        arrayListOf("City 1", "City 2", "City 3", "City 4", "City 5", "City 6", "City 7", "City 8")

    var selectedIndices: MutableSelection<Long>? = null

    fun removeAt(index: Int) {
        for ((ind, selected) in selectedIndices!!.withIndex()) {
            if (index == ind) {
                selectedIndices?.remove(selected)
                break
            }
        }
    }

    fun getSelectedCities(): ArrayList<String>{
        val selectedCities: ArrayList<String> = arrayListOf()
        selectedIndices?.forEach { selectedCities.add(cities[it.toInt()]) }
        return selectedCities
    }
}