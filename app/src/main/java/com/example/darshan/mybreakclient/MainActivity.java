package com.example.darshan.mybreakclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    /*
   author: darshan099

   Online Database: google spreadsheet

   This app can only be used with Break app (check repo)

   Dependecies used:
   1. 'com.squareup.okhttp:okhttp:2.4.0' : http client connection
   2. 'com.squareup.okhttp:okhttp-urlconnection:2.2.0'
   3. 'com.journeyapps:zxing-android-embedded:3.4.0' : scanning qr codes
   4. 'com.google.zxing:core:3.2.1'

   Permission needed:
   1) camera : to get the qr code
   2) stable internet connection

   Program index:
   1) reading quantity from google spreadsheet: line=127
   2) update the order: line=206
   3) scanning qr code: line=77,86

   Overview:
   this app will enable you to decode the qr code from break app

   working: (this app will only work with the break app, check repo)
   1) decode the qr code
   2) list all the orders and update the list
   3) if the qr code is used twice, show invalid


   possible issues/bugs:
   nothing yet



    */

    TextView txtname,txtdosa,txtidly,txtpongal,txt,txtburger,txtpizza,txtsandwich;
    Button btnqrscan,btnread,btnupdate;
    private IntentIntegrator qrscan;
    public String id,name,dosa,idly,pongal,count,burger,pizza,sandwich;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtname=(TextView)findViewById(R.id.txtname);
        txtdosa=(TextView)findViewById(R.id.txtdosa);
        txtidly=(TextView)findViewById(R.id.txtidly);
        txtburger=(TextView)findViewById(R.id.txtburger);
        txtpizza=(TextView)findViewById(R.id.txtpizza);
        txtsandwich=(TextView)findViewById(R.id.txtsandwich);
        txtpongal=(TextView)findViewById(R.id.txtpongal);
        qrscan=new IntentIntegrator(this);
        txt=(TextView)findViewById(R.id.textView);
        btnqrscan=(Button)findViewById(R.id.button);
        btnread=(Button)findViewById(R.id.button2);
        btnupdate=(Button)findViewById(R.id.button3);
        btnupdate.setEnabled(false);
        btnread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ReadDataActivity().execute();

            }
        });
        btnqrscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrscan.initiateScan();
            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new UpdateDataActivity().execute();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestcode,int resultcode,Intent data)
    {
        IntentResult result=IntentIntegrator.parseActivityResult(requestcode,resultcode,data);
        if(result!=null)
        {
            if(result.getContents()==null)
            {
                Toast.makeText(MainActivity.this, "result not found", Toast.LENGTH_SHORT).show();
            }
            else
            {
                txt.setText(result.getContents());
                id=txt.getText().toString();
            }
        }
        else
        {
            super.onActivityResult(requestcode,resultcode,data);
        }
    }

    //getting the list of quantity of orders from google spreadsheet
    class ReadDataActivity extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dialog;
        int jIndex;
        int x;
       @Override
       protected void onPreExecute()
        {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setTitle("Wait");
            dialog.setMessage("breathe in... out");
            dialog.show();
        }
        @Nullable
        @Override
        protected Void doInBackground(Void...params)
        {
            Log.i(Controller.TAG,"ID");
            JSONObject jsonObject=Controller.readData(id);
            Log.i(Controller.TAG,"json obj");
            try
            {
                if(jsonObject!=null)
                {
                    JSONObject user=jsonObject.getJSONObject("user");
                    name=user.getString("name");
                    dosa=user.getString("dosa");
                    idly=user.getString("idly");
                    pongal=user.getString("pongal");
                    count=user.getString("count");
                    burger=user.getString("burger");
                    pizza=user.getString("pizza");
                    sandwich=user.getString("sandwich");
                }
            }
            catch(JSONException je)
            {
                Log.i(Controller.TAG,je.getLocalizedMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(count!="0")
            {
                txtname.setText(name);
                txtdosa.setText(dosa);
                txtidly.setText(idly);
                txtpongal.setText(pongal);
                txtburger.setText(burger);
                txtpizza.setText(pizza);
                txtsandwich.setText(sandwich);
            }
            else if(count=="0")
            {
                txtname.setText("invalid");
                txtdosa.setText("invalid");
                txtidly.setText("invalid");
                txtpongal.setText("invalid");
                txtburger.setText("invalid");
                txtpizza.setText("invalid");
                txtsandwich.setText("invalid");
            }
            else
            {
                Toast.makeText(getApplicationContext(), "id not found", Toast.LENGTH_SHORT).show();
            }
            btnqrscan.setEnabled(false);
            btnread.setEnabled(false);
            btnupdate.setEnabled(true);
        }

    }

    //updating the order to prevent multiple order collection.
    class UpdateDataActivity extends AsyncTask<Void,Void,Void>
    {
        ProgressDialog dialog;
        int jIndex;
        int x;
        String result=null;
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            dialog=new ProgressDialog(MainActivity.this);
            dialog.setTitle("Wait");
            dialog.setMessage("wait until it updates");
            dialog.show();
        }
        @Nullable
        @Override
        protected Void doInBackground(Void...params)
        {
            JSONObject jsonObject=Controller.updateData(id);
            Log.i(Controller.TAG,"json obj");
            try
            {
                if(jsonObject!=null)
                {
                    result=jsonObject.getString("result");
                }
            }
            catch (JSONException e)
            {
                Log.i(Controller.TAG,"error");
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPreExecute();
            dialog.dismiss();
            btnqrscan.setEnabled(true);
            btnread.setEnabled(true);
            btnupdate.setEnabled(false);
            Toast.makeText(getApplicationContext(),"successful", Toast.LENGTH_SHORT).show();
        }
    }
}
