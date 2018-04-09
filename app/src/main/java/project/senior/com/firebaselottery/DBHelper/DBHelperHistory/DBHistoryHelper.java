package project.senior.com.firebaselottery.DBHelper.DBHelperHistory;


import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHistoryHelper extends SQLiteOpenHelper {

    public DBHistoryHelper(Context context){
        super(context, ConstantsHistory.DB_NAME, null, ConstantsHistory.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            sqLiteDatabase.execSQL(ConstantsHistory.CREATE_TB);
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(ConstantsHistory.DROP_TB);
        onCreate(sqLiteDatabase);
    }
}
