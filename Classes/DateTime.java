
import java.util.Date;

public class DateTime implements IDateTime {
    
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;

    public DateTime(int year, int month, int day, int hour, int minute) {

        if(year < 1) {
            throw new IllegalArgumentException("Year must be positive.");
        }
        if(month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be in [1..12].");
        }

        int maxDays = 31;
        if(month == 4 || month == 6 || month == 9 || month == 11) {
            maxDays = 30;
        } else if(month == 2) {
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            maxDays = isLeapYear ? 29 : 28;
        }

        if(day < 1 || day > maxDays) {
            throw new IllegalArgumentException("Day must be in [1.." + maxDays + "].");
        }
        if(hour < 0 || hour > 23) {
            throw new IllegalArgumentException("Hour must be in [0..23].");
        }
        if(minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute must be in [0..59].");
        }

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }
    public int getMonth() {
        return month;
    }
    public int getDay() {
        return day;
    }
    public int getHour() {
        return hour;
    }
    public int getMinute() {
        return minute;
    }
    
    public String format() {
        return String.format("%02d/%02d/%04d %02d:%02d", month, day, year, hour, minute);
    }

    public int compareTo(IDateTime other) {
        if (this.year != other.getYear()) {
            return Integer.compare(this.year, other.getYear());
        }
        if (this.month != other.getMonth()) {
            return Integer.compare(this.month, other.getMonth());
        }
        if (this.day != other.getDay()) {
            return Integer.compare(this.day, other.getDay());
        }
        if (this.hour != other.getHour()) {
            return Integer.compare(this.hour, other.getHour());
        }
        return Integer.compare(this.minute, other.getMinute());
    }

    
}

