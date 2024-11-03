package balloh.khabib.ahmad.app10

import android.app.Application
import com.google.firebase.FirebaseApp

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this)
    }
}