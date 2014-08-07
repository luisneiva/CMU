package pt.ipp.estgf.nnmusicdroid;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.security.Signature;

public class MainActivity extends ActionBarActivity {

    public static Context globalContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Torna o contexto disponivel para toda a aplicação
        globalContext = this.getApplicationContext();

        //Obter o id do xml da lista de musicas
        Button button_Musics = (Button) findViewById(R.id.button_Music);

        //Adicionar o listner ao botão musicas
        button_Musics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abre a activity Music
                Intent intent = new Intent(MainActivity.this, MyCountriesActivity.class);

                intent.putExtra("id", "musica");

                // Lança a Activity
                MainActivity.this.startActivity(intent);

            }
        });

        //obter o id do xml da lista de artistas
        Button button_Artist = (Button) findViewById(R.id.button_Artist);

        //Adicionar o Listner ao botão artistas
        button_Artist.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View view){
                //abre a activity artist
                Intent intent = new Intent(MainActivity.this, MyCountriesActivity.class);
                intent.putExtra("id", "artista");
                startActivity(intent);
            }
        });

        //obter o id do xml da ativity do mapa
        Button button_Map = (Button) findViewById(R.id.button_Map);

        //Adicionar o Listner ao botão mapa
        button_Map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //abre a activity artist
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        //obter o id do xml da lista de Paises
        Button button_Country = (Button) findViewById(R.id.button_Country);

        //Adicionar o Listner ao botão Paises
        button_Country.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //abre a activity lista de paises escolhidos
                Intent intent = new Intent(MainActivity.this, CountryListActivity.class);
                startActivity(intent);
            }
        });

        //obter o id do xml da activity sobre
        Button button_About = (Button) findViewById(R.id.button_About);

        //Adicionar o Listner ao botão Sobre
        button_About.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //abre a activity Sobre
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, PreferencesActivity.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
