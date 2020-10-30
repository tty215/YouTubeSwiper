package com.ttydev.youtubeswiper.views.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.ttydev.youtubeswiper.R;
import com.ttydev.youtubeswiper.entities.Channel;
import com.ttydev.youtubeswiper.service.database.DatabaseHelper;

import java.util.Arrays;
import java.util.List;

public class ChannelListAdapter extends ArrayAdapter<Channel> {

    private DatabaseHelper helper;
    private int resource;
    private List<Channel> items;
    private LayoutInflater inflater;

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public ChannelListAdapter(Context context, int resource, List<Channel> items) {
        super(context, resource, items);

        this.resource = resource;
        this.items = items;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = inflater.inflate(resource, null);
        }

        // リストビューに表示する要素を取得
        Channel item = items.get(position);

        // サムネイル画像を設定
        ImageView thumbnailView = (ImageView)view.findViewById(R.id.channel_thumbnail);
        Picasso.get().load(item.getThumbnail()).placeholder(R.drawable.loading3).into(thumbnailView);

        // チャンネル名を設定
        TextView title = (TextView)view.findViewById(R.id.channel_title);
        title.setText(item.getTitle());

        // チャンネルの説明を設定
        TextView description = (TextView)view.findViewById(R.id.channel_description);
        description.setText(item.getDescription());

        // お気に入りボタンを設定
        ImageButton favorite = (ImageButton)view.findViewById(R.id.channel_favorite_button);

        if (helper == null) {
            helper = DatabaseHelper.getInstance(getContext());
        }

        if (DatabaseHelper.getInstance(getContext()).isFavoriteChannel(item.getChannelId())) {
            favorite.setImageResource(R.drawable.ic_star_on);
        } else {
            favorite.setImageResource(R.drawable.ic_star_off);
        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, R.id.channel_favorite_button);
            }
        });

        return view;
    }

    @Override
    public void remove(@Nullable Channel object) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getChannelId().equals(object.getChannelId())) {
                super.remove(items.get(i));
            }
        }
    }
}