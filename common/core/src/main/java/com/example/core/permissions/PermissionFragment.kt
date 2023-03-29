package com.example.core.permissions

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.core.R
import com.example.core.presentation.BaseFragment
import com.example.core.databinding.FragmentPermissionBinding
import com.example.core.fragmentViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PermissionFragment : BaseFragment<PermissionState, Unit>(R.layout.fragment_permission) {

    override val vm: PermissionViewModel by viewModel()

    private val binding by fragmentViewBinding(FragmentPermissionBinding::bind)

    var getPermissionAfterLogin = false

    private val requestCommonPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        vm.onRequestCommonResult(result)
    }

    private val requestGeoPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        vm.onRequestGeoResult()
    }

    override fun handleEvent(event: Unit) = Unit

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initArguments()
    }

    private fun initView() {
        binding.btnAccept.setOnClickListener {
            vm.acceptPermission()
        }
    }

    private fun initArguments() {
        val permission = arguments?.getSerializable(KEY_PERMISSION) as Permission?

        vm.init(permission)

        if (permission == Permission.GEO_LOGIN) {
            getPermissionAfterLogin = true
        }
    }

    override fun renderState(state: PermissionState) {
        when (state) {
            is PermissionState.SetTitleAndDescription -> {
                binding.header.title.setText(state.titleId)
                binding.tvInfo.setText(state.descriptionId)
            }

            is PermissionState.SetAnimation -> {
                with(binding) {
                    lottie.setAnimation(id)
                    lottie.repeatCount = LottieDrawable.INFINITE
                    lottie.playAnimation()
                }
            }

            is PermissionState.AskGeoLoginPermission -> requestGeoPermissionLauncher.launch(state.permissions)


            is PermissionState.AskPermission -> requestCommonPermissionLauncher.launch(state.permissions)

            is PermissionState.Close ->  { vm.onBackPressed() }

            is PermissionState.SetGrantResult -> findNavController().previousBackStackEntry?.savedStateHandle?.set(
                KEY_PERMISSION,
                state.result
            )


            is PermissionState.StartLaunchActivity -> (requireActivity() as? PermissionCallback)?.launch()
        }
    }

    companion object {
        const val KEY_PERMISSION = "key_permission"
    }
}
