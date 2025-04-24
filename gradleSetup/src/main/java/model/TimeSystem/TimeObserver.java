package model.TimeSystem;

public interface TimeObserver {
    public void onHourChanged(DateTime time,boolean newDay);
}
