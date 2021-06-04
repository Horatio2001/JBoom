package com.example.jboom.ui.home;

import android.app.AlertDialog;
import android.app.Service;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.jboom.Chronometer;
import com.example.jboom.R;
import com.example.jboom.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    long stopTime;
    boolean flag = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Vibrator vibrator=(Vibrator)getActivity().getSystemService(Service.VIBRATOR_SERVICE);
        Chronometer chronometer = getActivity().findViewById(R.id.chronometer2);
        FloatingActionButton restart = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        Button stop = (Button) getActivity().findViewById(R.id.stop_chronometer);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "计时开始", Toast.LENGTH_LONG).show();
                if(flag){
                    chronometer.stop();
                }
                chronometer.setBase(SystemClock.elapsedRealtime());
                stopTime = 0;
                chronometer.start();
                flag = true;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                if(flag){
                    if(chronometer.getText().equals("10:00")){
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setMessage("你停在了"+chronometer.getText());
                        builder1.setTitle("还是你强");
                        builder1.show();
                        long[] patter = {1000, 1000, 2000, 50};
                        vibrator.vibrate(patter, 0);
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("你停在了"+chronometer.getText());
                        builder.setTitle("差一点点");
                        builder.show();
                    }
                }
                flag = false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}