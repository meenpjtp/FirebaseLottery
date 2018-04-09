package project.senior.com.firebaselottery.DBHelper.DBHelperPurchase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBPurchaseAdapter {

    Context context;
    SQLiteDatabase sqLiteDatabase;
    DBPurchaseHelper helper;

    public DBPurchaseAdapter(Context context){
        this.context =context;
        helper = new DBPurchaseHelper(context);
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
        String[] columns = {ConstantsPurchase.COLS_ID,
                ConstantsPurchase.COLS_DATE,
                ConstantsPurchase.COLS_NUMBER,
                ConstantsPurchase.COLS_AMOUNT,
                ConstantsPurchase.COLS_PAID,
                ConstantsPurchase.COLS_PAID };

        return sqLiteDatabase.query(ConstantsPurchase.TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null);
    }

    public boolean addLottery(String purchase_date, String purchase_number, String purchase_amount, String purchase_paid, String purchase_status){
        try{
            ContentValues values = new ContentValues();
            values.put(ConstantsPurchase.COLS_DATE, purchase_date);
            values.put(ConstantsPurchase.COLS_NUMBER, purchase_number);
            values.put(ConstantsPurchase.COLS_AMOUNT, purchase_amount);
            values.put(ConstantsPurchase.COLS_PAID, purchase_paid);
            values.put(ConstantsPurchase.COLS_STATUS, purchase_status);


            sqLiteDatabase.insert(ConstantsPurchase.TB_NAME, ConstantsPurchase.COLS_ID, values);
            return true;

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLottery(int id){
        try {
            int result = sqLiteDatabase.delete(ConstantsPurchase.TB_NAME, ConstantsPurchase.COLS_ID + " =?",
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
