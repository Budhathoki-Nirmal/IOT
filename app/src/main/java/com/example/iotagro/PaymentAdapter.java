package com.example.iotagro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder>implements Filterable {

    Context context;
    List<Model> payment_list;
    List<Model> search_list;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date_tv,temp_tv,hum_tv,soil_tv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date_tv=itemView.findViewById(R.id.date_tv);
            temp_tv=itemView.findViewById(R.id.temp_tv);
            hum_tv=itemView.findViewById(R.id.hum_tv);
            soil_tv=itemView.findViewById(R.id.soil_tv);

        }
    }

    public PaymentAdapter(Context context,List<Model> payment_list) {
        this.context = context;
        this.payment_list=payment_list;
        search_list=new ArrayList<>(payment_list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (payment_list!=null && payment_list.size()>0){
            Model model=payment_list.get(position);
            holder.date_tv.setText(model.getDate());
            holder.temp_tv.setText(model.getTemp());
            holder.hum_tv.setText(model.getHum());
            holder.soil_tv.setText(model.getSoil());

        }else {
            return;
        }

    }

    @Override
    public int getItemCount() {
        return payment_list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleSearch;
    }

    private Filter exampleSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Model> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(search_list);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Model item : search_list) {
                    if (item.getDate().toLowerCase().contains(filterPattern)) {
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
            payment_list.clear();
            payment_list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
}
