package pt.ipp.estgf.nnmusicdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.TopTrack;
import pt.ipp.estgf.cmu.musicdroidlib.Track;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Created by luisteixeira on 31/07/14.
 */
public class TrackAdapter extends ArrayAdapter<TopTrack> {

    //Lista com as musicas que estão no TOP
    private ArrayList<TopTrack> listOfTopTracks;
    Context context;

    public TrackAdapter(Context context, ArrayList<TopTrack> mlist) {
        super(context, android.R.layout.activity_list_item);

        this.listOfTopTracks = mlist;
        this.context = context;
    }

    /**
     * Obter as musicas pela posição
     */

    @Override
    public TopTrack getItem(int id){
        return  this.listOfTopTracks.get(id);
    }

    /**
     * Obter o totla de musicas
     */
    @Override
    public int getCount(){
        return this.listOfTopTracks.size();
    }

    /**
     * Obtém a view
     */
    @Override
    public View getView(int id, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.track_list_item, null);
        }
        //Obtem a musica para a posição
        TopTrack topTrack = this.getItem(id);

        //Inicialização da interface
        TextView trackName = (TextView) v.findViewById(android.R.id.text1);
        trackName.setText(topTrack.getName());

        return v;
    }
}
