package gavrolov.am.gifts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import gavrolov.am.gifts.activities.my.friends.MyFriendsActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, MyFriendsActivity::class.java)
        finish()
        startActivity(intent)
    }
}