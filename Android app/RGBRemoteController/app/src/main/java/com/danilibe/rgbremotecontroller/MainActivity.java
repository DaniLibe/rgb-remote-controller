package com.danilibe.rgbremotecontroller;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar red_bar = (SeekBar) findViewById(R.id.red_bar);
        SeekBar green_bar = (SeekBar) findViewById(R.id.green_bar);
        SeekBar blue_bar = (SeekBar) findViewById(R.id.blue_bar);
        TextView led_color = (TextView) findViewById(R.id.led_color);

        RGBRemoteController rgb_remote_controller = new RGBRemoteController(red_bar, green_bar, blue_bar, led_color);
        rgb_remote_controller.change_label_color();
        rgb_remote_controller.execute();
    }

    public void print_color(View view)
    {
        TextView led_color = (TextView) findViewById(R.id.led_color);

        ColorDrawable led_colorBackground = (ColorDrawable) led_color.getBackground();

        Toast.makeText(MainActivity.this, String.valueOf(led_colorBackground.getColor()), Toast.LENGTH_SHORT).show();
    }
}
