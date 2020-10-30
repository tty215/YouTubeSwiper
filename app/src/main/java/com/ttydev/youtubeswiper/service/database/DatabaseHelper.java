package com.ttydev.youtubeswiper.service.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.ttydev.youtubeswiper.entities.Channel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "YOUTUBE_SWIPER";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "FAVORITE_CHANNEL";

    private static DatabaseHelper singleton = null;

    private static SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (singleton == null) {
            singleton = new DatabaseHelper(context);
            db = singleton.getWritableDatabase();
        }
        return singleton;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE "+ TABLE_NAME + " (");
        sb.append("channelId CHAR(24) PRIMARY KEY, ");
        sb.append("title TEXT NOT NULL, ");
        sb.append("description TEXT, ");
        sb.append("thumbnail TEXT, ");
        sb.append("publishedAt DATETIME");
        sb.append(");");
        String sql = sb.toString();

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}

    public List<Channel> getFavoriteChannels() {
        String sql = "SELECT * FROM " + TABLE_NAME + ";";

        List<Channel> channels = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        try {
            while (cursor.moveToNext()) {
                Channel channel = new Channel(
                    cursor.getString(cursor.getColumnIndex("channelId")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("thumbnail")),
                    cursor.getString(cursor.getColumnIndex("publishedAt"))
                );
                channels.add(channel);
            }
            return channels;
        }
        catch (Error e) {
            return channels;
        }
    }

    public void createChannel(Channel channel) {
        ContentValues values = new ContentValues();
        values.put("channelId", channel.getChannelId());
        values.put("title", channel.getTitle());
        values.put("description", channel.getDescription());
        values.put("thumbnail", channel.getThumbnail());
        values.put("publishedAt", channel.getPublishedAt().toString());

        db.insert(TABLE_NAME, null, values);
    }

    public void deleteChannel(String channelId) {
        db.delete(TABLE_NAME, "channelId = ?", new String[]{channelId});
    }

    public boolean isFavoriteChannel(String channelId) {
        String sql = "SELECT channelId FROM " + TABLE_NAME + " WHERE channelId = \"" + channelId + "\";";

        try {
            Cursor cursor = db.rawQuery(sql, null);

            if (cursor.moveToFirst()) {
                int idxChannelId = cursor.getColumnIndex("channelId");
                String id = cursor.getString(idxChannelId);

                if (id.isEmpty()) return false;
                else return true;
            }
            return false;
        }
        catch (Error e) {
            return false;
        }
    }
}
