package com.example.jboom.ui.slideshow;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import com.example.jboom.databinding.FragmentSlideshowBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.SENSOR_SERVICE;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private long interval;
    private long timeDiff;
    SharedPreferences sharedPreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final long[] startTime = new long[1];
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        TextView display = (TextView) getActivity().findViewById(R.id.height);
        Button button = (Button) getActivity().findViewById(R.id.lift_button);
        FloatingActionButton fab2 = (FloatingActionButton) getActivity().findViewById(R.id.fab2);
        button.setEnabled(false);
        final CountDownTimer[] countDownTimer = {null};
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                display.setText("0");
                interval = (long)(Math.random()*5000+3000);
                button.setEnabled(true);
                countDownTimer[0] = new CountDownTimer(interval, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {}

                    @Override
                    public void onFinish() {
                        display.setText("1");
                    }
                };
                startTime[0] = System.nanoTime();
                countDownTimer[0].start();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                long record2 = sharedPreferences.getLong("record2", 1000000000);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setPositiveButton("确定",null);
                timeDiff = System.nanoTime() - startTime[0];
                button.setEnabled(false);
                if(timeDiff < interval*1000000){
                    builder.setTitle("提前触屏！");
                    countDownTimer[0].cancel();
                }else{
                    if(timeDiff/1000-interval*1000 < record2){
                        builder.setTitle("新纪录！");
                        builder.setMessage(timeDiff/1000 - interval*1000 + " μs");
                        sharedPreferences.edit().putLong("record2", timeDiff/1000-interval*1000).commit();
                    }else{
                        builder.setTitle("最佳纪录："+ record2+" μs");
                        builder.setMessage("当前成绩："+(timeDiff/1000-interval*1000)+" μs");
                    }
                }
                builder.show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}