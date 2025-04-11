package model.TimeSystem;

public interface Observer {
    public void onHourChanged(DateTime time);
    public void onDayChanged(DateTime time);
}
