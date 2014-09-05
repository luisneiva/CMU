package pt.ipp.estgf.nnmusicdroid.model;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.nnmusicdroid.dbAccess.MyDbAccess;

/**
 * Model para o Country
 */
public class Country {

    // --- CAMPOS BASE DE DADOS
    public final static String TBL_NAME = "countries";
    public final static String NAME = "langEN";
    public final static String ALPHA_3 = "alpha3";

    // ---
    private static SQLiteDatabase db;
    private static DatabaseHelper mydbHelper;

    // ---
    private String alpha3;
    private String name;

    public Country() {
    }

    public Country( String alpha3, String name) {
        this.name = name;
        this.alpha3 = alpha3;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // --------------------------------------------------------------------------------------------

    /**
     * Lição 04, ficheiro: 7_base_dados_2013_10_13
     *
     * Obtem a lista de paises a partir da base de dados.
     *
     * @param list
     */
    public static List<Country> getAll(List<Country> list) {
        list = (list != null) ? list : new ArrayList<Country>();

        // Apaga os items da lista
        list.clear();

        // Abre a coneção com o SQL (somente leitura(getReadableDatabase))
        mydbHelper = new MyDbAccess();
        db = mydbHelper.getReadableDatabase();

        Country country = null;

        //Pesquisa SQL, slide 15
        String query = "SELECT * FROM " + TBL_NAME + " ORDER BY " + NAME;

        // Cria um cursor, slide 15
        Cursor c = db.rawQuery(query, null);

        // Percorre todos os resultados
        if(c != null && c.moveToFirst()){
            do {
                // Cria um novo modelo
                country = new Country(c.getString(2), c.getString(5));

                // Adiciona o modelo à lista
                list.add(country);
            } while (c.moveToNext());
        }

        return list;
    }

}
