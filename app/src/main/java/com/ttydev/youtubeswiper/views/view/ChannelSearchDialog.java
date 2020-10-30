package com.ttydev.youtubeswiper.views.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ttydev.youtubeswiper.R;
import com.ttydev.youtubeswiper.entities.Channel;
import com.ttydev.youtubeswiper.service.database.DatabaseHelper;
import com.ttydev.youtubeswiper.views.fragment.FavoriteFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ChannelSearchDialog extends DialogFragment {
    private Activity _parentActivity;
    private EditText _teSearchChannel;
    private Button _btSearchChannel;
    private ListView _listView;
    private ListView _parentListView;
    private List<Channel> _channels;

    private DatabaseHelper helper;

    public ChannelSearchDialog(Activity parentActivity, ListView listView) {
        this._parentActivity = parentActivity;
        this._parentListView = listView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new Dialog(_parentActivity);

        // タイトル非表示
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.channel_search_dialog);

        // 検索UIの設定
        _listView = dialog.findViewById(R.id.searchedChannelList);
        _teSearchChannel = dialog.findViewById(R.id.teSearchChannel);
        _btSearchChannel = dialog.findViewById(R.id.btSearchChannel);
        _btSearchChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String searchWord = _teSearchChannel.getText().toString();

            if (searchWord.isEmpty()) {
                return;
            }
            else {
                ChannelSearchDialog.ChannelSearchApi channelSearchApi = new ChannelSearchDialog.ChannelSearchApi();
                channelSearchApi.execute(searchWord);

                InputMethodManager inputMethodManager = (InputMethodManager)_parentActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            }
        });

        return dialog;
    }


    private class ChannelSearchApi extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String word = params[0];
            String urlStr = "https://www.googleapis.com/youtube/v3/search?key=AIzaSyDNtlUSFUNQI1mMeGPMsC6IwibTmnnYKDA&part=snippet&type=channel&q=" + word;
            String result = "";

            // API呼び出し
            HttpURLConnection con = null;
            InputStream is = null;

            try {
                URL url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();

                result = is2String(is);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (con != null) {
                    con.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // json解析
            JSONArray items;
            try {
                JSONObject rootJSON = new JSONObject(result);
                items = rootJSON.getJSONArray("items");

                for (int i = 0; i < items.length(); i++) {
                    if (i == 0) _channels = new ArrayList(5);

                    JSONObject item = items.getJSONObject(i);
                    JSONObject snippet = item.getJSONObject("snippet");
                    _channels.add(i, new Channel(
                            snippet.getString("channelId"),
                            snippet.getString("title"),
                            snippet.getString("description"),
                            snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url"),
                            snippet.getString("publishedAt")
                    ));
                }

                ChannelListAdapter adapter = new ChannelListAdapter(_parentActivity, R.layout.view_channellist, _channels);
                _listView.setAdapter(adapter);
                _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        if (view.getId() == R.id.channel_favorite_button) {
                            if (helper == null) {
                                helper = DatabaseHelper.getInstance(_parentActivity.getApplicationContext());
                            }

                            ChannelListAdapter adapter = (ChannelListAdapter)_listView.getAdapter();
                            ChannelListAdapter parentAdapter = (ChannelListAdapter)_parentListView.getAdapter();

                            if (helper.isFavoriteChannel(_channels.get(i).getChannelId())) {
                                helper.deleteChannel(_channels.get(i).getChannelId());
                                parentAdapter.remove(_channels.get(i));
                            }
                            else {
                                helper.createChannel(_channels.get(i));
                                adapter.notifyDataSetChanged();
                                parentAdapter.add(_channels.get(i));
                            }

                            adapter.notifyDataSetChanged();
                            parentAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private String is2String(InputStream is) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }
    }
}
