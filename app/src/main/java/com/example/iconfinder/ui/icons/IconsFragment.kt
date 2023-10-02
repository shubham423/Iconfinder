package com.example.iconfinder.ui.icons

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentIconsBinding
import com.example.iconfinder.models.Icon
import com.example.iconfinder.ui.BranchEventsViewModel
import com.example.iconfinder.ui.iconsets.IconSetsFragment
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.BRANCH_STANDARD_EVENT
import io.branch.referral.util.BranchContentSchema
import io.branch.referral.util.BranchEvent
import io.branch.referral.util.ContentMetadata
import io.branch.referral.util.CurrencyType
import io.branch.referral.util.LinkProperties
import io.branch.referral.util.ProductCategory
import timber.log.Timber


@AndroidEntryPoint
class IconsFragment : Fragment(),IconsCallBack{
    private var _binding: FragmentIconsBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchView: SearchView
    private lateinit var item: MenuItem
    private val viewModel: IconsViewModel by activityViewModels()
    private var iconSetId:String?=null
    private lateinit var iconAdapter: IconsAdapter
    private var writePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentIconsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iconSetId=arguments?.getString(IconSetsFragment.ICON_ID)
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                writePermissionGranted = it
            }
        setHasOptionsMenu(true)
        initObservers()
    }

    private fun initObservers() {
        viewModel.iconsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    iconAdapter= IconsAdapter(this)
                    binding.rvIcons.adapter=iconAdapter
                    iconAdapter.submitList(it.data?.icons)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility=View.GONE
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility=View.VISIBLE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.serach_menu, menu)
        item = menu.findItem(R.id.action_search)
        searchView = item.actionView as SearchView
        if (iconSetId=="-1") {
            item.expandActionView()
            searchView.setQuery("", false)
        }else{
            if (iconSetId!=null){
                viewModel.getIconsFromIconSets(iconSetId!!.toInt())
            }
        }


        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                val direction = IconsFragmentDirections.actionIconsFragmentToHomeFragment()
                findNavController().navigate(direction)
                return true
            }
        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if (query != null && query != "") {
                    viewModel.setQuery(query)
                    viewModel.searchIcons()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

    }

    private fun showDownloadSheet() {
        val bottomSheet = IconResolutionBottomSheet()
        bottomSheet.show(parentFragmentManager,"bottomsheet")
    }

    private fun askPermission() {
        if (!writePermissionGranted) {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun downloadIcon(icon: Icon) {
        BranchEvent("DOWNLOAD_ICON_EVENT").logEvent(requireContext())
        viewModel.selectedIcon = icon
        askPermission()
        showDownloadSheet()
        logCustomEvent()
    }

    private fun logCustomEvent(){
        BranchEvent("Icon finder custom event")
            .addCustomDataProperty("price", "22")
            .addCustomDataProperty("discount", "2")
            .setCustomerEventAlias("my_custom_alias")
            .logEvent(requireContext())
    }

    private fun logCommerceEvent() {
        val buo = BranchUniversalObject()
            .setCanonicalIdentifier("myprod/1234")
            .setCanonicalUrl("https://test_canonical_url")
            .setTitle("test_title")
            .setContentMetadata(
                ContentMetadata()
                    .addCustomMetadata("custom_metadata_key1", "custom_metadata_val1")
                    .addCustomMetadata("custom_metadata_key1", "custom_metadata_val1")
                    .addImageCaptions("image_caption_1", "image_caption2", "image_caption3")
                    .setAddress(
                        "Street_Name",
                        "test city",
                        "test_state",
                        "test_country",
                        "test_postal_code"
                    )
                    .setRating(5.2, 6.0, 5)
                    .setLocation(-151.67, -124.0)
                    .setPrice(10.0, CurrencyType.USD)
                    .setProductBrand("test_prod_brand")
                    .setProductCategory(ProductCategory.APPAREL_AND_ACCESSORIES)
                    .setProductName("test_prod_name")
                    .setProductCondition(ContentMetadata.CONDITION.EXCELLENT)
                    .setProductVariant("test_prod_variant")
                    .setQuantity(1.5)
                    .setSku("test_sku")
                    .setContentSchema(BranchContentSchema.COMMERCE_PRODUCT)
            )
            .addKeyWord("keyword1")
            .addKeyWord("keyword2")


        //  Do not add an empty branchUniversalObject to the BranchEvent
        BranchEvent(BRANCH_STANDARD_EVENT.ADD_TO_CART)
            .setAffiliation("test_affiliation")
            .setCustomerEventAlias("my_custom_alias")
            .setCoupon("Coupon Code")
            .setCurrency(CurrencyType.USD)
            .setDescription("Customer added item to cart")
            .setShipping(0.0)
            .setTax(9.75)
            .setRevenue(1.5)
            .setSearchQuery("Test Search query")
            .addCustomDataProperty("Custom_Event_Property_Key1", "Custom_Event_Property_val1")
            .addCustomDataProperty("Custom_Event_Property_Key2", "Custom_Event_Property_val2")
            .addContentItems(buo)
            .logEvent(requireContext())
    }


}