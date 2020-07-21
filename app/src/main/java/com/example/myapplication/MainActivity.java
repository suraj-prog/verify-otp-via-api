package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
Button send,verify;
EditText name,phone,verOtp;
int randomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText)findViewById(R.id.txtName);
        phone = (EditText)findViewById(R.id.txtPhone);
        verOtp = (EditText)findViewById(R.id.txtVerifyOtp);
        send = (Button)findViewById(R.id.btnSend);
        verify = (Button)findViewById(R.id.btnVerifyOtp);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // Construct data
                    String apiKey = "apikey=" + "e7dXlblp6Ek-wuZo7hk3r9ZihCX3Eb1w2JF45Ksptm";
                    Random random = new Random();
                    randomNumber=random.nextInt(9999);
                    String message = "&message=" + "Your TokenQ OTP is"+randomNumber+"This OTP will automatically expires in 15 minutes.";
                    String sender = "&sender=" + "BP-TokenQ";
                    String numbers = "&numbers=" + phone.getText().toString();

                    // Send data
                    HttpURLConnection conn = (HttpURLConnection) new URL("http://socialenginespecialist.com/PuneDC/tokenq/api/v1/index.php?action=getOtp&mobNo="+phone.getText().toString()).openConnection();
                    String data = apiKey + numbers + message + sender;
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
                    conn.getOutputStream().write(data.getBytes("UTF-8"));
                    final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    final StringBuffer stringBuffer = new StringBuffer();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        stringBuffer.append(line);
                    }
                    rd.close();
                    Toast.makeText(getApplicationContext(), "OTP Send Successfully", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "OTP Can't Send", Toast.LENGTH_LONG).show();
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(randomNumber==Integer.valueOf(verOtp.getText().toString())){
                    Toast.makeText(getApplicationContext(),"You had login successfully !!",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"wrong Otp",Toast.LENGTH_LONG).show();
                    verOtp.setText("");
                }
            }
        });
    }
}
