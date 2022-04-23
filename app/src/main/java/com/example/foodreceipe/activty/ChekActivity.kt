package com.example.foodreceipe.activty

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodreceipe.R
import com.example.foodreceipe.adapter.ProductAdapter
import com.example.foodreceipe.database.ChekRepository
import com.example.foodreceipe.model.Chek
import com.example.foodreceipe.model.Product
import com.google.zxing.WriterException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ChekActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var tv_name: TextView
    lateinit var hashMap1: HashMap<String, Int>
    lateinit var hashMap2: HashMap<String, Int>
    lateinit var iv_qr_code: ImageView
    lateinit var bitmap: Bitmap
    lateinit var tv_all: TextView
    var summa: Int = 0
    var prise = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chek)
        initViews()
    }

    private fun initViews() {
        tv_name = findViewById(R.id.tv_name)
        tv_name.text = "Xarid qilingan mahsulotlar!!"

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 1)

        iv_qr_code = findViewById(R.id.iv_qr_code)
        tv_all = findViewById(R.id.tv_all)

        hashMap1 = intent.getSerializableExtra("map1") as HashMap<String, Int>
        hashMap2 = intent.getSerializableExtra("map2") as HashMap<String, Int>

        refreshAdapter(allCkeks())
    }

    private fun refreshAdapter(items: ArrayList<Product>) {
        val adapter = ProductAdapter(this, items)
        recyclerView.adapter = adapter
    }

    private fun allCkeks(): ArrayList<Product> {
        val items = ArrayList<Product>()

        for ((k, v) in hashMap1){
            items.add(Product(k,  v.toString(), hashMap2.get(k).toString()))
            prise += "$k - ${hashMap2.get(k)} kg : $v so'm. "
            summa += v
        }

        prise += "Yetkazib berish xizmat - 50000 so'm, Umumiy xarid narxi = ${summa}"

        val manager = getSystemService(WINDOW_SERVICE) as WindowManager
        val display: Display = manager.defaultDisplay
        val point = Point()
        display.getSize(point)
        val width: Int = point.x
        val height: Int = point.y
        var dimen = if (width < height) width else height
        dimen = dimen * 3 / 4
        val qrgEncoder = QRGEncoder(prise, null, QRGContents.Type.TEXT, dimen)
        try {
            bitmap = qrgEncoder.encodeAsBitmap()
            iv_qr_code.setImageBitmap(bitmap)
        } catch (e: WriterException) {

            Log.e("Tag", e.toString())
        }

        val cal = Calendar.getInstance()
        val mYear: Int = cal.get(Calendar.YEAR)
        val mMonth: Int = cal.get(Calendar.MONTH)
        val mDay: Int = cal.get(Calendar.DAY_OF_MONTH)
        val minute = cal[Calendar.MINUTE]
        val hourofday = cal[Calendar.HOUR_OF_DAY]

        val repository = ChekRepository(Application())
        val chek = Chek(
            time = "$mDay-$mMonth-$mYear  ${hourofday}:${minute}",
            total = summa.toString(),
            image = bitmap
        )
        repository.saveChek(chek)
        tv_all.text = summa.toString()

        return items
    }
}