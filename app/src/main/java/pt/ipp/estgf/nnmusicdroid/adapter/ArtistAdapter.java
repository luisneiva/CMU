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

import pt.ipp.estgf.cmu.musicdroidlib.TopArtist;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Lição 03, ficheiro: 6_listas_2013_10_11
 * -- um ADAPTER Permite a gestão de grandes conjuntos de
 * dados de uma maneira eficiente e escalável.
 * -- é um objeto que age como um ponte entre um AdapterView
 * (ListView, Spinner) e os dados subjacentes para esse tipo de View.
 * -- O Adapter irá fornecer o acesso às várias linhas de dados.
 */
public class ArtistAdapter extends ArrayAdapter<TopArtist> {

    //Lista de artistas que estão no TOP
    private ArrayList<TopArtist> listOfTopArtists;
    Context context;

    //Método construtor, slide 10
    public ArtistAdapter(Context context, ArrayList<TopArtist> mlist) {
        super(context, android.R.layout.activity_list_item);

        this.listOfTopArtists = mlist;
        this.context = context;
    }

    //Método que obtem os artistas pela sua posição
    @Override
    public TopArtist getItem(int id){
        return  this.listOfTopArtists.get(id);
    }

    //Método que obtem o total de artistas
    @Override
    public int getCount(){
        return this.listOfTopArtists.size();
    }

    /**
     @Override
     public long getItemId(int position) {
     return position;
     }
     */


    //Método que obtem a view, slide 11
    @Override
    public View getView(int id, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null){
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.artist_list_item, null);
        }
        
        //Obtem o topArtist para a posição atual
        TopArtist topArtist = this.getItem(id);

        //Inicialização da interface
        TextView artistName = (TextView) v.findViewById(android.R.id.text1);
        artistName.setText(topArtist.getArtistName());

        return v;
    }
}
