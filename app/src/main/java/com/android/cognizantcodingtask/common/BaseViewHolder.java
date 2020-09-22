package com.android.cognizantcodingtask.common;


import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

import com.android.cognizantcodingtask.view.base.BaseViewModel;


public class BaseViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public BaseViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object obj, BaseHandler handler, BaseViewModel baseViewModel) {
        binding.setVariable(BR.obj, obj);
        binding.setVariable(BR.handlers, handler);
//        binding.setVariable(BR.viewModel, baseViewModel);
        binding.executePendingBindings();
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}