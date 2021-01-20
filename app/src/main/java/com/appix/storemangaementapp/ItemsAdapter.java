package com.appix.storemangaementapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> implements Filterable {
   private ArrayList<Store> listdata;
   Context context;
      ListItemClickListener mOnClickListener;
    private List<Store> exampleListFull;
    // RecyclerView recyclerView;
    public ItemsAdapter(Context context,ArrayList<Store> listdata,ListItemClickListener onClickListener) {
        this.listdata = listdata;
        this.context = context;
        this.mOnClickListener = onClickListener;
        exampleListFull = new ArrayList<>(listdata);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.items_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Store myListData = listdata.get(position);
        holder.textView_name.setText(myListData.getName());
        holder.textView_scale.setText(myListData.getItem_scale());
        holder.textView_price.setText(myListData.getItem_price());
        holder.tv_quantity.setText(myListData.getItem_quantity());
        holder.tv_qeematfrokht.setText(myListData.getQeemat_frokht());
        holder.textView_totalprice.setText(""+myListData.getTotal_price());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView_name,textView_scale, textView_price, textView_totalprice, tv_quantity,tv_qeematfrokht;

        public ViewHolder(View itemView) {
            super(itemView);

            this.textView_name = (TextView) itemView.findViewById(R.id.item_name);
            this.textView_scale = (TextView) itemView.findViewById(R.id.item_scale);
            this.textView_price = (TextView) itemView.findViewById(R.id.price);
            this.tv_quantity = (TextView) itemView.findViewById(R.id.tv_quantity);
            this.textView_totalprice = (TextView) itemView.findViewById(R.id.price_total);
            this.tv_qeematfrokht = (TextView) itemView.findViewById(R.id.tv_qeematfrokht);
            itemView.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mOnClickListener.onListItemClick(position);
        }
    }
    //
//      @Override
//        public Filter getFilter() {
//            return exampleFilter;
//        }

        private Filter exampleFilter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<Store> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(exampleListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (Store item : exampleListFull) {
                        if (item.getName().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                listdata.clear();
                listdata.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };
}