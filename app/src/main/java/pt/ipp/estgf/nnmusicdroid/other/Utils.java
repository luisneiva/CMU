package pt.ipp.estgf.nnmusicdroid.other;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.widget.TopMusicDataProvider;
import pt.ipp.estgf.nnmusicdroid.widget.WidgetProvider;

public class Utils {

    public static Context getContext() {
        if (MainActivity.globalContext != null) {
            return MainActivity.globalContext;
        } else if (WidgetProvider.globalContext != null) {
            return  WidgetProvider.globalContext;
        } else if (TopMusicDataProvider.globalContext != null) {
            return TopMusicDataProvider.globalContext;
        }

        return null;
    }

    /**
     * Informa se é para usar a localização atual.
     *
     * @return
     */
    public static boolean isToUserCurrentPlace() {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());

        return mSettings.getBoolean("pref_atual_location_key", true);
    }

    /**
     * Obtem o place selecionado como por defeito.
     *
     * @return
     */
    public static int getSelectedDefaultPlace() {
        SharedPreferences mSettings = PreferenceManager.getDefaultSharedPreferences(Utils.getContext());

        return Integer.parseInt(mSettings.getString("pref_default_country_key", "0"));
    }

}
