package project.senior.com.firebaselottery.DBHelper.DBHelperHistory;

public class ConstantsHistory {

    //COLUMNS
    static final String COLS_ID = "id";
    static final String COLS_DATE = "selected_date";
    static final String COLS_LOTTERY_NUMBER = "lottery_number";
    static final String COLS_RESULT = "lottery_result";

    static final String DB_NAME = "history";
    static final String TB_NAME = "history";
    static final int DB_VERSION = 1;

    //CREATE TABLE
    static final String CREATE_TB = "CREATE TABLE history(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "selected_date TEXT, lottery_number TEXT, lottery_result TEXT)";

    static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;

}
