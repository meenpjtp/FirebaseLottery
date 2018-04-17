package project.senior.com.firebaselottery.Models;

public class SimulationStaticModel {

    private int  id;
    private int sim_win;
    private int sim_didNotWin;

    public SimulationStaticModel(int id, int sim_win, int sim_didNotWin) {
        this.id = id;
        this.sim_win = sim_win;
        this.sim_didNotWin = sim_didNotWin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSim_win() {
        return sim_win;
    }

    public void setSim_win(int sim_win) {
        this.sim_win = sim_win;
    }

    public int getSim_didNotWin() {
        return sim_didNotWin;
    }

    public void setSim_didNotWin(int sim_didNotWin) {
        this.sim_didNotWin = sim_didNotWin;
    }
}
