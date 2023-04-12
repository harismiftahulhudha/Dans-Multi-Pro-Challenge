package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import co.harismiftahulhudha.dansmultiprochallenge.R;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.core.util.base.BaseFragment;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.FragmentRecruitmentBinding;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.activity.viewmodel.MainViewModel;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.adapter.RecruitmentAdapter;
import co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.viewmodel.RecruitmentViewModel;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RecruitmentFragment extends BaseFragment {
    private FragmentRecruitmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRecruitmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private RecruitmentViewModel viewModel;
    private RecruitmentAdapter adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(RecruitmentViewModel.class);
        if (savedInstanceState != null) {
            viewModel.inputLocation = savedInstanceState.getString("inputLocation", "");
            viewModel.inputQuery = savedInstanceState.getString("inputQuery", "");
            viewModel.isFulltime = savedInstanceState.getBoolean("isFulltime", false);
        }
        initComponents();
        subscribeListeners();
        subscribeObservers();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("inputLocation", viewModel.inputLocation);
        outState.putString("inputQuery", viewModel.inputQuery);
        outState.putBoolean("isFulltime", viewModel.isFulltime);
    }

    @Override
    public void initComponents() {
        final MainViewModel activityViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        activityViewModel.setShowMenu(true);
        binding.rvRecruitment.setHasFixedSize(true);
        binding.rvRecruitment.setItemViewCacheSize(20);
        binding.rvRecruitment.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new RecruitmentAdapter(new RecruitmentAdapter.DiffCallback());
        binding.rvRecruitment.setAdapter(adapter);
        if (!viewModel.inputLocation.equals("")) {
            binding.inputLocation.setText(viewModel.inputLocation);
        }
        if (!viewModel.inputQuery.equals("")) {
            binding.inputQuery.setText(viewModel.inputQuery);
        }
        binding.switchFulltime.setChecked(viewModel.isFulltime);
    }

    @Override
    public void subscribeListeners() {
        adapter.setOnClickListener((RecruitmentEntity data, View view) -> {
            Navigation.findNavController(view).navigate(RecruitmentFragmentDirections.actionRecruitmentFragment2ToRecruitmentDetailFragment(data.getId()));
        });
        binding.rvRecruitment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    assert layoutManager != null;
                    int position = layoutManager.findLastVisibleItemPosition();
                    if (!recyclerView.canScrollVertically(1) && position >= 0) {
                        int type = adapter.getItemViewType(position);
                        if (adapter.getItemCount() > 0 && type == R.layout.item_loading) {
                            viewModel.getListRecruitment(true);
                        }
                    }
                }
            }
        });
        binding.linearFilter.setOnClickListener((v) -> {
            viewModel.setIsMenuFilterOpen();
        });
        binding.inputQuery.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.getListRecruitment(false);
                return true;
            }
            return false;
        });
        binding.inputQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setInputQuery(Objects.requireNonNull(binding.inputQuery.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.switchFulltime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            viewModel.setIsFulltime(isChecked);
        });
        binding.inputLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setInputLocation(Objects.requireNonNull(binding.inputLocation.getText()).toString().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.btnFilter.setOnClickListener((v) -> {
            viewModel.getListRecruitment(false);
        });
    }

    @Override
    public void subscribeObservers() {
        viewModel.getListRecruitment(false);
        viewModel.observeListRecruitment().observe(getViewLifecycleOwner(), data -> {
            if (data != null) {
                switch (data.status) {
                    case ERROR:
                        if (data.data != null) {
                            adapter.submitList(adapter.getData(data.data, false));
                            setNotFoundVisibility(data.data.size());
                        }
                        Toast.makeText(requireContext(), data.message, Toast.LENGTH_LONG).show();
                        break;
                    case LOADING:
                        if (data.data != null) {
                            adapter.submitList(adapter.getData(data.data, true));
                        }
                        break;
                    case SUCCESS:
                        if (data.data != null) {
                            adapter.submitList(adapter.getData(data.data, false));
                            setNotFoundVisibility(data.data.size());
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        viewModel.observeIsMenuFilterOpen.observe(getViewLifecycleOwner(), data -> {
            if (data != null && data) {
                binding.cardFilter.setVisibility(View.VISIBLE);
                binding.imgArrowFilter.setRotation(270f);
            } else {
                binding.cardFilter.setVisibility(View.GONE);
                binding.imgArrowFilter.setRotation(90f);
            }
        });
    }

    private void setNotFoundVisibility(int total) {
        if (total == 0) {
            binding.linearNotFound.setVisibility(View.VISIBLE);
            binding.rvRecruitment.setVisibility(View.GONE);
        } else {
            binding.linearNotFound.setVisibility(View.GONE);
            binding.rvRecruitment.setVisibility(View.VISIBLE);
        }
    }
}