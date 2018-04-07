package project.senior.com.firebaselottery.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBHelper helper;

    public DBAdapter(Context context){
        this.context =context;
        helper = new DBHelper(context);
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
        String[] columns = {Constants.COLS_ID,
                Constants.COLS_DATE,
                Constants.COLS_LOTTERY_NUMBER,
                Constants.COLS_RESULT };

        return sqLiteDatabase.query(Constants.TABLE_NAME,
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
            values.put(Constants.COLS_DATE, selected_date);
            values.put(Constants.COLS_LOTTERY_NUMBER, lottery_number);
            values.put(Constants.COLS_RESULT, lottery_result);

            sqLiteDatabase.insert(Constants.TABLE_NAME, Constants.COLS_ID, values);
            return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLottery(int id){
        try {
            int result = sqLiteDatabase.delete(Constants.TABLE_NAME, Constants.COLS_ID + " =?",
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
