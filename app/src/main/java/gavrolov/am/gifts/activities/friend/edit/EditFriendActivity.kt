package gavrolov.am.gifts.activities.friend.edit

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import gavrolov.am.gifts.R
import gavrolov.am.gifts.USER_ID
import gavrolov.am.gifts.activities.friend.card.FriendCardActivity
import gavrolov.am.gifts.app.controllers.AppController
import gavrolov.am.gifts.app.model.UserDto
import gavrolov.am.gifts.databinding.ActivityEditFriendBinding
import gavrolov.am.gifts.utils.RecyclerItemClickListener

class EditFriendActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditFriendBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditFriendBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val user = load()

        bindData(user)

        if (user != null) {
            supportActionBar?.title = "Редактирование"
        } else {
            supportActionBar?.title = "Создание"
        }
    }

    private fun bindData(data: UserDto?) {
        binding.friendNameEditText.setText(data?.name)

        binding.saveFriendButton.setOnClickListener {
            onSave()
        }
        bindList(data)
    }

    private fun bindList(data: UserDto?) {
        val avatars = arrayListOf(
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_4,
            R.drawable.avatar_5,
            R.drawable.avatar_6,
            R.drawable.avatar_7,
            R.drawable.avatar_8,
            R.drawable.avatar_9,
            R.drawable.avatar_10,
            R.drawable.avatar_11,
            R.drawable.avatar_12,
            R.drawable.avatar_13,
            R.drawable.avatar_14,
            R.drawable.avatar_15,
            R.drawable.avatar_16,
        ).toMutableList()

        val recyclerView = binding.avatarsRecyclerView
        val adapter = FriendAvatarsAdapter(avatars, data?.imageId)
        if (recyclerView.adapter == null) {
            recyclerView.adapter = adapter
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

            recyclerView.addOnItemTouchListener(
                RecyclerItemClickListener(
                    this,
                    recyclerView,
                    object : RecyclerItemClickListener.OnItemClickListener {
                        override fun onItemClick(view: View?, position: Int) {
                            val resourceId = adapter.getAdapterData()[position]
                            adapter.selected = resourceId
                            adapter.notifyDataSetChanged()
                        }

                        override fun onLongItemClick(view: View?, position: Int) {
                        }
                    })
            )
        }
    }

    private fun onSave() {
        val isNew = getUserId() == null
        val id = save()
        if (isNew) {
            val intent = Intent(this, FriendCardActivity::class.java)
            intent.putExtra(USER_ID, id)
            finish()
            startActivity(intent)
        } else {
            onNavigateUp()
        }
    }

    private fun save(): String {
        val controller = AppController()
        val adapter = binding.avatarsRecyclerView.adapter as FriendAvatarsAdapter

        val id = getUserId()
        val name = binding.friendNameEditText.text.toString()
        val imageId = adapter.selected ?: R.drawable.avatar_1
        return if (id != null) {
            controller.renameUser(id, name, imageId)
            id
        } else {
            controller.createUser(name, imageId)
        }
    }

    private fun load(): UserDto? {
        val controller = AppController()
        val id = getUserId()
        return if (id != null) {
            controller.getUserCard(id).userDto
        } else {
            null
        }
    }

    private fun getUserId(): String? {
        return intent.getStringExtra(USER_ID)
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