package com.beintrack.bittask.ui.view.profile;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.beintrack.bittask.BR;
import com.beintrack.bittask.R;
import com.beintrack.bittask.databinding.ActivityProfileBinding;
import com.beintrack.bittask.model.Image;
import com.beintrack.bittask.ui.adapter.ImagesAdapter;
import com.beintrack.bittask.ui.view.base.BaseActivity;
import com.beintrack.bittask.ui.dialog.ImageFragment;
import com.beintrack.bittask.ui.viewmodel.ProfileViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class ProfileActivity extends BaseActivity<ActivityProfileBinding, ProfileViewModel>
        implements AppBarLayout.OnOffsetChangedListener, ProfileNavigation {

    private ProfileViewModel viewModel;
    private ActivityProfileBinding binding;


    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_profile;
    }

    @Override
    public ProfileViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = getViewDataBinding();

        int span = getResources().getInteger(R.integer.span_count);
        binding.imagesRecycler.setLayoutManager(new GridLayoutManager(this, span));
        binding.imagesRecycler.setItemAnimator(new DefaultItemAnimator());

        binding.rootView.setOnRefreshListener(() -> viewModel.getProfile());

        observersRegisters();
    }

    private void observersRegisters() {
        ImagesAdapter adapter = new ImagesAdapter();
        viewModel.getProfile().observe(this, profile -> {
            if (profile != null)
                adapter.setImageList(profile.getImageList());
        });
        viewModel.isLoading().observe(this, binding.rootView::setRefreshing);
        viewModel.setNavigator(this);
        binding.imagesRecycler.setAdapter(adapter);

        adapter.setOnItemClickedListener(viewModel.getNavigator()::openImage);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        binding.rootView.setEnabled(i == 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.appBar.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.appBar.removeOnOffsetChangedListener(this);
    }

    @Override
    public void openImage(Image image) {
        ImageFragment dialog = ImageFragment.newInstance(image);
        dialog.show(getSupportFragmentManager());
    }
}
