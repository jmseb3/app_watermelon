package com.math.watermelon


import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.room.Room

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.math.watermelon.databinding.ActivityMainBinding
import com.math.watermelon.room.AppDatabase


class MainActivity : AppCompatActivity() {

    private val fragmentOne by lazy { conceptFragment() }
    private val fragmentThree by lazy { TestFragment() }
    private val fragmentFour by lazy { etcFragment() }
    private val fragmentTwo by lazy { FavoriteFragment() }
    private var backKeyPressedTime: Long = 0

    private lateinit var binding: ActivityMainBinding
    private lateinit var mBottomNavigationView: BottomNavigationView
    private lateinit var appUpdateManager: AppUpdateManager
    private val appUpdateLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            MaterialAlertDialogBuilder(this)
                .setPositiveButton("ok") { _, _ -> }
                .setMessage("업데이트가 취소되었습니다.")
                .show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val systemBars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.root.updatePadding(top = systemBars.top, bottom = systemBars.bottom)
            windowInsets
        }
        ViewCompat.requestApplyInsets(binding.root)

        mBottomNavigationView = findViewById(R.id.bottom_navigation)

        initNavigationBar()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleBackPressed()
            }
        })

        appUpdateManager = AppUpdateManagerFactory.create(this)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() ==
                UpdateAvailability.UPDATE_AVAILABLE
            ) {
                appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    appUpdateLauncher,
                    AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
                )
            }
        }


    }

    fun hideKeyboard(editText: EditText) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private fun handleBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                finish();
            }

        } else {
            supportFragmentManager.popBackStack()
        }

    }

    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() ==
                    UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        appUpdateLauncher,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build()
                    )

                }
            }
    }

    private fun initNavigationBar() {
        mBottomNavigationView.run {
            setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_concept -> {
                        changeFragment(fragmentOne)
                    }
                    R.id.nav_favorite -> {
                        changeFragment(fragmentTwo)
                    }
                    R.id.nav_test -> {
                        changeFragment(fragmentThree)
                    }
                    R.id.nav_etc ->
                        changeFragment(fragmentFour)
                }
                true
            }
            selectedItemId = R.id.nav_concept
        }
    }

    private fun changeFragment(fragment: Fragment) {
        if (supportFragmentManager.backStackEntryCount > 0) {
            for (i in 0..supportFragmentManager.backStackEntryCount) {
                supportFragmentManager.popBackStack()
            }
        }
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragcontainer, fragment)
            .commit()
    }


}



