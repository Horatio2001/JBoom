package com.example.jboom.ui.gallery;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jboom.R;
import com.example.jboom.databinding.FragmentGalleryBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    int c = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView counter = (TextView)getActivity().findViewById(R.id.counter);
        TextView timeCounter = (TextView)getActivity().findViewById(R.id.time_counter);
        FloatingActionButton fab1 = (FloatingActionButton)getActivity().findViewById(R.id.fab1);
        Button button = (Button)getActivity().findViewById(R.id.button);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                counter.setText(Integer.parseInt((String) counter.getText())+1+"");
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter.setText("0");
                timeCounter.setText("10s");
                CountDownTimer countDownTimer0 = new CountDownTimer(4*1000, 1000) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTick(long millisUntilFinished) {
                        button.setText(millisUntilFinished /1000+"");
                    }

                    @Override
                    public void onFinish() {
                        button.setText("戳");
                        CountDownTimer countDownTimer = new CountDownTimer(10 * 1000, 1000){
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onTick(long millisUntilFinished) {
                                timeCounter.setText(millisUntilFinished / 1000 + "s");
                                button.setEnabled(true);
                            }

                            @Override
                            public void onFinish() {
                                timeCounter.setText("时间到");
                                button.setEnabled(false);
                            }
                        };
                        countDownTimer.start();
                    }
                };
                countDownTimer0.start();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}