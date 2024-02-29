package com.esaubernal.cetes

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MesAdapter
    private val MesViewModel:MesViewModel by viewModels()
    lateinit var monto:EditText
    var progreso:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler=findViewById(R.id.rvMes)
        adapter=MesAdapter(MesViewModel.elementos)

        recycler.adapter=adapter
        recycler.layoutManager=LinearLayoutManager(this)

        monto=findViewById(R.id.nmMonto)
        val barra=findViewById<SeekBar>(R.id.sbMeses)

        monto.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cambioDeBarra()
            }

            override fun afterTextChanged(s: Editable?) {
                cambioDeBarra()
            }

        })

        barra.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                progreso=progress
                cambioDeBarra()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    fun cambioDeBarra(){
        val can=monto.text.toString()
        if(can.isNotEmpty()){
            MesViewModel.elementos.clear()
            val porcentaje:Float= (11.49/12).toFloat()
            var cantidad:Float=can.toInt().toFloat()
            var intCant:Float
            for (i in 0..progreso){
                if(i!=0){
                    intCant= (cantidad*(1+(porcentaje/100)))
                    MesViewModel.elementos.add(Mes(i,intCant,intCant-cantidad,porcentaje))
                    cantidad=intCant
                }
            }
            adapter.notifyDataSetChanged()
        }
    }
}