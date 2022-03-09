package ir.es.mohammad.cities

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.es.mohammad.cities.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var mAdapter: CitiesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSecondBinding.bind(view)


        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                Log.d("TAGG", "a")
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("TAGG", viewModel.selectedIndices.toString() + "1")
                remove(viewHolder.adapterPosition)
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewSecond)
        binding.recyclerViewSecond.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = CitiesAdapter(requireContext(), viewModel.getSelectedCities())
        binding.recyclerViewSecond.adapter = mAdapter
    }

    private fun remove(position: Int) {
        Log.d("TAGG", viewModel.selectedIndices.toString() + "2")
        viewModel.removeAt(position)
        Log.d("TAGG", viewModel.selectedIndices.toString() + "3")
        mAdapter.remove(position)
    }
}