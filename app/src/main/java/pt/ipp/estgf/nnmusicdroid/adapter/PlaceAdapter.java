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

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.R;

/**
 * Lição 03, ficheiro: 6_listas_2013_10_11
 * -- um ADAPTER Permite a gestão de grandes conjuntos de
 * dados de uma maneira eficiente e escalável.
 * -- é um objeto que age como um ponte entre um AdapterView
 * (ListView, Spinner) e os dados subjacentes para esse tipo de View.
 * -- O Adapter irá fornecer o acesso às várias linhas de dados.
 */
public class PlaceAdapter extends ArrayAdapter<Place> {

    // Lista de paises que estão nos "meus paises"
    private ArrayList<Place> listOfPlaces;
    Context context;

    //Método construtor, slide 10
    public PlaceAdapter(Context context, ArrayList<Place> mlist) {
        super(context, android.R.layout.activity_list_item, mlist);

        this.listOfPlaces = mlist;
        this.context = context;
    }

    //Método que obtem um país pela sua posição
    @Override
    public Place getItem(int position) {
        return this.listOfPlaces.get(position);
    }

    //Método que obtem o total de paises
    @Override
    public int getCount() {
        return this.listOfPlaces.size();
    }

    /**
     @Override
     public long getItemId(int position) {
     return position;
     }
     */


    //Método que obtem a view, slide 11
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.country_list_item, null);

        }

        //Obtem o place para a posição atual
        Place place = this.getItem(position);

        // Inicialização da interface
        TextView countryName = (TextView) v.findViewById(android.R.id.text1);
        countryName.setText(place.getName());

        return v;
    }
}
