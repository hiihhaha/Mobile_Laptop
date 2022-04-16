package com.kotlin.mobile_laptop.ui.type_product

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlin.mobile_laptop.R
import com.kotlin.mobile_laptop.base.BaseActivity
import com.kotlin.mobile_laptop.ui.detail.DetailActivity
import com.kotlin.mobile_laptop.model.Product
import com.kotlin.mobile_laptop.model.ProductResponse
import com.kotlin.mobile_laptop.data.remote.retrofit.ApiApp
import com.kotlin.mobile_laptop.data.remote.retrofit.Cilent
import com.kotlin.mobile_laptop.ui.home.HomeViewModel
import com.kotlin.mobile_laptop.utils.Utils
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_type_product.*

class TypeProductActivity : BaseActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this)[TyprProductViewModel::class.java]
    }

    companion object {
        fun getIntent(context: Context, type: Int = 1): Intent {
            return Intent(context, TypeProductActivity::class.java).apply {
                putExtra("type", type)
            }
        }
    }

    var adapterTypeProduct: TypesProductAdapter? = null
    var listTypeProduct = ArrayList<Product>()
    var type = 1
    override val layoutId: Int = R.layout.activity_type_product

    override fun setupView() {
        type = intent.getIntExtra("type", 1)
        setupRecyclerViewTypeProduct()
        viewModel.getDetail(type)

    }

    override fun setupObserver() {
        viewModel.typeProductLiveData.observe(this, { productResponse ->
            if (productResponse.success == true) {
                productResponse.result?.toMutableList()?.let {
                    listTypeProduct.addAll(it)
                    adapterTypeProduct?.notifyDataSetChanged()
                }
            } else {
                Toast.makeText(this@TypeProductActivity, productResponse.message, Toast.LENGTH_SHORT)
                    .show()

            }

        })
        viewModel.errorLiveData.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun setupEvent() {
        toolbar.setOnClickListener {
            onBackPressed()
        }
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

}

