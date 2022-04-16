package com.kotlin.mobile_laptop.ui.home

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.data.local.AppPreferences
import com.kotlin.mobile_laptop.ui.detail.DetailActivity
import com.kotlin.mobile_laptop.model.ItemMenu
import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.model.ProductResponse
import com.kotlin.mobile_laptop.data.remote.retrofit.ApiApp
import com.kotlin.mobile_laptop.data.remote.retrofit.Cilent
import com.kotlin.mobile_laptop.ui.home.adapter.HomeAdapter
import com.kotlin.mobile_laptop.ui.home.adapter.ProductAdapter
import com.kotlin.mobile_laptop.ui.login.LoginActivity
import com.kotlin.mobile_laptop.ui.login.LoginViewModel
import com.kotlin.mobile_laptop.ui.type_product.TypeProductActivity
import com.kotlin.mobile_laptop.utils.Utils.Companion.BaseUrl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }

    var listProduct = ArrayList<Product>()
    var listItemMenu = ArrayList<ItemMenu>()
    var adapterHome: HomeAdapter? = null
    var adapterProduct: ProductAdapter? = null

    companion object {
        const val TRANG_CHU = 0
        const val DIEN_THOAI = 1
        const val LAP_TOP = 2
        const val LIEN_HE = 3
        const val THONG_TIN = 4
    }

    override val layoutId: Int = R.layout.activity_home

    override fun setupView() {
        setupUserLogin()
        setupRecyclerViewMenu()
        setUpRecyclerViewHome()
        actionViewFlipper()
        actionBar()
    }

    override fun setupObserver() {
        viewModel.itemMenuResponseLiveData.observe(this, { response ->
            if (response?.success == true) {
                response.result?.let {
                    listItemMenu.addAll(it)
                    adapterHome?.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(this@HomeActivity, response?.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })

        viewModel.productResponseLiveData.observe(this, { productResponse ->
            if (productResponse.success == true) {
                productResponse.result?.let {
                    listProduct.addAll(it)
                    adapterProduct?.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(
                    this@HomeActivity,
                    productResponse.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })


        viewModel.errorLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun setupEvent() {
        setupUserLogin()
        if (isNetworkConnected()) {
            viewModel.getItemMenu()
            viewModel.getListProduct()
        } else {
            Toast.makeText(this, "Vui lòng kiểm tra kết nối internet", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRecyclerViewMenu() {
        adapterHome = HomeAdapter(this, listItemMenu, ::onItemMenuClick)
        rcv_navigation.layoutManager = LinearLayoutManager(this)
        rcv_navigation.adapter = adapterHome

    }

    private fun setUpRecyclerViewHome() {
        adapterProduct = ProductAdapter(this, listProduct, ::onItemclick)
        rcv_product.layoutManager = GridLayoutManager(this, 2)
        rcv_product.adapter = adapterProduct

    }


    private fun actionViewFlipper() {
        val mangquangcao = ArrayList<String>()
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-Le-hoi-phu-kien-800-300.png")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-HC-Tra-Gop-800-300.png")
        mangquangcao.add("http://mauweb.monamedia.net/thegioididong/wp-content/uploads/2017/12/banner-big-ky-nguyen-800-300.jpg")
        for (i in 0 until mangquangcao.size) {
            var imageView = ImageView(this)
            Glide.with(applicationContext).load(mangquangcao[i]).into(imageView)
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            viewflipper.addView(imageView)
        }
        viewflipper.flipInterval = 5000
        viewflipper.isAutoStart = true
        val slide_in = AnimationUtils.loadAnimation(this, R.anim.slide_in_right)
        val slide_out = AnimationUtils.loadAnimation(this, R.anim.slide_out_right)
        viewflipper.inAnimation = slide_in
        viewflipper.outAnimation = slide_out

    }

    private fun actionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        img_menu.setOnClickListener {
            drawelayout.openDrawer(GravityCompat.START)
        }
        tv_login.setOnClickListener {
            var intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupUserLogin() {
        AppPreferences().getUserInfo().username?.let { userName ->
            tv_login.text = userName
        }
    }

    private fun isNetworkConnected(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    private fun onItemclick(product: Product) {
        Toast.makeText(this, product.name, Toast.LENGTH_SHORT).show()
        var intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("mobilePhone", product)
        startActivity(intent)

    }

    private fun onItemMenuClick(itemMenu: ItemMenu) {
        Toast.makeText(this, itemMenu.name, Toast.LENGTH_SHORT).show()
        when (itemMenu.id) {
            DIEN_THOAI -> {
                val intent = TypeProductActivity.getIntent(this, 1)
                startActivity(intent)
            }
            LAP_TOP -> {
                val intent = TypeProductActivity.getIntent(this, 2)
                startActivity(intent)
            }
            TRANG_CHU -> {
                drawelayout.close()
            }

        }
    }

}