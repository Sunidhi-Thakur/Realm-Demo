package com.sunidhi.demorealm

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sunidhi.demorealm.databinding.ActivityMainBinding
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.data_input.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var realm: Realm? = null
    private val dataModel = DataModel()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        realm = Realm.getDefaultInstance()

        btn_insertdata.setOnClickListener(this)
        btn_readdata.setOnClickListener(this)
   }

    override fun onClick(v: View?) {

        when (v?.id) {

            R.id.btn_insertdata -> {
                addData()
            }

            R.id.btn_readdata -> {
                readData()
            }
        }
    }

    private fun addData() {

        try {

            dataModel.id = edt_id.text.toString().toInt()
            dataModel.name = edt_name.text.toString()
            realm!!.executeTransactionAsync { realm -> realm.copyToRealm(dataModel) }

            clearFields()

            Log.d("Status","Data Inserted !!!")

        }catch (e:Exception){
            Log.d("Status",e.message.toString())
        }
    }

    private fun readData() {

        try {

            val dataModels: List<DataModel> =
                realm!!.where(DataModel::class.java).findAll()

            val map = mutableMapOf<Int, String?>()

            for (i in dataModels.indices) {
                map[dataModels[i].id] = dataModels[i].name
            }

            binding.showData.text = map.toString()

            Log.d("Status","Data Fetched !!!")

        } catch (e: Exception) {
            Log.d("Status",e.message.toString())
        }
    }

    private fun clearFields(){

        edt_id.setText("")
        edt_name.setText("")
    }
}