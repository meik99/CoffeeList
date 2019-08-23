package rynkbit.tk.coffeelist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import rynkbit.tk.coffeelist.R
import rynkbit.tk.coffeelist.db.AppDatabase
import rynkbit.tk.coffeelist.db.facade.CustomerFacade
import rynkbit.tk.coffeelist.db.facade.ItemFacade
import rynkbit.tk.coffeelist.ui.customer.CustomerAdapter
import rynkbit.tk.coffeelist.ui.entity.UICustomer
import rynkbit.tk.coffeelist.ui.entity.UIItem

class MainActivity : AppCompatActivity() {
    private lateinit var mCustomerAdapter: CustomerAdapter

    private val appModule = module {
        single {
            Room.databaseBuilder(
                applicationContext, AppDatabase::class.java, "coffeelist")
                    .addCallback(object: RoomDatabase.Callback(){
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            for(i in 0..50){
                                CustomerFacade()
                                        .insert(UICustomer(0, "Test Customer $i"))
                                        .subscribeOn(Schedulers.newThread())
                                        .subscribe()
                                ItemFacade()
                                        .insert(UIItem(
                                                0,
                                                "Test Item $i",
                                                i * 0.75,
                                                i
                                        ))
                                        .subscribeOn(Schedulers.newThread())
                                        .subscribe()
                            }

                        }
                    })
                    .build()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()

        startKoin {
            androidLogger()
            androidContext(this@MainActivity)
            modules(appModule)
        }
    }

    override fun onStop() {
        super.onStop()

        stopKoin()
    }
}
