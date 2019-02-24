package com.example.tournaments.warroom.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tournaments.warroom.PurchaseEntryActivity;
import com.example.tournaments.warroom.R;
import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import java.util.ArrayList;
import java.util.List;

public class EventFragment extends Fragment {
    private CardView mCardView;
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.event_card, container, false);
        mCardView=view.findViewById(R.id.event_cardview);
        setPieMembers(50);
        Button purchase_entry=(Button)view.findViewById(R.id.purchase_entry);
        purchase_entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),PurchaseEntryActivity.class));
            }
        });
        return view;
    }
    void setPieMembers(int number)
    {
        PieChart pieChart = (PieChart) view.findViewById(R.id.pie_chart);
        List<ChartData> data = new ArrayList<>();
        data.add(new ChartData("Joined", number));
        pieChart.setChartData(data);
    }
    public CardView getCardView(){
        return mCardView;
    }
}
