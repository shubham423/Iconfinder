package com.example.iconfinder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.iconfinder.databinding.ActivityMainBinding
import com.example.iconfinder.ui.home.HomeFragment
import com.example.iconfinder.ui.iconsets.IconSetsFragment.Companion.ICON_ID
import dagger.hilt.android.AndroidEntryPoint
import io.branch.referral.Branch
import io.branch.referral.util.BranchEvent
import org.json.JSONException
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onStart() {
        super.onStart()
        Branch.sessionBuilder(this).withCallback { branchUniversalObject, linkProperties, error ->
            if (error != null) {
                Timber.tag("BranchSDK_Tester").e("branch init failed. Caused by -%s", error.message)
            } else {
                Timber.tag("BranchSDK_Tester").i("branch init complete!")
                if (branchUniversalObject != null) {
                    Timber.tag("BranchSDK_Tester").i("title %s", branchUniversalObject.title)
                    Timber.tag("BranchSDK_Tester")
                        .i("CanonicalIdentifier %s", branchUniversalObject.canonicalIdentifier)
                    val metaData = branchUniversalObject.contentMetadata.convertToJson()
                    Timber.tag("BranchSDK_Tester").i("metadata %s", metaData)

                }
                if (linkProperties != null) {
                    Timber.tag("BranchSDK_Tester").i("Channel %s", linkProperties.channel)
                    Timber.tag("BranchSDK_Tester")
                        .i("control params %s", linkProperties.controlParams)

                    try {
                        if (linkProperties.controlParams?.get("\$iconset_category") != null) {
                            val bundle = Bundle()
                            bundle.putString(
                                HomeFragment.ICON_SET_IDENTIFIER,
                                linkProperties.controlParams?.get("\$iconsetid")
                            )
                            BranchEvent("NAVIGATE_TO_ICON_SETS_SCREEN").logEvent(this)
                            navController.navigate(R.id.iconSetsListFragment, bundle)
                        }
                        if (linkProperties.controlParams?.get("\$iconid") != null){
                            val bundle = Bundle()
                            bundle.putString(
                               ICON_ID,
                                linkProperties.controlParams?.get("\$iconid")
                            )
                            BranchEvent("NAVIGATE_TO_ICONS_SCREEN").logEvent(this)
                            navController.navigate(R.id.iconsFragment, bundle)
                        }
                    } catch (e: JSONException) {
                        Timber.e(e.message)
                    }
                }
            }
        }.withData(this.intent.data).init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        this.intent = intent
        Branch.sessionBuilder(this).withCallback { referringParams, error ->
            if (error != null) {
                Timber.tag("BranchSDK_Tester").e(error.message)
            } else if (referringParams != null) {
                Timber.tag("BranchSDK_Tester").i(referringParams.toString())
            }
        }.reInit()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}