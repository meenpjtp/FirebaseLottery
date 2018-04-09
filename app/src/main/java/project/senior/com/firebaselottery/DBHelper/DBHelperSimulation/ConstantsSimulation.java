package project.senior.com.firebaselottery.DBHelper.DBHelperSimulation;

public class ConstantsSimulation {

    //COLUMNS
    static final String COLS_ID = "id";
    static final String COLS_DATE = "simulation_date";
    static final String COLS_NUMBER = "simulation_number";
    static final String COLS_AMOUNT = "simulation_amount";
    static final String COLS_PAID = "simulation_paid";
    static final String COLS_STATUS = "simulation_status";

    static final String DB_NAME = "mode_simulation";
    static final String TB_NAME = "mode_simulation";
    static final int DB_VERSION = 1;

    //CREATE TABLE
    static final String CREATE_TB = "CREATE TABLE simulation(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "purchase_date TEXT, purchase_number TEXT, purchase_amount TEXT, purchase_paid TEXT, purchase_status TEXT)";

    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;
}

