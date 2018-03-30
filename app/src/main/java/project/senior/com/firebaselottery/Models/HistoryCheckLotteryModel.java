package project.senior.com.firebaselottery.Models;


import android.provider.BaseColumns;

public class HistoryCheckLotteryModel {

    private int id;
    private String selected_date;
    private String lottery_number;
    private String lottery_result;

    public HistoryCheckLotteryModel(int id, String selected_date, String lottery_number, String lottery_result) {
        this.id = id;
        this.selected_date = selected_date;
        this.lottery_number = lottery_number;
        this.lottery_result = lottery_result;
    }

    public class Column{
        public static final String ID = BaseColumns._ID;
        public static final String SELECTED_DATE = "selected_date";
        public static final String LOTTERY_NUMBER = "lottery_number";
        public static final String LOTTERY_RESULT = "lottery_result";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSelected_date() {
        return selected_date;
    }

    public void setSelected_date(String selected_date) {
        this.selected_date = selected_date;
    }

    public String getLottery_number() {
        return lottery_number;
    }

    public void setLottery_number(String lottery_number) {
        this.lottery_number = lottery_number;
    }

    public String getLottery_result() {
        return lottery_result;
    }

    public void setLottery_result(String lottery_result) {
        this.lottery_result = lottery_result;
    }
}
