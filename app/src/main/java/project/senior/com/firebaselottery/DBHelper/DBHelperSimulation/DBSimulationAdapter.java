package project.senior.com.firebaselottery.DBHelper.DBHelperSimulation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBSimulationAdapter {

    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBSimulationHelper helper;

    public DBSimulationAdapter(Context context){
        this.context =context;
        helper = new DBSimulationHelper(context);
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
        String[] columns = {ConstantsSimulation.COLS_ID,
                ConstantsSimulation.COLS_DATE,
                ConstantsSimulation.COLS_NUMBER,
                ConstantsSimulation.COLS_AMOUNT,
                ConstantsSimulation.COLS_PAID,
                ConstantsSimulation.COLS_PAID };

        return sqLiteDatabase.query(ConstantsSimulation.TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    public boolean addLottery(String simulation_date, String simulation_number, String simulation_amount, String simulation_paid, String simulation_status){
        try{
            ContentValues values = new ContentValues();
            values.put(ConstantsSimulation.COLS_DATE, simulation_date);
            values.put(ConstantsSimulation.COLS_NUMBER, simulation_number);
            values.put(ConstantsSimulation.COLS_AMOUNT, simulation_amount);
            values.put(ConstantsSimulation.COLS_PAID, simulation_paid);
            values.put(ConstantsSimulation.COLS_STATUS, simulation_status);


            sqLiteDatabase.insert(ConstantsSimulation.TB_NAME, ConstantsSimulation.COLS_ID, values);
            return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLottery(int id){
        try {
            int result = sqLiteDatabase.delete(ConstantsSimulation.TB_NAME, ConstantsSimulation.COLS_ID + " =?",
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
