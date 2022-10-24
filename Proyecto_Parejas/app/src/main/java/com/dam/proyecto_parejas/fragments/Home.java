package com.dam.proyecto_parejas.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.dam.proyecto_parejas.Principal;
import com.dam.proyecto_parejas.R;


public class Home extends Fragment {
    //BUTTON



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageButton Btn_ABC, Btn_number, Btn_color, Btn_saludos, Btn_family, Btn_Alimento;

    public Home() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Btn_ABC = view.findViewById(R.id.Btn_ABC);
        Btn_number = view.findViewById(R.id.Btn_number);
        Btn_color = view.findViewById(R.id.Btn_color);
        Btn_saludos = view.findViewById(R.id.Btn_saludos);
        Btn_family = view.findViewById(R.id.Btn_family);
        Btn_Alimento = view.findViewById(R.id.Btn_Alimento);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Btn_ABC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((Principal) getActivity()).abcedario();
            }
        });
        Btn_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Principal) getActivity()).Numero();
            }
        });
        Btn_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Principal) getActivity()).Colores();
            }
        });
        Btn_saludos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Principal) getActivity()).Saludos();
            }
        });
        Btn_Alimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Principal) getActivity()).Alimentos();
            }
        });
        Btn_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Principal) getActivity()).Familia();
            }
        });

    }
}