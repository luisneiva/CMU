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

import pt.ipp.estgf.nnmusicdroid.R;
import pt.ipp.estgf.nnmusicdroid.model.Country;

import java.util.ArrayList;

/**
 * Lição 03, ficheiro: 6_listas_2013_10_11
 * -- um ADAPTER Permite a gestão de grandes conjuntos de
 * dados de uma maneira eficiente e escalável.
 * -- é um objeto que age como um ponte entre um AdapterView
 * (ListView, Spinner) e os dados subjacentes para esse tipo de View.
 * -- O Adapter irá fornecer o acesso às várias linhas de dados.
 */
public class CountryAdapter extends ArrayAdapter<Country> {

    // Lista de todos os paises
    private ArrayList<Country> listOfCountries;
    Context context;

    //Método construtor, slide 10
    public CountryAdapter(Context context, ArrayList<Country> mlist) {
        super(context, android.R.layout.activity_list_item, mlist);

        this.listOfCountries = mlist;
        this.context = context;
    }

    //Método que obtem os paises pela sua posição
    @Override
    public Country getItem(int position) {
        return this.listOfCountries.get(position);
    }

    //Método que obtem o total de paises
    @Override
    public int getCount() {
        return this.listOfCountries.size();
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

        //Obtem o country para a posição atual
        Country country = this.getItem(position);

        // Inicialização da interface
        TextView countryName = (TextView) v.findViewById(android.R.id.text1);
        countryName.setText(country.getName());

        return v;
    }
}
