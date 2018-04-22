package project.senior.com.firebaselottery.DBHelper.DBHelperPurchase;

public class ConstantsPurchase{

    //COLUMNS
    static final String COLS_ID = "purchase_id";
    static final String COLS_DATE = "purchase_date";
    static final String COLS_NUMBER = "purchase_number";
    static final String COLS_AMOUNT = "purchase_amount";
    static final String COLS_PAID = "purchase_paid";
    static final String COLS_STATUS = "purchase_status";
    static final String COLS_VALUE = "purchase_value";


    static final String DB_NAME = "modePurchase";
    static final String TB_NAME = "modePurchase";
    static final int DB_VERSION = 1;

    //CREATE TABLE
    static final String CREATE_TB = "CREATE TABLE modePurchase(purchase_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "purchase_date TEXT, purchase_number TEXT, purchase_amount TEXT, purchase_paid TEXT, purchase_status TEXT, purchase_value TEXT)";

    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;
}
