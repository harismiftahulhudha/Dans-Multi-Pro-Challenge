package co.harismiftahulhudha.dansmultiprochallenge.main.ui.fragment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import co.harismiftahulhudha.dansmultiprochallenge.R;
import co.harismiftahulhudha.dansmultiprochallenge.core.database.roomDB.entity.RecruitmentEntity;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.ItemLoadingBinding;
import co.harismiftahulhudha.dansmultiprochallenge.databinding.ItemRecruitmentBinding;

public class RecruitmentAdapter extends ListAdapter<RecruitmentEntity, RecyclerView.ViewHolder> {

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public List<RecruitmentEntity> getData(List<RecruitmentEntity> data, boolean isLoading) {
        List<RecruitmentEntity> list = new ArrayList<>(data);
        boolean isPaginate;
        if (data.size() > 0 && data.size() % 10 == 0) {
            isPaginate = true;
        } else {
            isPaginate = isLoading && data.size() == 0;
        }
        if (isPaginate && !isLoading(list)) {
            list.add(addLoading());
        }
        return list;
    }

    private RecruitmentEntity addLoading() {
        return new RecruitmentEntity("loading",
                "", "", "", "", "", "", "", "", "", "");

    }

    private boolean isLoading(List<RecruitmentEntity> data) {
        return data.size() > 0 && Objects.equals(data.get(data.size() - 1).getId(), "loading");
    }

    public RecruitmentAdapter(@NonNull DiffUtil.ItemCallback<RecruitmentEntity> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == R.layout.item_recruitment) {
            return new ViewHolder(
                    ItemRecruitmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false),
                    (position, view) -> {
                        if (onClickListener != null && getItem(position) != null) {
                            onClickListener.onClick(getItem(position), view);
                        }
                    }
            );
        } else {
            return new LoadingViewHolder(ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder.getItemViewType() == R.layout.item_recruitment) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.bind(getItem(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getId().equals("loading")) {
            return R.layout.item_loading;
        } else {
            return R.layout.item_recruitment;
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemRecruitmentBinding binding;

        public ViewHolder(@NonNull ItemRecruitmentBinding binding, OnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.getRoot().setOnClickListener((view) -> {
                if (onClickListener != null) {
                    onClickListener.onClick(getAdapterPosition(), view);
                }
            });
        }

        public void bind(RecruitmentEntity data) {
            Glide.with(binding.getRoot().getContext())
                    .load(data.getCompanyLogo())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(binding.imgCompanyLogo);
            binding.txtCompanyLocation.setText(data.getLocation());
            binding.txtCompanyName.setText(data.getCompany());
            binding.txtJobTitle.setText(data.getTitle());
        }

        private interface OnClickListener {
            void onClick(int position, View view);
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(@NonNull ItemLoadingBinding binding) {
            super(binding.getRoot());
        }
    }

    public static class DiffCallback extends DiffUtil.ItemCallback<RecruitmentEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull RecruitmentEntity oldItem, @NonNull RecruitmentEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull RecruitmentEntity oldItem, @NonNull RecruitmentEntity newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }
    }

    public interface OnClickListener {
        void onClick(RecruitmentEntity data, View view);
    }
}
