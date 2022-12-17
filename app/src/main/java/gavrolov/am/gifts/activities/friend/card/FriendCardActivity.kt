package gavrolov.am.gifts.activities.friend.card

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import gavrolov.am.gifts.GIFT_ID
import gavrolov.am.gifts.R
import gavrolov.am.gifts.USER_ID
import gavrolov.am.gifts.activities.friend.edit.EditFriendActivity
import gavrolov.am.gifts.activities.gift.edit.EditGiftActivity
import gavrolov.am.gifts.app.controllers.AppController
import gavrolov.am.gifts.app.helpers.CostFormatter
import gavrolov.am.gifts.app.model.GiftDto
import gavrolov.am.gifts.app.model.UserCardDto
import gavrolov.am.gifts.app.model.UserDto
import gavrolov.am.gifts.databinding.ActivityFriendCardBinding
import gavrolov.am.gifts.utils.DeletableAdapter
import gavrolov.am.gifts.utils.RecyclerItemClickListener
import gavrolov.am.gifts.utils.SwipeToDeleteCallback


class FriendCardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendCardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendCardBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        reload()
    }

    private fun reload() {
        val card = load()
        bindData(card)
    }

    private fun bindData(data: UserCardDto) {
        binding.userIconImageView.setImageResource(data.userDto.imageId)
        binding.userNameTextView.text = data.userDto.name
        binding.totalUserTextView.text = CostFormatter.formatCost(data.totalCost)

        bindList(data.gifts.toMutableList())

        binding.editUser.setOnClickListener {
            val intent = Intent(this, EditFriendActivity::class.java)
            intent.putExtra(USER_ID, data.userDto.id)
            startActivity(intent)
        }

        binding.addGift.setOnClickListener {
            moveToGiftEdit()
        }
    }

    private fun bindList(data: MutableList<GiftDto>) {
        val recyclerView = binding.recyclerView
        if (recyclerView.adapter == null) {

            with(recyclerView) {
                adapter = FriendCardGiftsAdapter(data)
            }
            recyclerView.setHasFixedSize(true)
            recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect, view: View,
                    parent: RecyclerView, state: RecyclerView.State
                ) {
                    val spaceSize = 10
                    with(outRect) {
                        top = spaceSize
                        bottom = spaceSize

                        right = spaceSize
                        left = spaceSize
                    }
                }
            })
            enableSwipeToDeleteAndUndo()

            recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                    this,
                    recyclerView,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val gift = getListData(position)
                            moveToGiftEdit(gift.id)
                        }

                        override fun onLongItemClick(view: View?, position: Int) {
                        }
                    })
            )
        } else {
            @Suppress("UNCHECKED_CAST")
            val adapter = recyclerView.adapter as DeletableAdapter<GiftDto>
            adapter.setAdapterData(data)
        }
    }

    private fun moveToGiftEdit(giftId: String? = null) {
        val intent = Intent(this, EditGiftActivity::class.java)
        intent.putExtra(USER_ID, getUserId())
        intent.putExtra(GIFT_ID, giftId)
        startActivity(intent)
    }

    private fun removeGift(giftId: String) {
        val controller = AppController()
        controller.deleteGift(giftId)
        reload()
    }

    private fun cancelRemoveGift(giftId: String) {
        val controller = AppController()
        controller.cancelDeleteGift(giftId)
        reload()
    }

    private fun getUserId(): String? {
        return intent.getStringExtra(USER_ID)
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                @Suppress("UNCHECKED_CAST")
                val adapter = binding.recyclerView.adapter as DeletableAdapter<GiftDto>
                val position = viewHolder.bindingAdapterPosition
                val item = adapter.getAdapterData()[position]
                adapter.removeItem(position)
                removeGift(item.id)

                val snackBar = Snackbar
                    .make(
                        binding.coordinatorLayout,
                        "Падорок был удален",
                        Snackbar.LENGTH_LONG
                    )
                    .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                        }
                    })
                snackBar.setAction("Отменить") {
                    adapter.restoreItem(item, position)
                    binding.recyclerView.scrollToPosition(position)
                    cancelRemoveGift(item.id)
                }
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun load(): UserCardDto {
        val controller = AppController()
        val id = getUserId()!!
        return controller.getUserCard(id)
    }

    private fun getListData(position: Int): GiftDto {
        @Suppress("UNCHECKED_CAST")
        val adapter = binding.recyclerView.adapter as DeletableAdapter<GiftDto>
        return adapter.getAdapterData()[position]
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onNavigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}