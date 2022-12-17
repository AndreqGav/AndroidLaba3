package gavrolov.am.gifts.activities.my.friends

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import gavrolov.am.gifts.R
import gavrolov.am.gifts.app.helpers.CostFormatter.formatCost
import gavrolov.am.gifts.app.model.UserDto
import gavrolov.am.gifts.utils.SimpleRecycleAdapter

class MyFriendsAdapter(data: MutableList<UserDto>) :
    SimpleRecycleAdapter<MyFriendsAdapter.MyFriendsViewHolder, UserDto>(data) {

    inner class MyFriendsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView = view.findViewById(R.id.user_name_text_view)
        val userCostTextView: TextView = view.findViewById(R.id.user_cost_text_view)
        val usesIconImageView: ImageView = view.findViewById(R.id.user_icon_image_view)

        lateinit var itemData: UserDto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFriendsViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)

        return MyFriendsViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: MyFriendsViewHolder, position: Int) {
        val itemData = data[position]
        holder.itemData = itemData

        holder.usesIconImageView.setImageResource(itemData.imageId)
        holder.userNameTextView.text = itemData.name
        holder.userCostTextView.text = formatCost(itemData.cost)
    }
}