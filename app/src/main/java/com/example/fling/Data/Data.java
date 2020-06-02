package com.example.fling.Data;

import android.content.Context;

import com.example.fling.Helper.Connection;
import com.example.fling.Helper.Helper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

public class Data
{
    public void insertData(Context context, String filename, String data)
    {
        try
        {
            HttpURLConnection conn= Connection.createConnection(filename);
            OutputStream outputStream=conn.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            InputStream inputStream=conn.getInputStream();
            conn.disconnect();
            Helper.createLog("DATA","data Insert");

        }
        catch (Exception e)
        {
          Helper.createLog("DATA","DATA"+e.toString());
        }
    }

    public void updateData(Context context, String filename, String data)
    {
        try
        {
            HttpURLConnection conn=Connection.createConnection(filename);
            OutputStream outputStream=conn.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            InputStream inputStream=conn.getInputStream();
            conn.disconnect();
            Helper.createLog("DATA","data Insert");

        }
        catch (Exception e)
        {
            Helper.createLog("DATA",e.toString());
        }
    }
    public void deleteData (Context context, String filename, String data)
    {
        try
        {
            HttpURLConnection conn=Connection.createConnection(filename);
            OutputStream outputStream=conn.getOutputStream();
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(outputStream,"UTF-8");
            BufferedWriter bufferedWriter=new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            InputStream inputStream=conn.getInputStream();
            conn.disconnect();
            Helper.createLog("DATA","data Insert");

        }
        catch (Exception e)
        {
            Helper.createLog("DATA",e.toString());
        }
    }

    public String selectData(Context context, String filename, String data) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        try {
            HttpURLConnection conn = Connection.createConnection(filename);
            OutputStream outputStream = conn.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(data);
            bufferedWriter.close();
            outputStreamWriter.close();
            outputStream.close();
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

        } catch (Exception e) {

        }
        return stringBuilder.toString();

    }
    public String selectAll(Context context, String filename)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        try {
            HttpURLConnection conn = Connection.createConnection(filename);
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

        } catch (Exception e) {

        }
        return stringBuilder.toString();

    }
}
