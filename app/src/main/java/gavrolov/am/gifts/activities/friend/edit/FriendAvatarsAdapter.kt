package gavrolov.am.gifts.activities.friend.edit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import gavrolov.am.gifts.R
import gavrolov.am.gifts.utils.SimpleRecycleAdapter
import kotlin.properties.Delegates

class FriendAvatarsAdapter(data: MutableList<Int>, var selected: Int?) :
    SimpleRecycleAdapter<FriendAvatarsAdapter.FriendAvatarViewHolder, Int>(data) {

    inner class FriendAvatarViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val avatarImageView: ImageView = view.findViewById(R.id.avatar_image_view)
        val selectedAvatarImageView: ImageView = view.findViewById(R.id.selected_avatar)

        var itemData by Delegates.notNull<Int>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendAvatarViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_avatar_item, parent, false)

        return FriendAvatarViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: FriendAvatarViewHolder, position: Int) {
        val itemData = data[position]
        holder.itemData = itemData

        holder.avatarImageView.setImageResource(itemData)

        val visibility = if (itemData == selected) View.VISIBLE else View.INVISIBLE
        holder.selectedAvatarImageView.visibility = visibility
    }
}