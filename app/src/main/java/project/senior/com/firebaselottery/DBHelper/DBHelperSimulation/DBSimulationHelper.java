package project.senior.com.firebaselottery.DBHelper.DBHelperSimulation;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import project.senior.com.firebaselottery.DBHelper.DBHelperPurchase.ConstantsPurchase;

public class DBSimulationHelper extends SQLiteOpenHelper {

    public DBSimulationHelper(Context context){
        super(context, ConstantsSimulation.DB_NAME, null, ConstantsSimulation.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ConstantsSimulation.CREATE_TB);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ConstantsSimulation.DROP_TB);
        onCreate(sqLiteDatabase);
    }
}
