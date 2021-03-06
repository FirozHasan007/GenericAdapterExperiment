package com.example.genericadapterexperiment.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genericadapterexperiment.views.adapter.GenericAdapter
import com.example.genericadapterexperiment.viewmodel.ListActivityVM
import com.example.genericadapterexperiment.R
import com.example.genericadapterexperiment.views.adapter.viewholder.ViewHolderFactory
import com.example.genericadapterexperiment.models.BusItemModel
import com.example.genericadapterexperiment.models.Demo
import com.example.genericadapterexperiment.models.ItemModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listVM: ListActivityVM
    private var myAdapter: GenericAdapter<Any>? = null
    private var listColors: ArrayList<ItemModel> = ArrayList()
    private var listBuses: ArrayList<BusItemModel> = ArrayList()
    private var listUsers: List<Demo.UData> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listVM = ViewModelProviders.of(this).get(ListActivityVM::class.java)
        setContentView(R.layout.activity_main)
        observeViewModel(listVM)
        populateData()
        populateBusData()


        color.setOnClickListener {
            myAdapter?.setItems(listColors)
        }
        Buses?.setOnClickListener {
            myAdapter?.setItems(listBuses)
        }
        Users?.setOnClickListener {
            observeViewModel(listVM)
        }

        second?.setOnClickListener {
            startActivity(Intent(this@MainActivity, SecondActivity::class.java))
        }

        setupAdapter()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun populateData() {
        var item = ItemModel("Red")
        listColors.add(item)
        item = ItemModel("Green")
        listColors.add(item)
        item = ItemModel("Blue")
        listColors.add(item)
        item = ItemModel("Yellow")
        listColors.add(item)
        item = ItemModel("Maroon")
        listColors.add(item)
        item = ItemModel("Black")
        listColors.add(item)
    }

    private fun populateBusData() {
        var item = BusItemModel("Audi", "Red")
        listBuses.add(item)
        item = BusItemModel("Mercedes Benz", "Green")
        listBuses.add(item)
        item = BusItemModel("Hundai", "Blue")
        listBuses.add(item)
        item = BusItemModel("Ford", "Yellow")
        listBuses.add(item)
        item = BusItemModel("Suzuki", "Maroon")
        listBuses.add(item)
        item = BusItemModel("Volks Wagon", "Black")
        listBuses.add(item)
        item = BusItemModel("Mercedes A370", "Gray")
        listBuses.add(item)
        item = BusItemModel("Toyota", "Royal Blue")
        listBuses.add(item)
        item = BusItemModel("Ford 430D", "Yellow")
        listBuses.add(item)
        item = BusItemModel("Hino 345", "White")
        listBuses.add(item)
        item = BusItemModel("Royal Roach", "Black")
        listBuses.add(item)
    }
    private fun observeViewModel(vm: ListActivityVM) {
        vm.getUserListObservable().observe(this, Observer<Demo> {
            listUsers = it.uData as List<Demo.UData>
            Log.d("Demo", "${listUsers.size} and ${it?.uData[1].type}")
            myAdapter?.setItems(listUsers)
           // LoggingClass.debug("list: $listUsers // ${listUsers.size} and // $")
        })
    }

    private fun setupAdapter() {
        myAdapter = object : GenericAdapter<Any>(this) {
            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ViewHolderFactory.create(view, viewType, this@MainActivity)
            }


            override fun getLayoutId(position: Int, obj: Any): Int {
                return when (obj) {
                    is ItemModel -> R.layout.car_item_layout
                    is BusItemModel -> R.layout.bus_item_layout
                    else -> R.layout.user_item_layout
                }
            }
        }
        //  observeViewModel(listVM)
        /*    when (Random.nextInt(0, 3)) {
                1 -> myAdapter?.setItems(listColors)
                0 -> myAdapter?.setItems(listBuses)
                else -> observeViewModel(listVM)
            }*/


        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = myAdapter
    }
}
