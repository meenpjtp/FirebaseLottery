package project.senior.com.firebaselottery.DBHelper.DBHelperHistory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHistoryAdapter {

    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBHistoryHelper helper;

    public DBHistoryAdapter(Context context){
        this.context =context;
        helper = new DBHistoryHelper(context);
    }

    public void openDB(){
        try {
            sqLiteDatabase = helper.getWritableDatabase();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeDB(){
        try {
            helper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cursor retrieve() {
        String[] columns = {ConstantsHistory.COLS_ID,
                ConstantsHistory.COLS_DATE,
                ConstantsHistory.COLS_LOTTERY_NUMBER,
                ConstantsHistory.COLS_RESULT };

        return sqLiteDatabase.query(ConstantsHistory.TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    public boolean addLottery(String selected_date, String lottery_number, String lottery_result){
        try{
            ContentValues values = new ContentValues();
            values.put(ConstantsHistory.COLS_DATE, selected_date);
            values.put(ConstantsHistory.COLS_LOTTERY_NUMBER, lottery_number);
            values.put(ConstantsHistory.COLS_RESULT, lottery_result);

            sqLiteDatabase.insert(ConstantsHistory.TB_NAME, ConstantsHistory.COLS_ID, values);
            return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLottery(int id){
        try {
            int result = sqLiteDatabase.delete(ConstantsHistory.TB_NAME, ConstantsHistory.COLS_ID + " =?",
                    new String[]{String.valueOf(id)});
            if(result > 0){
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
