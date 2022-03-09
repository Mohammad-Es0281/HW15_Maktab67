package ir.es.mohammad.cities

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.LinearLayoutManager
import ir.es.mohammad.cities.databinding.FragmentFirstBinding

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding
    private val viewModel: MyViewModel by activityViewModels()
    private lateinit var mAdapter: CitiesAdapter
    private lateinit var mTracker: SelectionTracker<Long>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFirstBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mAdapter = CitiesAdapter(requireContext(), viewModel.cities)
        binding.recyclerView.adapter = mAdapter

        mTracker = getTracker()
        mAdapter.tracker = mTracker

        binding.button.setOnClickListener {
            val selections = MutableSelection<Long>()
            selections.copyFrom(mTracker.selection)
            viewModel.selectedIndices = selections
            findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
        }
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.selectedIndices != null) {
            mTracker.setItemsSelected(viewModel.selectedIndices!!, true)
            Log.d("TAGG", viewModel.selectedIndices.toString())
        }
    }

    private fun getTracker(): SelectionTracker<Long> {
        val itemDetails = object : ItemDetailsLookup<Long>() {
            override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
                val currentView = binding.recyclerView.findChildViewUnder(e.x, e.y)
                return if (currentView != null) {
                    (binding.recyclerView.getChildViewHolder(currentView) as CitiesAdapter.CustomViewHolder).getViewHolderItemDetails()
                } else null
            }
        }

        return SelectionTracker.Builder(
            "selection1",
            binding.recyclerView,
            StableIdKeyProvider(binding.recyclerView),
            itemDetails,
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(SelectionPredicates.createSelectAnything()).build()
    }
}