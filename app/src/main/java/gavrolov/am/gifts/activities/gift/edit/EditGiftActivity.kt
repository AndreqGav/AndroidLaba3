package gavrolov.am.gifts.activities.gift.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import gavrolov.am.gifts.GIFT_ID
import gavrolov.am.gifts.USER_ID
import gavrolov.am.gifts.app.controllers.AppController
import gavrolov.am.gifts.app.helpers.CostFormatter
import gavrolov.am.gifts.app.helpers.IntParser
import gavrolov.am.gifts.app.model.GiftDto
import gavrolov.am.gifts.databinding.ActivityEditGiftBinding

class EditGiftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditGiftBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditGiftBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val gift = load()

        bindData(gift)

        if (gift != null) {
            supportActionBar?.title = "Редактирование подарка"
        } else {
            supportActionBar?.title = "Создание подарка"
        }
    }

    private fun bindData(data: GiftDto?) {
        binding.giftNameEditText.setText(data?.name)
        binding.giftDescription.setText(data?.description)
        binding.giftCost.setText(data?.cost?.toString() ?: "")
        binding.giftCount.setText(data?.count?.toString() ?: "1")
        recalculateTotal()

        binding.minus.setOnClickListener {
            var count = binding.giftCount.text.toString().toInt()
            count--

            if (count < 1) {
                count = 1
            }
            binding.giftCount.setText(count.toString())
            recalculateTotal()
        }

        binding.plus.setOnClickListener {
            var count = binding.giftCount.text.toString().toInt()
            count++
            binding.giftCount.setText(count.toString())
            recalculateTotal()
        }

        binding.giftCost.addTextChangedListener {
            recalculateTotal()
        }

        binding.saveGiftButton.setOnClickListener {
            onSave()
        }
    }

    private fun onSave() {
        save()
        onNavigateUp()
    }

    private fun save(): String {
        val controller = AppController()
        val id = getGiftId()
        val userId = getUserId()!!
        val name = binding.giftNameEditText.text.toString()
        val description = binding.giftDescription.text.toString()
        val cost = IntParser.parse(binding.giftCost.text.toString())
        val count = IntParser.parse(binding.giftCount.text.toString(), 1)

        return if (id != null) {
            controller.updateGift(id, name, cost, description, count)
            id
        } else {
            controller.createGift(userId, name, cost, description, count)
        }
    }

    private fun load(): GiftDto? {
        val controller = AppController()
        val userId = getUserId()!!
        val id = getGiftId()
        return controller.getUserCard(userId).gifts.find { it.id == id }
    }

    private fun getUserId(): String? {
        return intent.getStringExtra(USER_ID)
    }

    private fun getGiftId(): String? {
        return intent.getStringExtra(GIFT_ID)
    }

    private fun recalculateTotal() {
        val count = IntParser.parse(binding.giftCount.text.toString(), 1)
        val cost = IntParser.parse(binding.giftCost.text.toString())

        val total = count * cost

        binding.totalGiftTextView.text = CostFormatter.formatCost(total)
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