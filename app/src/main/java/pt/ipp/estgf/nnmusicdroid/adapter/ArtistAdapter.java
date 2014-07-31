package pt.ipp.estgf.nnmusicdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Created by luisteixeira on 31/07/14.
 */
public class ArtistAdapter extends ArrayAdapter<TopArtist> {

    //Lista com os artistas que estão no TOP
    private ArrayList<TopArtist> listOfTopArtists;
    Context context;

    public ArtistAdapter(Context context, ArrayList<TopArtist> mlist) {
        super(context, android.R.layout.activity_list_item);

        this.listOfTopArtists = mlist;
        this.context = context;
    }

    /**
     * Obter os artistas pela posição
     */

    @Override
    public TopArtist getItem(int id){
        return  this.listOfTopArtists.get(id);
    }

    /**
     * Obter o total de artistas
     */
    @Override
    public int getCount(){
        return this.listOfTopArtists.size();
    }

    /**
     * Obtém a view
     */
    @Override
    public View getView(int id, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.artist_list_item, null);
        }
        //Obtem o artista para a posição
        TopArtist topArtist = this.getItem(id);

        //Inicialização da interface
        TextView artistName = (TextView) v.findViewById(android.R.id.text1);
        artistName.setText(topArtist.getArtistName());

        return v;
    }
}
