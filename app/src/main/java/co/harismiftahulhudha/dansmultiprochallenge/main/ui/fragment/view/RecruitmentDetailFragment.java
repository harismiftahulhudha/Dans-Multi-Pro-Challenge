package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseFragment;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.FragmentRecruitmentDetailBinding;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel.MainViewModel;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.viewmodel.RecruitmentDetailViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecruitmentDetailFragment extends BaseFragment {
    private FragmentRecruitmentDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecruitmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private RecruitmentDetailViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RecruitmentDetailViewModel.class);
        if (savedInstanceState != null) {
            viewModel.id = savedInstanceState.getString("id", "");
        }
        initComponents();
        subscribeListeners();
        subscribeObservers();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("id", viewModel.id);
    }

    @Override
    public void initComponents() {
        final MainViewModel activityViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        activityViewModel.setShowMenu(false);
    }

    @Override
    public void subscribeListeners() {
        binding.toolbar.setNavigationOnClickListener((v) -> Navigation.findNavController(v).popBackStack());
        binding.txtCompanyWebsite.setOnClickListener((v) -> {
            if (!viewModel.companyUrl.equals("")) {
                String url = viewModel.companyUrl;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public void subscribeObservers() {
        viewModel.getDetailRecruitment();
        viewModel.observeDetailRecruitment().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                switch (data.status) {
                    case ERROR:
                        if (data.data != null) {
                            setDataToView(data.data);
                        }
                        Toast.makeText(requireContext(), data.message, Toast.LENGTH_LONG).show();
                        break;
                    case LOADING:
                    case SUCCESS:
                        if (data.data != null) {
                            setDataToView(data.data);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(RecruitmentEntity data) {
        viewModel.companyUrl = data.getCompanyUrl();
        binding.txtCompanyName.setText(data.getCompany());
        binding.txtCompanyLocation.setText(data.getLocation());
        binding.txtJobTitle.setText(data.getTitle());
        if (data.getType().equals("Full Time")) {
            binding.txtJobType.setText("Yes");
        } else {
            binding.txtJobType.setText("No");
        }
        binding.txtJobDescription.setText(HtmlCompat.fromHtml(data.getDescription(), 0));
        binding.txtJobDescription.setMovementMethod(LinkMovementMethod.getInstance());
    }
}