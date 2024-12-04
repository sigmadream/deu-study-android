package com.sangkon.ch15

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sangkon.ch15.databinding.ActivityMainBinding
import com.sangkon.ch15_outer.MyAIDLInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var connectionMode = "none"
    var aidlService: MyAIDLInterface? = null
    var aidlJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onCreateAIDLService()
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it.all { permission -> permission.value == true }) {
                onCreateJobScheduler()
            } else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this, "android.permission.POST_NOTIFICATIONS"
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                onCreateJobScheduler()
            } else {
                permissionLauncher.launch(
                    arrayOf(
                        "android.permission.POST_NOTIFICATIONS"
                    )
                )
            }
        } else {
            onCreateJobScheduler()
        }
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStop() {
        super.onStop()
        if (connectionMode === "aidl") {
            onStopAIDLService()
        }
        connectionMode = "none"
        changeViewEnable()
    }

    fun changeViewEnable() = when (connectionMode) {
        "aidl" -> {
            binding.aidlPlay.isEnabled = false
            binding.aidlStop.isEnabled = true
        }
        else -> {
            binding.aidlPlay.isEnabled = true
            binding.aidlStop.isEnabled = false
            binding.aidlProgress.progress = 0
        }
    }

    val aidlConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            aidlService = MyAIDLInterface.Stub.asInterface(service)
            aidlService!!.start()
            binding.aidlProgress.max = aidlService!!.maxDuration
            val backgroundScope = CoroutineScope(Dispatchers.Default + Job())
            aidlJob = backgroundScope.launch {
                while (binding.aidlProgress.progress < binding.aidlProgress.max) {
                    delay(1000)
                    binding.aidlProgress.incrementProgressBy(1000)
                }
            }
            connectionMode = "aidl"
            changeViewEnable()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            aidlService = null
        }
    }

    private fun onCreateAIDLService() {
        binding.aidlPlay.setOnClickListener {
            val intent = Intent("ACTION_SERVICE_AIDL")
            intent.setPackage("com.sangkon.ch15_outer")
            bindService(intent, aidlConnection, Context.BIND_AUTO_CREATE)
        }
        binding.aidlStop.setOnClickListener {
            aidlService!!.stop()
            unbindService(aidlConnection)
            aidlJob?.cancel()
            connectionMode = "none"
            changeViewEnable()
        }
    }

    private fun onStopAIDLService() {
        unbindService(aidlConnection)
    }

    private fun onCreateJobScheduler() {
        var jobScheduler: JobScheduler? = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val builder = JobInfo.Builder(1, ComponentName(this, MyJobService::class.java))
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
        val jobInfo = builder.build()
        jobScheduler!!.schedule(jobInfo)
    }
}