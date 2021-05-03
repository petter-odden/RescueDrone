package View

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rescuedrone.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val bottomNav : BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNav.menu.findItem(R.id.home).isChecked = true;

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {

                R.id.missions -> {
                    val intent = Intent(this, MissionActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.home -> {

                }

                R.id.dronehub -> {
                    val intent = Intent(this, MainActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }

                R.id.profile -> {
                    val intent = Intent(this, UserProfileActivity::class.java).apply {
                        //putExtra(EXTRA_MESSAGE, message)
                    }
                    startActivity(intent)
                    overridePendingTransition(0, 0);

                }
            }
            true
        }
    }
}