package com.moonfoxy.binlist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moonfoxy.binlist.adapters.BinAdapter
import com.moonfoxy.binlist.databinding.ActivityMainBinding
import com.moonfoxy.binlist.models.BinInfo
import com.moonfoxy.binlist.models.BinInfoResponse
import com.moonfoxy.binlist.network.RetrofitClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var binAdapter: BinAdapter
    private val binList = mutableListOf<BinInfo>()
    private val gson = Gson()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        binList.addAll(loadFromSharedPreferences())
        binAdapter = BinAdapter(binList)
        mBinding.recycleView.layoutManager = LinearLayoutManager(this)
        mBinding.recycleView.adapter = binAdapter

        mBinding.searchButton.setOnClickListener {
            val binValue = mBinding.searchBar.text.toString()
            if (binValue.isNotEmpty()) {
                getDataList(binValue)
                mBinding.searchBar.text.clear()
            } else {
                Toast.makeText(this, getString(R.string.input_bin), Toast.LENGTH_SHORT).show()
            }
        }

        binAdapter.setOnItemClickListener(object : BinAdapter.OnItemClickListener {
            override fun onItemClick(bin: BinInfo) {
                val intent = Intent(this@MainActivity, CardInfoActivity::class.java)
                intent.putExtra("bin", bin)
                startActivity(intent)
            }
        })
    }

    override fun onStop() {
        super.onStop()
        saveToSharedPreferences(binList)
    }

    private fun saveToSharedPreferences(binList: MutableList<BinInfo>) {
        val gson = Gson()
        val json = gson.toJson(binList)
        val sharedPreferences = getSharedPreferences("com.moonfoxy.binlist", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("bin_list", json)
        editor.apply()
    }

    private fun loadFromSharedPreferences(): MutableList<BinInfo> {
        val gson = Gson()
        val sharedPreferences = getSharedPreferences("com.moonfoxy.binlist", MODE_PRIVATE)
        val json = sharedPreferences.getString("bin_list", null)
        return if (json == null) {
            mutableListOf()
        } else {
            val type = object : TypeToken<MutableList<BinInfo>>() {}.type
            gson.fromJson(json, type)
        }
    }

    private fun getDataList(bin: String) {
        RetrofitClient.retrofit.getDataList(bin).enqueue(object : Callback<BinInfoResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<BinInfoResponse>, response: Response<BinInfoResponse>
            ) {
                if (response.isSuccessful) {
                    val element = response.body()!!
                    val binElement = BinInfo(bin, element.copy())
                    binList.add(binElement)
                    binAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@MainActivity, getString(R.string.request_fail), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<BinInfoResponse>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity, getString(R.string.request_fail), Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}