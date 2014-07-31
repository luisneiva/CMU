package pt.ipp.estgf.nnmusicdroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ipp.estgf.cmu.musicdroidlib.Place;
import pt.ipp.estgf.nnmusicdroid.R;

public class PlaceAdapter extends ArrayAdapter<Place> {

    // Lista com todos os paises
    private ArrayList<Place> listOfPlaces;
    Context context;

    public PlaceAdapter(Context context, ArrayList<Place> mlist) {
        super(context, android.R.layout.activity_list_item, mlist);

        this.listOfPlaces = mlist;
        this.context = context;
    }

    /**
     * Obtem um pais pela posição.
     *
     * @param position
     * @return
     */
    @Override
    public Place getItem(int position) {
        return this.listOfPlaces.get(position);
    }

    /**
     * Obtém o total de paises.
     *
     * @return
     */
    @Override
    public int getCount() {
        return this.listOfPlaces.size();
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

        // Obtem o place para a posição atual
        Place place = this.getItem(position);

        // Inicialização da interface
        TextView countryName = (TextView) v.findViewById(android.R.id.text1);
        countryName.setText(place.getName());

        return v;
    }
}
