package com.kotlin.mobile_laptop.ui.home

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.ui.detail.DetailActivity
import com.kotlin.mobile_laptop.model.ItemMenu
import com.kotlin.mobile_laptop.model.ItemMenuResponse
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.model.ProductResponse
import com.kotlin.mobile_laptop.retrofit.ApiApp
import com.kotlin.mobile_laptop.retrofit.Cilent
import com.kotlin.mobile_laptop.ui.home.adapter.HomeAdapter
import com.kotlin.mobile_laptop.ui.home.adapter.ProductAdapter
import com.kotlin.mobile_laptop.ui.type_product.TypeProductActivity
import com.kotlin.mobile_laptop.utils.Utils.Companion.BaseUrl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var apiBanHang = Cilent.getInstance(BaseUrl)?.create(ApiApp::class.java)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        // setup action bar
        actionBar()
        // setup quảng cáo
        actionViewFlipper()

        // setup recycle view
        setupRecyclerViewMenu()
        setUpRecyclerViewHome()


        // kiểm tra có mạng thì gọi data server về
        if (isNetworkConnected()) {
            getItemMenu()
            getProduct()
        }else {
            Toast.makeText(this, "Mất mạng", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getItemMenu() {
        apiBanHang?.getItemMenu()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ItemMenuResponse> {
                override fun onSubscribe(d: Disposable?) {


                }

                override fun onSuccess(response: ItemMenuResponse?) {
                    if (response?.success == true) {
                        response.result?.let {
                            listItemMenu.addAll(it)
                            adapterHome?.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(this@HomeActivity, response?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onError(response: Throwable?) {
                    Toast.makeText(
                        this@HomeActivity,
                        response?.message ?: "Lỗi",
                        Toast.LENGTH_SHORT
                    ).show()

                }

            })

    }

    private fun getProduct() {
        apiBanHang?.getProduct()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ProductResponse> {
                override fun onSuccess(productResponse: ProductResponse) {
                    if (productResponse.success == true) {
                        productResponse.result?.let {
                            listProduct.addAll(it)
                            adapterProduct?.notifyDataSetChanged()
                        }
                    } else {
                        Toast.makeText(
                            this@HomeActivity,
                            productResponse?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onSubscribe(d: Disposable?) {
                }

                override fun onError(e: Throwable?) {
                    Toast.makeText(this@HomeActivity, e?.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setupRecyclerViewMenu() {
        adapterHome = HomeAdapter(this, listItemMenu,:: onItemMenuClick)
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