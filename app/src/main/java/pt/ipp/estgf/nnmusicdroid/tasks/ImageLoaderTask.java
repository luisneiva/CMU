package pt.ipp.estgf.nnmusicdroid.tasks;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Lição 07, ficheiro: 16_asynctask_threads_2013_11_08
 *
 * -- Em Android, existem pelo menos duas formas de lançarmos
 * novas linhas de execução paralelas (Threads)
 *     Classe AsyncTask (abstração que faz parte da API Android)
 *     Classe Thread (tradicionalmente utilizada em Java)
 * -- Na AsyncTask o código que executa na nova linha de execução
 * está no método doInBackground()
 *
 * slide 10
 *
 * Esta task é para carregar as imagens dos artistas e dos albuns
 * a partir da API.
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
