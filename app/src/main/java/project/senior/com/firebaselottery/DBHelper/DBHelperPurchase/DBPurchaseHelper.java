package project.senior.com.firebaselottery.DBHelper.DBHelperPurchase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBPurchaseHelper extends SQLiteOpenHelper {

    public DBPurchaseHelper(Context context){
        super(context, ConstantsPurchase.DB_NAME, null, ConstantsPurchase.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ConstantsPurchase.CREATE_TB);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ConstantsPurchase.DROP_TB);
        onCreate(sqLiteDatabase);
    }
}
