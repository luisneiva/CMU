package pt.ipp.estgf.nnmusicdroid;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import pt.ipp.estgf.cmu.musicdroidlib.Place;


/**
 * Implementation of App Widget functionality.
 */
public class widget_layout extends AppWidgetProvider {

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;

        for (int i=0; i<N; i++) {

            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
            int appWidgetId) {

        // Obtem o place atual
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        LocationUtils locationUtils = new LocationUtils(context);
        Place place = locationUtils.getCurrentPlace();

        if (place != null) {
            views.setTextViewText(R.id.appwidget_text, place.getName());
        } else {
            Toast.makeText(context, "Não foi possivel obter o place.", Toast.LENGTH_LONG).show();
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


