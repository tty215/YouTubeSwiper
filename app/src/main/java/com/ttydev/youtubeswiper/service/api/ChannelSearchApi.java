package com.ttydev.youtubeswiper.service.api;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//public class ChannelSearchApi extends AsyncTask<String, String, String> {
//    @Override
//    protected String doInBackground(String... params) {
//        String word = params[0];
//        String urlStr = "https://www.googleapis.com/youtube/v3/channels?key=AIzaSyDNtlUSFUNQI1mMeGPMsC6IwibTmnnYKDA&part=snippet&type=channel&q=" + word;
//        String result = "";
//
//        // API呼び出し
//        HttpURLConnection con = null;
//        InputStream is = null;
//
//        try {
//            URL url = new URL(urlStr);
//            con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//            con.connect();
//            is = con.getInputStream();
//
//            result = is2String(is);
//        }
//        catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally {
//            if (con != null) {
//                con.disconnect();
//            }
//            if (is != null) {
//                try {
//                    is.close();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        return result;
//    }
//
//    private String is2String(InputStream is) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        br.close();
//        return sb.toString();
//    }
//}
