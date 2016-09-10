package com.danilibe.rgbremotecontroller;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.SeekBar;
import android.widget.TextView;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class RGBRemoteController extends AsyncTask<Void, Void, Void>
{
    public int red;
    public int green;
    public int blue;
    public SeekBar red_bar;
    public SeekBar green_bar;
    public SeekBar blue_bar;
    public TextView led_color;
    public String board_ip;
    public String rgb_values;
    public int red_prev;
    public int green_prev;
    public int blue_prev;

    public RGBRemoteController(SeekBar red_bar_c, SeekBar green_bar_c, SeekBar blue_bar_c, TextView led_color_c)
    {
        this.red = 0;
        this.green = 0;
        this.blue = 0;
        this.red_bar = red_bar_c;
        this.green_bar = green_bar_c;
        this.blue_bar = blue_bar_c;
        this.led_color = led_color_c;
        this.board_ip = "192.168.1.58";
    }

    public int get_red_value()
    {
        this.red_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RGBRemoteController.this.red = progress;
                RGBRemoteController.this.change_label_color();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return this.red;
    }

    public int get_green_value()
    {
        this.green_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RGBRemoteController.this.green = progress;
                RGBRemoteController.this.change_label_color();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return this.green;
    }

    public int get_blue_value()
    {
        this.blue_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RGBRemoteController.this.blue = progress;
                RGBRemoteController.this.change_label_color();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return this.blue;
    }

    public void change_label_color()
    {
        this.led_color.setBackgroundColor(Color.rgb(get_red_value(), get_green_value(), get_blue_value()));
    }

    @Override
    public Void doInBackground(Void... arg0)
    {
        try
        {
            while (true)
            {
                this.red_prev = this.red;
                this.green_prev = this.green;
                this.blue_prev = this.blue;

                get_red_value();
                get_green_value();
                get_blue_value();

                if ((this.red != this.red_prev) || (this.green != this.green_prev) || (this.blue != this.blue_prev))
                {
                    this.rgb_values = "(" + String.valueOf(this.red) + "," + String.valueOf(this.green) + "," + String.valueOf(this.blue) + ")";

                    DatagramSocket udp_socket = new DatagramSocket();

                    DatagramPacket packet = new DatagramPacket(rgb_values.getBytes(), 0, rgb_values.getBytes().length, InetAddress.getByName(this.board_ip), 7878);

                    udp_socket.send(packet);

                    udp_socket.close();
                }

            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onProgressUpdate(Void... arg1)
    {}

    @Override
    public void onPostExecute(Void arg2)
    {}
}
