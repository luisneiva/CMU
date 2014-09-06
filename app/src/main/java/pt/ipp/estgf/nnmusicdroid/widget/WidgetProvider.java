package pt.ipp.estgf.nnmusicdroid.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.UUID;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.LocationUtils;
import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.MusicListActivity;
import pt.ipp.estgf.nnmusicdroid.R;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;
import pt.ipp.estgf.nnmusicdroid.model.MyPlace;
import pt.ipp.estgf.nnmusicdroid.other.Utils;

/**
 * Notifica todos os widgets quando deteta mudanças.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class WidgetDataProviderObserver extends ContentObserver {

    private AppWidgetManager mAppWidgetManager;
    private ComponentName mComponentName;

    WidgetDataProviderObserver(AppWidgetManager mgr, ComponentName cn, Handler h) {
        super(h);
        mAppWidgetManager = mgr;
        mComponentName = cn;
    }

    @Override
    public void onChange(boolean selfChange) {
        mAppWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetManager.getAppWidgetIds(mComponentName), R.id.top_music_list);
    }
}

/**
 * Provider para Widget
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class WidgetProvider extends AppWidgetProvider {

    public static Context globalContext;

    public static String CLICK_ACTION = "pt.ipp.estgf.nnmusicdroid.listwidget.CLICK";
    public static String REFRESH_ACTION = "pt.ipp.estgf.nnmusicdroid.widget.REFRESH";
    public static String EXTRA_PLACE_ID = "pt.ipp.estgf.nnmusicdroid.widget.place";

    private static HandlerThread sWorkerThread;
    private static Handler sWorkerQueue;
    private static WidgetDataProviderObserver sDataObserver;

    public WidgetProvider() {
        // start the worker thread
        sWorkerThread = new HandlerThread("WidgetProvider-worker");
        sWorkerThread.start();
        sWorkerQueue = new Handler(sWorkerThread.getLooper());
    }

    @Override
    public void onEnabled(Context context) {
        Log.d("WidgetProvider", "onEnabled");
        final ContentResolver r = context.getContentResolver();

        // Disponibiliza o contexto
        globalContext = context;

        if (sDataObserver == null) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, WidgetProvider.class);
            sDataObserver = new WidgetDataProviderObserver(mgr, cn, sWorkerQueue);
            r.registerContentObserver(TopMusicDataProvider.CONTENT_URI, true, sDataObserver);
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.d("WidgetProvider", "onReceive");
        final String action = intent.getAction();

        if (action.equals(REFRESH_ACTION)) {
            sWorkerQueue.removeMessages(0);
            sWorkerQueue.post(new Runnable() {
                @Override
                public void run() {
                    final ContentResolver r = context.getContentResolver();
                    final Cursor c = r.query(TopMusicDataProvider.CONTENT_URI, null, null, null, null);
                    final int count = c.getCount();

                    r.unregisterContentObserver(sDataObserver);
                    for (int i = 0; i < count; i++) {
                        final Uri uri = ContentUris.withAppendedId(TopMusicDataProvider.CONTENT_URI, i);
                        final ContentValues values = new ContentValues();
                        values.put("Name", "---");
                        r.update(uri, values, null, null);
                    }

                    r.registerContentObserver(TopMusicDataProvider.CONTENT_URI, true, sDataObserver);

                    final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                    final ComponentName cn = new ComponentName(context, TopMusicDataProvider.class);
                    mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.top_music_list);
                }
            });
        } else if (action.equals(CLICK_ACTION)) {
            final int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            final String place = intent.getStringExtra(EXTRA_PLACE_ID);
            Toast.makeText(context, "ALTERAR_TEXTO", Toast.LENGTH_SHORT).show();
        }

        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent intent = new Intent(context, WidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Obtem o RemoteViews
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // RemoteView para a lista
            rv.setRemoteAdapter(appWidgetIds[i], R.id.top_music_list, intent);
            rv.setEmptyView(R.id.top_music_list, android.R.id.empty);

            // Coloca o nome do place
            Place place = null;
            if (Utils.isToUserCurrentPlace()) {
                // Obtem o Place correspondente à localização atual
                LocationUtils locationUtils = new LocationUtils(context);
                place = locationUtils.getCurrentPlace();
            } else {
                MyDbAccess db = new MyDbAccess(Utils.getContext());
                place = Place.get(Utils.getSelectedDefaultPlace(), db.getReadableDatabase());
                db.close();
            }

            rv.setTextViewText(R.id.appwidget_text, place.getName());

            // Ao clicar no nome do pais abre a aplicação
            Intent intentOpenMain = new Intent(context, MusicListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intentOpenMain.setData(Uri.parse(intentOpenMain.toUri(Intent.URI_INTENT_SCHEME)));
            intentOpenMain.putExtra("id", place.getId());
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentOpenMain, 0);
            rv.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
