package com.example.vishk.prettycolors;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private static List<Palette> palettes;

    public ColorsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        palettes = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_colors, container, false);
    }

    public static void addPalette(Palette palette) {
        palettes.add(palette);
    }
}
