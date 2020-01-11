package com.example.donatorapp.adapters.Hospital;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.donatorapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {


    private Context context;
    private List<Hospital> itemList;


    public HospitalAdapter(Context context, List<Hospital> itemList) {
        this.context = context;
        this.itemList = itemList;


    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hospital, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        holder.textViewHospitalName.setText(itemList.get(listPosition).getName());
        holder.textViewHospitalNumber.setText(itemList.get(listPosition).getPhone());
        holder.textViewHospitalAddress.setText(itemList.get(listPosition).getAddress());

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewHospitalName;
        TextView textViewHospitalNumber;
        TextView textViewHospitalAddress;
        CircleImageView circleImageView;

        ViewHolder(View itemView) {
            super(itemView);
            textViewHospitalName = itemView.findViewById(R.id.text_view_hospital_name);
            textViewHospitalNumber = itemView.findViewById(R.id.text_view_hospital_number);
            textViewHospitalAddress = itemView.findViewById(R.id.text_view_hospital_address);
            circleImageView = itemView.findViewById(R.id.image_view_hospital);
        }

        @Override
        public void onClick(View view) {
        }
    }
}
