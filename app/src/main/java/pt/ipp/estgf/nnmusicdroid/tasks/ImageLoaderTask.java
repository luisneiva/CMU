package pt.ipp.estgf.nnmusicdroid.tasks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Created by sio on 01-08-2014.
 */
public class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

    ImageView bmImage;

    public ImageLoaderTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            mIcon11 = BitmapFactory.decodeResource(MainActivity.globalContext.getResources(), R.drawable.no_image);
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
