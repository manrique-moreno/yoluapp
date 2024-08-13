package com.example.yoluapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yoluapp.model.BrandModel
import com.example.yoluapp.model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DashboardVM: ViewModel() {

    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private val _banner= MutableLiveData<List<SliderModel>>()
    val banners : LiveData<List<SliderModel>> = _banner

    private val _brand =    MutableLiveData<MutableList<BrandModel>>()
    val brands : LiveData<MutableList<BrandModel>> = _brand

    fun loadBanners(){
        val ref = firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for (child in snapshot.children){
                    val list = child.getValue(SliderModel::class.java)
                    if (list != null){
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun loadBrands(){
        val ref = firebaseDatabase.getReference("Category")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<BrandModel>()
                for (child in snapshot.children){
                    val list = child.getValue(BrandModel::class.java)
                    if (list!=null){
                        lists.add(list)
                    }
                }
                _brand.value = lists
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}