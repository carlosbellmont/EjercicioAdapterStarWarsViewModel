package com.cbellmont.ejercicioadapterstarwars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cbellmont.ejercicioadapterstarwars.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private var adapter : FilmsAdapter = FilmsAdapter()
    private lateinit var model :MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        createRecyclerView()
        downloadAll()

        binding.bAntiguas.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            downloadOldFilm()
        }

        binding.bNuevas.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            downloadNewFilm()
        }

        binding.bTodas.setOnClickListener {
            binding.pbLoading.visibility = View.VISIBLE
            downloadAll()
        }
    }

    private fun createRecyclerView() {
        binding.filmRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.filmRecyclerView.adapter = adapter
    }

    private fun downloadOldFilm(){
        lifecycleScope.launch {
            val list = loadFilmOldInBackground()
            setAdapterOnMainThread(list)
        }
    }
    private suspend fun loadFilmOldInBackground() : MutableList<Film>{
        // El withContext(Dispatchers.IO) no es estrictamente necesario. Lo ponemos solo por seguridad.
        return withContext(Dispatchers.IO) {
            return@withContext model.getOldFilms()
        }
    }

    private fun downloadNewFilm(){
        lifecycleScope.launch {
            val list = loadFilmNewInBackground()
            setAdapterOnMainThread(list)
        }
    }
    private suspend fun loadFilmNewInBackground() : MutableList<Film>{
        // El withContext(Dispatchers.IO) no es estrictamente necesario. Lo ponemos solo por seguridad.
        return withContext(Dispatchers.IO) {
            return@withContext model.getNewFilms()
        }
    }

    private fun downloadAll(){
        lifecycleScope.launch {
            val list = loadFilmAllInBackground()
            setAdapterOnMainThread(list)
        }
    }

    private suspend fun loadFilmAllInBackground() : MutableList<Film>{
        // El withContext(Dispatchers.IO) no es estrictamente necesario. Lo ponemos solo por seguridad.
        return withContext(Dispatchers.IO) {
            return@withContext model.getFilms()
        }
    }

    private suspend fun setAdapterOnMainThread(filmsList: MutableList<Film>) {
        withContext(Dispatchers.Main) {
            adapter.updateFilms(filmsList)
            pbLoading.visibility = View.GONE
        }
    }
}