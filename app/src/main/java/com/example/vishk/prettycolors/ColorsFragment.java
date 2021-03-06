package com.example.vishk.prettycolors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.text.TextWatcher;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView noPaletteText;
    View v;

    private static List<PaletteItem> paletteItems;

    public ColorsFragment() {
        // Required empty public constructor
        paletteItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_colors, container, false);
        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new PaletteListAdapter(paletteItems);
        recyclerView.setAdapter(adapter);

        noPaletteText = v.findViewById(R.id.noPaletteText);

        if (paletteItems.isEmpty()) {
            noPaletteText.setVisibility(View.VISIBLE);
        } else {
            noPaletteText.setVisibility(View.INVISIBLE);
        }

        return v;
    }


    public static void addPalette(PaletteItem paletteItem) {
        paletteItems.add(paletteItem);
    }
}
