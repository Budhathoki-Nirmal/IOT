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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder>implements Filterable {

    Context context;
    List<Model> table_list;
    List<Model> search_list;
    List<Model> filter_list;
    

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

    public TableAdapter(Context context,List<Model> table_list) {
        this.context = context;
        this.table_list=table_list;
        search_list=new ArrayList<>(table_list);
        filter_list=new ArrayList<>(table_list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (table_list!=null && table_list.size()>0){
            Model model=table_list.get(position);
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
        return table_list.size();
    }


    public void filterDateRange(Date charText,Date charText1) {
        List<Model> rangedList = new ArrayList<>();
        if (charText.equals("")|| charText == null) {
            rangedList.addAll(filter_list);
        } else {
            for (Model wp : filter_list) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy",new Locale("eng", "NEP"));
                try {
                    Date strDate = sdf.parse(wp.getDate());
                    if (charText1.after(strDate)&&charText.before(strDate)) {
                        rangedList.add(wp);
                    }else{

                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        table_list.clear();
        table_list.addAll((List) rangedList);
        notifyDataSetChanged();
    };




    @Override
    public android.widget.Filter getFilter() {
        return exampleSearch;
    }

    private android.widget.Filter exampleSearch = new Filter() {
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
            table_list.clear();
            table_list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
