package project.senior.com.firebaselottery.Models;

public class PurchaseModel {

    private String id;
    private String lottery_date;
    private String lottery_number;
    private String lottery_amount;
    private String lottery_paid;
    private String lottery_status;
    private String lottery_value;

    public PurchaseModel(String id, String lottery_date, String lottery_number, String lottery_amount, String lottery_paid, String lottery_status, String lottery_value) {
        this.id = id;
        this.lottery_date = lottery_date;
        this.lottery_number = lottery_number;
        this.lottery_amount = lottery_amount;
        this.lottery_paid = lottery_paid;
        this.lottery_status = lottery_status;
        this.lottery_value = lottery_value;
    }

    public PurchaseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLottery_date() {
        return lottery_date;
    }

    public void setLottery_date(String lottery_date) {
        this.lottery_date = lottery_date;
    }

    public String getLottery_number() {
        return lottery_number;
    }

    public void setLottery_number(String lottery_number) {
        this.lottery_number = lottery_number;
    }

    public String getLottery_amount() {
        return lottery_amount;
    }

    public void setLottery_amount(String lottery_amount) {
        this.lottery_amount = lottery_amount;
    }

    public String getLottery_paid() {
        return lottery_paid;
    }

    public void setLottery_paid(String lottery_paid) {
        this.lottery_paid = lottery_paid;
    }

    public String getLottery_status() {
        return lottery_status;
    }

    public void setLottery_status(String lottery_status) {
        this.lottery_status = lottery_status;
    }

    public String getLottery_value() {
        return lottery_value;
    }

    public void setLottery_value(String lottery_value) {
        this.lottery_value = lottery_value;
    }
}
