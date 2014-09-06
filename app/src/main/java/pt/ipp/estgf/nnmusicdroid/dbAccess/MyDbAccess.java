package pt.ipp.estgf.nnmusicdroid.dbAccess;

/**
 * Created by Luis Teixeira & Nuno Nunes
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import pt.ipp.estgf.cmu.musicdroidlib.DatabaseHelper;
import pt.ipp.estgf.cmu.musicdroidlib.ScriptFileReader;
import pt.ipp.estgf.nnmusicdroid.MainActivity;
import pt.ipp.estgf.nnmusicdroid.R;
import pt.ipp.estgf.nnmusicdroid.other.Utils;


/**
 * Lição 04, ficheiro: 7_base_dados_2013_10_13
 *
 * -- O Android fornece, nativamente, suporte para base de dados SQLite.
 * As base de dados apenas podem ser acedidas pela aplicação que a cria,
 * nunca por aplicações terceiras
 * -- Para criar/gerir a base de dados deve-se criar uma classe que deve
 * ser subclasse do SQLiteOpenHelper. Deve-se também fazer override aos
 * métodos onCreate() e onUpgrade()
 */
public class MyDbAccess extends DatabaseHelper {

    //Versão da base de dados, slide 4
    private static final int DATABASE_VERSION = 1;

    //Método construtor
    public MyDbAccess() {
        super(MainActivity.globalContext);
    }

    public MyDbAccess(Context context) {
        super(context);
    }

    //Método onCreate, slide 5
    @Override
    public void onCreate(SQLiteDatabase _db) {
        super.onCreate(_db);

        // Cria a tabela country e inicializa a base de dados
        execSQLScript(R.raw.tbl_country, _db);
        initCountry(_db);
    }

    //Inicializa a table country com dados.
    private void initCountry(SQLiteDatabase _db) {
        execSQLScript(R.raw.tbl_init_country, _db);
    }


    //Faz o drop das tabelas.
    @Override
    protected void dropAll(SQLiteDatabase _db) {
        super.dropAll(_db);
        execSQLScript(R.raw.tbl_drop_country, _db);
    }

    /**
     * Executa um script externo com instruções SQL.
     *
     * Esta é uma implementação baseada na do prof. mas
     * com SUPORTE A MULTIPLAS LINHAS.
     *
     * @param fileRes
     * @param _db
     */
    @Override
    protected void execSQLScript(int fileRes, SQLiteDatabase _db) {
        ScriptFileReader sfr = new ScriptFileReader(Utils.getContext(), fileRes);
        sfr.open();

        // Cria um novo StringBuilder
        StringBuilder strB = new StringBuilder();

        String str = sfr.nextLine();
        while (str != null) {
            // Ignora linhas em branco e comentários
            if (str.trim().length() > 0 && !str.startsWith("--")) {
                // Retira os espaços no inicio e fim da linha
                str = str.trim();

                // FAz o append da string ao string builder
                strB.append(str);

                // Verifica se é o final do comando, tem que ter um ";" no fim
                if (str.lastIndexOf(';') == (str.length() - 1)) {
                    _db.execSQL(strB.toString());
                    strB.setLength(0);
                }
            }

            // Proxima linha
            str = sfr.nextLine();
        }

        // Fecha a ligação ao ficheiro
        sfr.close();
        sfr = null;
    }
}
