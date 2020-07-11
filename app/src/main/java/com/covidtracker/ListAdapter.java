package com.covidtracker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListAdapter extends ArrayAdapter<CountriesModel> {

    private Context context;
    private List<CountriesModel> countriesModelList;
    private List<CountriesModel> countriesModelListFiltered;

    public ListAdapter(@NonNull Context context, List<CountriesModel> countriesModelList ) {
        super(context, R.layout.list_item, countriesModelList);

        this.context = context;
        this.countriesModelList = countriesModelList;
        this.countriesModelListFiltered = countriesModelList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null, true);
        TextView countryName = view.findViewById(R.id.countryName);
        CircleImageView countryFlag = view.findViewById(R.id.flagImage);

        countryName.setText(countriesModelListFiltered.get(position).getCountry());
        Glide.with(context).load(countriesModelListFiltered.get(position).getFlag()).into(countryFlag);
        return view;
    }

    @Override
    public int getCount() {
        return countriesModelListFiltered.size();
    }

    @Nullable
    @Override
    public CountriesModel getItem(int position) {
        return countriesModelListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults filterResults = new FilterResults();
                if(constraint == null || constraint.length() == 0){
                    filterResults.count = countriesModelList.size();
                    filterResults.values = countriesModelList;

                }else{
                    List<CountriesModel> resultsModel = new ArrayList<>();
                    String searchStr = constraint.toString().toLowerCase();

                    for(CountriesModel itemsModel:countriesModelList){
                        if(itemsModel.getCountry().toLowerCase().contains(searchStr)){
                            resultsModel.add(itemsModel);

                        }
                        filterResults.count = resultsModel.size();
                        filterResults.values = resultsModel;
                    }


                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                countriesModelListFiltered = (List<CountriesModel>) results.values;
                AffectedCountriesActivity.countriesModelList = (List<CountriesModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }

    }

