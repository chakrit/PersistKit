package co.omise.persister

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    val storage: Storage by lazy {
        Storage(
            Room.databaseBuilder(applicationContext, KVDatabase::class.java, "mydb")
                .build()
        )
    }

    val adapter = TodoListAdapter()
    val background = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val handler = Handler()
        background.execute {
            val items: List<TodoItem> = storage.loadAll()
            handler.post {
                adapter.setItems(items)
            }
        }

        main_recycler_view.adapter = adapter
        main_recycler_view.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.main_menu_new -> handleNewItemTapped()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleNewItemTapped(): Boolean {
        val randomId = UUID.randomUUID().toString()
        val randomDescription = UUID.randomUUID().toString()

        val item = TodoItem(randomId, randomDescription, false)
        val handler = Handler()
        background.execute {
            storage.save(item)
            val newList: List<TodoItem> = storage.loadAll()
            handler.post {
                adapter.setItems(newList)
            }
        }

        return true
    }
}
