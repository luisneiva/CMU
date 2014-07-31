package pt.ipp.estgf.cmu.musicdroidlib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String TAG = "MUSICDROID_DATABASE";

	private static final String DATABASE_NAME = "musicdroid.db";
	private static final int DATABASE_VERSION = 1;

	private final Context mContext;

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext = context;
	}

	// executa um script externo com instruções SQL
	protected void execSQLScript(int fileRes, SQLiteDatabase _db) {
		ScriptFileReader sfr = new ScriptFileReader(mContext, fileRes);
		sfr.open();

		String str = sfr.nextLine();
		while (str != null) {
			if (str.trim().length() > 0 && !str.startsWith("--"))
				_db.execSQL(str);
			str = sfr.nextLine();
		}

		sfr.close();
		sfr = null;
	}

	@Override
	public void onCreate(SQLiteDatabase _db) {
		Log.w(TAG, "Creating Database...");
		execSQLScript(R.raw.tbl_creates, _db);
		init(_db);
	}

	protected void init(SQLiteDatabase _db) {
		Log.w(TAG, "Initializing database...");
		execSQLScript(R.raw.tbl_init, _db);
	}

	protected void dropAll(SQLiteDatabase _db) {
		Log.w(TAG, "Droping Database...");
		execSQLScript(R.raw.tbl_drops, _db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		Log.w(TAG, "Upgrading from version " + _oldVersion + " to "
				+ _newVersion + ", which will destroy all old data.");

		dropAll(_db);
		onCreate(_db);
	}
	
	@SuppressWarnings("resource")
	public static boolean backupDatabase(Context mContext) {
		try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	            String currentDBPath = "//data//" + mContext.getPackageName() + "//databases//" + DATABASE_NAME;
	            String backupDBPath = DATABASE_NAME;
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(sd, backupDBPath);

	            if (currentDB.exists()) {
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	                
	                return true;
	            }
	        }
	    } catch (Exception e) {
	    	Log.e(TAG, "Error backing up database.", e);
	    }
		
		return false;
	}	
}
