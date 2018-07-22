package project.senior.com.firebaselottery.DBHelperDontUse.DBHelperSimulation;

public class ConstantsSimulation {

    //COLUMNS
    static final String COLS_ID = "simulation_id";
    static final String COLS_DATE = "simulation_date";
    static final String COLS_NUMBER = "simulation_number";
    static final String COLS_AMOUNT = "simulation_amount";
    static final String COLS_PAID = "simulation_paid";
    static final String COLS_STATUS = "simulation_status";
    static final String COLS_VALUE = "simulation_value";

    static final String DB_NAME = "modeSimulation";
    static final String TB_NAME = "modeSimulation";
    static final int DB_VERSION = 1;

    //CREATE TABLE
    static final String CREATE_TB = "CREATE TABLE modeSimulation(simulation_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "simulation_date TEXT, simulation_number TEXT, simulation_amount TEXT, simulation_paid TEXT, simulation_status TEXT, simulation_value TEXT)";

    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;
}

