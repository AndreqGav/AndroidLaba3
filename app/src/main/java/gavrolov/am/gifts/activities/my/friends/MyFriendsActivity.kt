package gavrolov.am.gifts.activities.my.friends

import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import gavrolov.am.gifts.USER_ID
import gavrolov.am.gifts.activities.friend.card.FriendCardActivity
import gavrolov.am.gifts.activities.friend.edit.EditFriendActivity
import gavrolov.am.gifts.app.controllers.AppController
import gavrolov.am.gifts.app.helpers.CostFormatter
import gavrolov.am.gifts.app.model.UserDto
import gavrolov.am.gifts.app.model.UserListDto
import gavrolov.am.gifts.databinding.ActivityMyFriendsBinding
import gavrolov.am.gifts.utils.DeletableAdapter
import gavrolov.am.gifts.utils.RecyclerItemClickListener
import gavrolov.am.gifts.utils.SwipeToDeleteCallback


class MyFriendsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMyFriendsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

    }

    override fun onResume() {
        super.onResume()

        reload()
    }

    private fun reload() {
        val userList = load()
        bindData(userList)
    }

    private fun bindData(data: UserListDto) {
        binding.total.text = CostFormatter.formatCost(data.totalCost)
        bindList(data.users.toMutableList())

        binding.fab.setOnClickListener {
            moveToCreateNew()
        }
    }

    private fun bindList(data: MutableList<UserDto>) {
        val recyclerView = binding.recyclerView

        if (recyclerView.adapter == null) {
            with(recyclerView) {
                adapter = MyFriendsAdapter(data)
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
                            val user = getListData(position)
                            moveToCard(user.id)
                        }

                        override fun onLongItemClick(view: View?, position: Int) {
                        }
                    })
            )
        } else {
            @Suppress("UNCHECKED_CAST")
            val adapter = recyclerView.adapter as DeletableAdapter<UserDto>
            adapter.setAdapterData(data)
        }
    }

    private fun moveToCard(userId: String) {
        val intent = Intent(this, FriendCardActivity::class.java)
        intent.putExtra(USER_ID, userId)
        startActivity(intent)
    }

    private fun moveToCreateNew() {
        val intent = Intent(this, EditFriendActivity::class.java)
        startActivity(intent)
    }

    private fun load(): UserListDto {
        val controller = AppController()
        return controller.getUserList()
    }

    private fun removeUser(userId: String) {
        val controller = AppController()
        controller.deleteUser(userId)
        reload()
    }

    private fun cancelRemoveUser(userId: String) {
        val controller = AppController()
        controller.cancelDeleteUser(userId)
        reload()
    }

    private fun enableSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {

                @Suppress("UNCHECKED_CAST")
                val adapter = binding.recyclerView.adapter as DeletableAdapter<UserDto>
                val position = viewHolder.bindingAdapterPosition
                val item = adapter.getAdapterData()[position]
                adapter.removeItem(position)
                removeUser(item.id)

                val snackBar = Snackbar
                    .make(
                        binding.coordinatorLayout,
                        "Друг был удален из вашего списка",
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
                    cancelRemoveUser(item.id)
                }
                snackBar.setActionTextColor(Color.YELLOW)
                snackBar.show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    private fun getListData(position: Int): UserDto {
        @Suppress("UNCHECKED_CAST")
        val adapter = binding.recyclerView.adapter as DeletableAdapter<UserDto>
        return adapter.getAdapterData()[position]
    }
}