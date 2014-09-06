package pt.ipp.estgf.nnmusicdroid.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import pt.ipp.estgf.nnmusicdroid.MusicActivity;
import pt.ipp.estgf.nnmusicdroid.MusicDetails;
import pt.ipp.estgf.nnmusicdroid.R;
import pt.ipp.estgf.nnmusicdroid.other.Utils;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    private int mAppWidgetId;

    public StackRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        Log.d("StackRemoteViewsFactory", "onDestroy");

        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        String musicName = "---";
        String artistName = "";

        if (mCursor.moveToPosition(position)) {
            musicName = mCursor.getString(0);
            artistName = mCursor.getString(1);
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        rv.setTextViewText(R.id.widget_item, musicName);

        // Cria um intent para lançar a Activity com os detalhes
        /*Intent intent = new Intent(Utils.getContext(), MusicDetails.class);
        intent.putExtra("ArtistName", artistName);
        intent.putExtra("TrackName", musicName);*/
        Bundle extras = new Bundle();
        extras.putString("ArtistName", artistName);
        extras.putString("TrackName", musicName);
        Intent intent = new Intent();
        intent.putExtras(extras);

        // Associa o intent a linha
        rv.setOnClickFillInIntent(R.id.widget_item, intent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {
        Log.d("StackRemoteViewsFactory", "onDataSetChanged");
        Thread thread = new Thread() {
            @Override
            public void run() {
                // Refresh the cursor
                if (mCursor != null) {
                    mCursor.close();
                }

                mCursor = mContext.getContentResolver().query(TopMusicDataProvider.CONTENT_URI, null, null, null, null);
            }
        };
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
