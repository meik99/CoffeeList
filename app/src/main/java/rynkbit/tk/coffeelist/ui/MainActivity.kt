package rynkbit.tk.coffeelist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.AppDatabase

class MainActivity : AppCompatActivity() {
    private val appModule = module {
        single {
            Room.databaseBuilder(
                applicationContext, AppDatabase::class.java, "coffeelist")
                    .addCallback(object: RoomDatabase.Callback(){
                    })
                    .fallbackToDestructiveMigration()
                    .build()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()


        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    override fun onPause() {
        super.onPause()

        stopKoin()
    }

    override fun onStop() {
        super.onStop()

    }
}
