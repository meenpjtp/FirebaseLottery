package project.senior.com.firebaselottery.DBHelperDontUse.DBHelperSimulation;

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
                ConstantsSimulation.COLS_STATUS,
                ConstantsSimulation.COLS_VALUE};

        return sqLiteDatabase.query(ConstantsSimulation.TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    public boolean addLottery(String simulation_date, String simulation_number, String simulation_amount, String simulation_paid, String simulation_status, String simulation_value){
        try{
            ContentValues values = new ContentValues();
            values.put(ConstantsSimulation.COLS_DATE, simulation_date);
            values.put(ConstantsSimulation.COLS_NUMBER, simulation_number);
            values.put(ConstantsSimulation.COLS_AMOUNT, simulation_amount);
            values.put(ConstantsSimulation.COLS_PAID, simulation_paid);
            values.put(ConstantsSimulation.COLS_STATUS, simulation_status);
            values.put(ConstantsSimulation.COLS_VALUE, simulation_value);


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


    public Cursor retrieveSearch(String search) {
        String[] columns = {ConstantsSimulation.COLS_ID,
                ConstantsSimulation.COLS_DATE,
                ConstantsSimulation.COLS_NUMBER,
                ConstantsSimulation.COLS_AMOUNT,
                ConstantsSimulation.COLS_PAID,
                ConstantsSimulation.COLS_STATUS,
                ConstantsSimulation.COLS_VALUE};

        Cursor cursor = null;
        if(search != null && search.length() > 0){
            String sql = "SELECT * FROM " + ConstantsSimulation.TB_NAME +
                    " WHERE " + ConstantsSimulation.COLS_DATE + " LIKE '%" + search + "%'";
            cursor = sqLiteDatabase.rawQuery(sql, null);
            return cursor;
        }


        cursor = sqLiteDatabase.query(ConstantsSimulation.TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }




}

    /*public Cursor search (String search) {
        //Open connection to read only
        SQLiteDatabase db = helper.getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                ConstantsSimulation.COLS_DATE + "," +
                ConstantsSimulation.COLS_NUMBER + "," +
                ConstantsSimulation.COLS_AMOUNT + "," +
                ConstantsSimulation.COLS_PAID + "," +
                ConstantsSimulation.COLS_STATUS +
                " FROM " + ConstantsSimulation.TB_NAME +
                " WHERE " +  ConstantsSimulation.COLS_DATE + "  LIKE  '%" +search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }*/
