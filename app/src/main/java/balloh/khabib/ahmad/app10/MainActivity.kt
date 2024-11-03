package balloh.khabib.ahmad.app10

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import balloh.khabib.ahmad.app10.databinding.ActivityMainBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var tvContent: TextView
    private lateinit var analytics: FirebaseAnalytics
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private lateinit var layout: LinearLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        analytics = FirebaseAnalytics.getInstance(this)
        tvContent = findViewById(R.id.tv_content)
        layout = findViewById(R.id.main_layout)

        remoteConfig = FirebaseRemoteConfig.getInstance()

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 5
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var content = remoteConfig.getString("content")
                    tvContent.text = content

                    val backgroundColor = remoteConfig.getString("background_color")
                    layout.setBackgroundColor(Color.parseColor(backgroundColor))
                }
            }
        binding.btnLanjut.setOnClickListener {
            startActivity(Intent(this@MainActivity, Login::class.java))
        }

    }
}