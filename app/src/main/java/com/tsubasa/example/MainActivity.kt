package com.tsubasa.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.tsubasa.listpopwindow.ListPopWindow

class MainActivity : AppCompatActivity() {

    var styleIes: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_style1).setOnClickListener { styleIes = null }
        findViewById<View>(R.id.btn_style2).setOnClickListener { styleIes = R.style.style2 }

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = object : RecyclerView.Adapter<ItemHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ItemHolder {
                val view = LayoutInflater.from(this@MainActivity).inflate(R.layout.item_pop, parent, false)
                return ItemHolder(view)
            }

            override fun onBindViewHolder(holder: ItemHolder, position: Int) {
                val onClick = View.OnClickListener {
                    ListPopWindow.Builder(it)
                            .addMenuItem(R.drawable.ic_ignore, "item1") { Toast.makeText(this@MainActivity, "ttt", Toast.LENGTH_SHORT).show() }
                            .addMenuItem(R.drawable.ic_error_1, "item2") { Toast.makeText(this@MainActivity, "ttt", Toast.LENGTH_SHORT).show() }
                            .addMenuItem(R.drawable.ic_waring, "item3") { Toast.makeText(this@MainActivity, "ttt", Toast.LENGTH_SHORT).show() }
                            .apply {
                                styleIes?.let { withStyle(it) }
                            }
                            .build()
                            .show()
                }
                holder.btn1.setOnClickListener(onClick)
                holder.btn2.setOnClickListener(onClick)
                holder.btn3.setOnClickListener(onClick)
            }

            override fun getItemCount(): Int = 100

        }

        recyclerView.adapter = adapter
    }
}

class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val btn1: View = itemView.findViewById(R.id.button)
    val btn2: View = itemView.findViewById(R.id.button2)
    val btn3: View = itemView.findViewById(R.id.button3)
}
