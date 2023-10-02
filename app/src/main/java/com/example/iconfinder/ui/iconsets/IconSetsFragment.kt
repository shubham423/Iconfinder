package com.example.iconfinder.ui.iconsets

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.iconfinder.R
import com.example.iconfinder.databinding.FragmentIconSetsBinding
import com.example.iconfinder.models.BranchEventRequest
import com.example.iconfinder.models.Iconset
import com.example.iconfinder.ui.BranchEventsViewModel
import com.example.iconfinder.ui.home.HomeFragment.Companion.ICON_SET_IDENTIFIER
import com.example.iconfinder.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.util.LinkProperties
import timber.log.Timber


@AndroidEntryPoint
class IconSetsFragment : Fragment(),IconSetsAdapterCallbacks {
    private var _binding: FragmentIconSetsBinding? = null
    private val binding get() = _binding!!
    private val vieModel:IconSetsViewModel by viewModels()
    private lateinit var iconSetsAdapter: IconSetsAdapter
    private val branchViewModel: BranchEventsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentIconSetsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val identifier=arguments?.getString(ICON_SET_IDENTIFIER)
        identifier.let { vieModel.getIconCategorySets(identifier!!) }
        initObservers()
    }

    private fun initObservers() {
        vieModel.iconSetsResponse.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility=View.GONE
                    iconSetsAdapter= IconSetsAdapter(this)
                    binding.rvIconSets.adapter=iconSetsAdapter
                    iconSetsAdapter.submitList(it.data?.iconsets)
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
    private fun shareIconWithLink(linkUrl: String) {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, linkUrl)
        startActivity(Intent.createChooser(shareIntent, "Share Icon"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding=null
    }
    companion object{
        const val ICON_ID="icon_id"
    }

    override fun onIconSetClicked(iconSetId: Int) {
        val bundle=Bundle()
        bundle.putString(ICON_ID,iconSetId.toString())
        findNavController().navigate(R.id.action_iconSetsListFragment_to_iconsFragment,bundle)
    }

    override fun onShareClicked(iconSet: Iconset) {
        branchViewModel.logCustomEvent(BranchEventRequest(name = "ICON_SET_SHARE_EVENT_USING_REST_API", userData = BranchEventRequest.UserData()))

        val iconUniversalObject = BranchUniversalObject()
            .setTitle("Icon")
            .setContentDescription("Check out this icon from my app!")
            .setContentImageUrl("https://www.iconfinder.com/static/img/favicons/favicon-194x194.png?bf2736d2f8")
        val linkProperties = LinkProperties()
            .setFeature("share")
            .addControlParameter("\$iconid",iconSet.iconsetId.toString())

        iconUniversalObject.generateShortUrl(requireContext(), linkProperties) { url, error ->
            if (error == null) {
                // The 'url' variable now contains the shareable deep link for your icon
                // You can use this link to share the icon with others
                shareIconWithLink(url)
            } else {
                // Handle the error
                Timber.tag("BranchSDK_Tester").e( error.message ?: "Error generating link")
            }
        }

    }
}