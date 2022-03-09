package ir.es.mohammad.cities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import ir.es.mohammad.cities.databinding.CityLayoutBinding

class CitiesAdapter(
    private val context: Context,
    private val cities: ArrayList<String>,
    var tracker: SelectionTracker<Long>? = null
) :
    RecyclerView.Adapter<CitiesAdapter.CustomViewHolder>() {

    init {
        setHasStableIds(true); }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = CityLayoutBinding.bind(itemView)

        init {
            binding.root.setOnClickListener {
                itemView.isSelected
            }
        }

        fun bind(city: String) {
            binding.textViewCity.text = city
        }

        fun getViewHolderItemDetails(): ItemDetails<Long> {
            return object : ItemDetails<Long>() {
                override fun getPosition(): Int {
                    return adapterPosition
                }

                override fun getSelectionKey(): Long {
                    return itemId
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_layout, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(cities[position])

        if (tracker != null) {
            val isSelected = tracker!!.isSelected(position.toLong())
            holder.itemView.isActivated = isSelected
        }
    }

    override fun getItemCount(): Int = cities.size

    override fun getItemId(position: Int): Long = position.toLong()

    fun remove(position: Int) {
        cities.removeAt(position)
        notifyItemRemoved(position)
    }
}