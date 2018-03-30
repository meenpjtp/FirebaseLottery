package project.senior.com.firebaselottery.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import project.senior.com.firebaselottery.Models.HistoryCheckLotteryModel;

public class DBHelperHistory extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;
    public static final String DB_NAME = "history.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "history";

    public DBHelperHistory(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = String.format("CREATE TABLE %s " + "(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT)",
                TABLE, HistoryCheckLotteryModel.Column.ID, HistoryCheckLotteryModel.Column.SELECTED_DATE,
                HistoryCheckLotteryModel.Column.LOTTERY_NUMBER, HistoryCheckLotteryModel.Column.LOTTERY_RESULT);
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE;
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

     public void addLottery(HistoryCheckLotteryModel history){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HistoryCheckLotteryModel.Column.SELECTED_DATE, history.getSelected_date());
        values.put(HistoryCheckLotteryModel.Column.LOTTERY_NUMBER, history.getLottery_number());
        values.put(HistoryCheckLotteryModel.Column.LOTTERY_RESULT, history.getLottery_result());
        sqLiteDatabase.insert(TABLE, null, values);
        sqLiteDatabase.close();
    }

    public ArrayList<HistoryCheckLotteryModel> getAllHistory(){
        String QUERY_ALL_HISTORY = "SELECT * FROM " + TABLE;
        ArrayList<HistoryCheckLotteryModel> historyList = new ArrayList<>();
        sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(QUERY_ALL_HISTORY, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            HistoryCheckLotteryModel history = new HistoryCheckLotteryModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            historyList.add(history);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        return historyList;
    }

    public void deleteHistory(int id){
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE, HistoryCheckLotteryModel.Column.ID +" = " + id,null);
        sqLiteDatabase.close();
    }
}
