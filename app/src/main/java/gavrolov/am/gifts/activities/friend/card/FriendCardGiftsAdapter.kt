package gavrolov.am.gifts.activities.friend.card

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gavrolov.am.gifts.R
import gavrolov.am.gifts.app.helpers.CostFormatter
import gavrolov.am.gifts.app.model.GiftDto
import gavrolov.am.gifts.utils.SimpleRecycleAdapter

class FriendCardGiftsAdapter(data: MutableList<GiftDto>) :
    SimpleRecycleAdapter<FriendCardGiftsAdapter.FriendCardGiftsViewHolder, GiftDto>(data) {

    inner class FriendCardGiftsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val giftNameTextView: TextView = view.findViewById(R.id.gift_name_text_view)
        val giftIconImageView: ImageView = view.findViewById(R.id.gift_icon_image_view)
        val giftCostTextView: TextView = view.findViewById(R.id.gift_cost_text_view)

        lateinit var itemData: GiftDto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendCardGiftsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.gift_item, parent, false)

        return FriendCardGiftsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FriendCardGiftsViewHolder, position: Int) {
        val itemData = data[position]
        holder.itemData = itemData

        holder.giftIconImageView.setImageResource(itemData.imageId)
        holder.giftNameTextView.text = itemData.name
        holder.giftCostTextView.text = CostFormatter.formatCost(itemData.cost * itemData.count)
    }
}