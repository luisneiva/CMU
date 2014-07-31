package pt.ipp.estgf.nnmusicdroid.adapter;

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
 *
 */
public class CountryAdapter extends ArrayAdapter<Country> {

    // Lista com todos os paises
    private ArrayList<Country> listOfCountries;
    Context context;

    public CountryAdapter(Context context, ArrayList<Country> mlist) {
        super(context, android.R.layout.activity_list_item, mlist);

        this.listOfCountries = mlist;
        this.context = context;
    }

    /**
     * Obtem um pais pela posição.
     *
     * @param position
     * @return
     */
    @Override
    public Country getItem(int position) {
        return this.listOfCountries.get(position);
    }

    /**
     * Obtém o total de paises.
     *
     * @return
     */
    @Override
    public int getCount() {
        return this.listOfCountries.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    /**
     * Otem a view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.country_list_item, null);

        }

        // Obtem o country para a posição atual
        Country country = this.getItem(position);

        // Inicialização da interface
        TextView countryName = (TextView) v.findViewById(android.R.id.text1);
        countryName.setText(country.getName());

        return v;
    }
}
