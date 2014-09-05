package pt.ipp.estgf.nnmusicdroid.adapter;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

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
 * Lição 03, ficheiro: 6_listas_2013_10_11
 * -- um ADAPTER Permite a gestão de grandes conjuntos de
 * dados de uma maneira eficiente e escalável.
 * -- é um objeto que age como um ponte entre um AdapterView
 * (ListView, Spinner) e os dados subjacentes para esse tipo de View.
 * -- O Adapter irá fornecer o acesso às várias linhas de dados.
 */
public class TrackAdapter extends ArrayAdapter<TopTrack> {

    //Lista de musicas que estão no TOP
    private ArrayList<TopTrack> listOfTopTracks;
    Context context;

    //Método construtor, slide 10
    public TrackAdapter(Context context, ArrayList<TopTrack> mlist) {
        super(context, android.R.layout.activity_list_item);

        this.listOfTopTracks = mlist;
        this.context = context;
    }

    //Método que obtem os músicas pela sua posição
    @Override
    public TopTrack getItem(int id){
        return  this.listOfTopTracks.get(id);
    }

    //Método que obtem o total de musicas
    @Override
    public int getCount(){
        return this.listOfTopTracks.size();
    }

    //Método que obtem a view, slide 11
    @Override
    public View getView(int id, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.track_list_item, null);
        }

        //Obtem o topTrack para a posição atual
        TopTrack topTrack = this.getItem(id);

        //Inicialização da interface
        TextView trackName = (TextView) v.findViewById(android.R.id.text1);
        trackName.setText(topTrack.getName());

        return v;
    }
}
