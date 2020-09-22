package com.android.cognizantcodingtask.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.android.cognizantcodingtask.data.common.BaseTModel;
import com.android.cognizantcodingtask.view.base.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class BaseAdapter<T extends BaseTModel> extends RecyclerView.Adapter<BaseViewHolder> {

    public int layoutResource = 0;
    public List<T> list = new ArrayList<>();
    public BaseHandler handler;
    private BaseViewModel viewModel;
    private boolean isDummyCardPresent = false;


    public BaseAdapter(int layoutResource) {
        this.layoutResource = layoutResource;
    }

    public BaseAdapter(int layoutResource, BaseHandler handler) {
        this.layoutResource = layoutResource;
        this.handler = handler;
    }


    public BaseAdapter(int layoutResource, BaseViewModel viewModel, BaseHandler handler) {
        this.layoutResource = layoutResource;
        this.viewModel = viewModel;
        this.handler = handler;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(
                layoutInflater, viewType, parent, false);
        return new BaseViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder,
                                 int position) {
        T obj = list.get(position);
        holder.bind(obj, handler, viewModel);

    }

    @Override
    public int getItemViewType(int position) {
        if (layoutResource != 0) {

            return layoutResource;
        } else {
            /**
             * for different view types
             */
            return list.get(position).getLayoutResId();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    /**
     * adding data for the first time in the list and scroll if required to last position
     *
     * @param li
     */
    public void updateList(List<T> li) {
        if (li != null) {
            this.list = null;
            this.list = new ArrayList<T>(li);
            notifyDataSetChanged();
        }
    }

    /**
     * insert data at the bottom of the list
     *
     * @param dataList
     */
    public void insertIntoBottomList(List<T> dataList) {
        int position = list.size();
        if (dataList == null) {
            return;
        }
        for (T datumList : dataList) {
            list.add(position, datumList);
            notifyItemInserted(position);
            position++;
        }

    }

    /**
     * inserting list data at the top of the list
     *
     * @param listDatum
     */
    public void insertIntoTopList(List<T> listDatum) {
        int position = 0;
        if (listDatum == null) {
            return;
        }
        for (T datum : listDatum) {
            list.add(position, datum);
            notifyItemInserted(position);
            position++;
        }

    }


    /**
     * inserting object at the top of the list
     *
     * @param datum
     */
    public void insertIntoTopDatum(T datum) {

        if (list != null && datum != null) {
            list.add(0, datum);
            notifyDataSetChanged();
        }

    }

    /**
     * inserting object at the Bottom of the list
     *
     * @param datum
     */
    public void insertIntoBottomDatum(T datum) {
        if (datum != null) {
            list.add(datum);
            notifyItemInserted(list.size() - 1);
        }
    }

    /**
     * basic function can make use of all below method if required
     *
     * @param
     */

    public List<T> getItems() {
        return list;
    }


    public void clearItems() {
        list.clear();
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return list.size() > position ? list.get(position) : null;
    }


    public T getFirstItem() {
        return list.size() > 0 ? list.get(0) : null;
    }

    /**
     * remove item at particular position
     *
     * @param position
     */
    public void removeItem(int position) {
        if (list.size() > 0) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }

}