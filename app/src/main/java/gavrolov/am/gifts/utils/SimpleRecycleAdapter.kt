package gavrolov.am.gifts.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleRecycleAdapter<T : RecyclerView.ViewHolder?, S>(val data: MutableList<S>) :
    RecyclerView.Adapter<T>(), DeletableAdapter<S> {

    override fun getItemCount(): Int = data.size

    override fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun restoreItem(item: S, position: Int) {
        data.add(position, item)
        notifyItemInserted(position)
    }

    override fun getAdapterData(): MutableList<S> {
        return data
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun setAdapterData(newData: MutableList<S>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
}