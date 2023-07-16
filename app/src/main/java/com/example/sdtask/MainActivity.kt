package com.example.sdtask

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.sdtask.adapter.PhotoAdapter
import com.example.sdtask.databinding.ActivityMainBinding
import com.example.sdtask.model.Photo
import com.example.sdtask.presenter.MainActivityPresenter
import com.example.sdtask.presenter.PhotoActivity
import com.example.sdtask.utils.CleanupWorker
import com.example.sdtask.view.MainActivityView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import java.util.concurrent.TimeUnit

class MainActivity : MvpAppCompatActivity(), MainActivityView {

    @InjectPresenter
    lateinit var presenter: MainActivityPresenter

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cleanUp()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PhotoAdapter(emptyList())
        binding.rvPhoto.adapter = adapter
        presenter.loadPhotos()

        clickListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadPhotos()
    }

    private fun clickListener() {
        binding.btnAddPhoto.setOnClickListener {
            navigateToCameraActivity()
        }
    }

    private fun cleanUp() {
        val cleanupWorker = PeriodicWorkRequestBuilder<CleanupWorker>(2, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueue(cleanupWorker)
    }

    override fun navigateToCameraActivity() {
        val intent = Intent(this, PhotoActivity::class.java)
        startActivity(intent)
//        Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show()
    }

    override fun showPhotos(photos: List<Photo>) {
        adapter.differ.submitList(photos)
    }

    override fun showProgressbar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showAddButton() {
        binding.btnAddPhoto.visibility = View.VISIBLE
    }

    override fun hideAddButton() {
        binding.btnAddPhoto.visibility = View.GONE
    }
}