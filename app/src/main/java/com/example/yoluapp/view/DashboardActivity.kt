package com.example.yoluapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnAttach
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.yoluapp.adapters.BrandAdapter
import com.example.yoluapp.adapters.SliderAdapter
import com.example.yoluapp.databinding.ActivityDashboardBinding
import com.example.yoluapp.model.SliderModel
import com.example.yoluapp.viewModel.DashboardVM

class DashboardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashboardBinding
    private val viewModel = DashboardVM()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initBrand()
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer { items->
            banners(items)
            binding.progressBarBanner.visibility = View.GONE
        })
        viewModel.loadBanners()
    }

    private fun banners(images :  List<SliderModel>){
        binding.viewPagerSlider.adapter = SliderAdapter(images, binding.viewPagerSlider)
        binding.viewPagerSlider.clipToPadding = false
        binding.viewPagerSlider.clipChildren = false
        binding.viewPagerSlider.offscreenPageLimit = 3
        binding.viewPagerSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(40))
        }

        binding.viewPagerSlider.setPageTransformer(compositePageTransformer)

        if (images.size>1){
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.setViewPager2(binding.viewPagerSlider)

        }


    }

    private fun initBrand(){
        binding.progressBarBrand.visibility = View.VISIBLE
        viewModel.brands.observe(this, Observer{
            binding.viewBrand.layoutManager = LinearLayoutManager(this@DashboardActivity, LinearLayoutManager.HORIZONTAL, false)
            binding.viewBrand.adapter = BrandAdapter(it)
            binding.progressBarBrand.visibility = View.GONE
        })
        viewModel.loadBrands()
    }
}