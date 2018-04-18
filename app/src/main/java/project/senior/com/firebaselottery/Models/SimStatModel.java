package project.senior.com.firebaselottery.Models;

// Spending

public class SimStatModel {

    private int sim_id;
    private String sim_date; // 18/4/2561
    private String sim_type; // win | did not win
    private float sim_score; // 1

    public SimStatModel(int sim_id, String sim_date, String sim_type, float sim_score) {
        this.sim_id = sim_id;
        this.sim_date = sim_date;
        this.sim_type = sim_type;
        this.sim_score = sim_score;
    }

    public int getSim_id() {
        return sim_id;
    }

    public void setSim_id(int sim_id) {
        this.sim_id = sim_id;
    }

    public String getSim_date() {
        return sim_date;
    }

    public void setSim_date(String sim_date) {
        this.sim_date = sim_date;
    }

    public String getSim_type() {
        return sim_type;
    }

    public void setSim_type(String sim_type) {
        this.sim_type = sim_type;
    }

    public float getSim_score() {
        return sim_score;
    }

    public void setSim_score(float sim_score) {
        this.sim_score = sim_score;
    }
}
