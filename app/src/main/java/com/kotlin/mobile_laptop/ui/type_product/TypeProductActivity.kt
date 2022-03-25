package com.kotlin.mobile_laptop.ui.type_product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.ui.detail.DetailActivity
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.model.ProductResponse
import com.kotlin.mobile_laptop.retrofit.ApiApp
import com.kotlin.mobile_laptop.retrofit.Cilent
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_type_product.*

class TypeProductActivity : AppCompatActivity() {
    companion object {
        fun getIntent(context: Context, type: Int = 1): Intent {
            return Intent(context, TypeProductActivity::class.java).apply {
                putExtra("type", type)
            }
        }
    }

    var apiTypeProduct = Cilent.getInstance(Utils.BaseUrl)?.create(ApiApp::class.java)
    var adapterTypeProduct: TypesProductAdapter? = null
    var listTypeProduct = ArrayList<Product>()
    var type = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_type_product)
        toolbar.setOnClickListener { onBackPressed() }
        type = intent.getIntExtra("type", 1)
        setupRecyclerViewTypeProduct()
        getProduct()

    }

    private fun setupRecyclerViewTypeProduct() {
        adapterTypeProduct = TypesProductAdapter(this, listTypeProduct, ::onItemClick)
        rcv_SanPham.layoutManager = LinearLayoutManager(this)
        rcv_SanPham.adapter = adapterTypeProduct

    }

    private fun onItemClick(phone: Product) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("mobilePhone", phone)
        startActivity(intent)
    }

    private fun getProduct() {
        apiTypeProduct?.getDetail(type)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(object : SingleObserver<ProductResponse> {
                override fun onSuccess(productResponse: ProductResponse) {
                    productResponse.result?.toMutableList()?.let {
                        listTypeProduct.addAll(it)
                        adapterTypeProduct?.notifyDataSetChanged()
                    }
                }

                override fun onError(e: Throwable) {
                    println(e.message)
                    Toast.makeText(this@TypeProductActivity, e.message, Toast.LENGTH_SHORT)
                        .show()

                }

                override fun onSubscribe(d: Disposable) {}
            })
    }
}