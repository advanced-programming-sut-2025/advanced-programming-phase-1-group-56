package model.timeSystem;

public interface TimeObserver {
    public void onHourChanged(DateTime time,boolean newDay);
}
