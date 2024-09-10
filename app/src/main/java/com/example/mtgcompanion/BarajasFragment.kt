package com.example.mtgcompanion

import JSON.MTGJSON
import JSON.TitulosDeBaraja
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.IOException


class BarajasFragment : Fragment() {

    private lateinit var barajasAdapter: BarajasAdapter
    private lateinit var barajas: List<TitulosDeBaraja>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_barajas, container, false)

        // Leer el archivo JSON desde assets
        val json = try {
            context?.assets?.open("mtgjson.json")?.bufferedReader().use { it?.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        if (json != null) {
            // Parsear el JSON a un objeto MTGJSON
            val gson = Gson()
            try {
                val mtgjson = gson.fromJson(json, MTGJSON::class.java)
                barajas = mtgjson.Titulos_de_Barajas // Lista de TitulosDeBaraja

                // Configurar el adaptador
                barajasAdapter = BarajasAdapter(barajas) { baraja ->
                    val fragment = PasosFragment.newInstance(baraja)
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, fragment)
                        .addToBackStack(null)
                        .commit()
                }

                view.findViewById<RecyclerView>(R.id.barajasRecyclerView).apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = barajasAdapter
                }
            } catch (e: JsonSyntaxException) {
                e.printStackTrace()
                Toast.makeText(context, "Error en el formato del archivo JSON", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Manejar el caso en que no se pudo leer el archivo JSON
            Toast.makeText(context, "Error al cargar el archivo JSON", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}