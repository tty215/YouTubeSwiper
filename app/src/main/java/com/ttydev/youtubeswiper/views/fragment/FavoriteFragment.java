package com.ttydev.youtubeswiper.views.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.ttydev.youtubeswiper.R;
import com.ttydev.youtubeswiper.entities.Channel;
import com.ttydev.youtubeswiper.service.database.DatabaseHelper;
import com.ttydev.youtubeswiper.views.view.ChannelListAdapter;
import com.ttydev.youtubeswiper.views.view.ChannelSearchDialog;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private FragmentManager _fragmentManager;
    private Activity _parentActivity;
    private View _fab;
    private ListView _listView;
    private List<Channel> _channels;

    private DatabaseHelper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _parentActivity = getActivity();
        _fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        /// お気に入りのリストの設定
        if (helper == null) {
            helper = DatabaseHelper.getInstance(_parentActivity.getApplicationContext());
        }

        // お気に入り一覧の取得
        _channels = helper.getFavoriteChannels();

        _listView = view.findViewById(R.id.favoriteChannelList);
        ChannelListAdapter adapter = new ChannelListAdapter(_parentActivity, R.layout.view_channellist, _channels);
        _listView.setAdapter(adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (view.getId() == R.id.channel_favorite_button) {
                    ChannelListAdapter adapter = (ChannelListAdapter)_listView.getAdapter();

                    if (helper.isFavoriteChannel(_channels.get(i).getChannelId())) {
                        helper.deleteChannel(_channels.get(i).getChannelId());
                        _channels.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });

        /// fabの設定
        _fab = view.findViewById(R.id.fab_search_channel);
        _fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ChannelSearchDialog(_parentActivity, _listView).onCreateDialog(null).show();
            }
        });

        return view;
    }
}