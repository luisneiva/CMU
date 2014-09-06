package pt.ipp.estgf.nnmusicdroid.other;

import android.content.Context;

import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.widget.TopMusicDataProvider;
import pt.ipp.estgf.nnmusicdroid.widget.WidgetProvider;

/**
 * Created by gil0mendes on 06/09/14.
 */
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

}
