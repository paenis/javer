package cse214.hw3;

public class Date implements Comparable<Date> {
    private int day;
    private int month;
    private int year;

    public Date() {
        this.day = 0;
        this.month = 0;
        this.year = 0;
    }

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    /**
     * Compares two dates.
     *
     * @param x the first date to compare
     * @param y the second date to compare
     * @return a negative integer if x is earlier than y, a positive integer if x is
     * later than y, or zero if both dates are the same
     */
    public static int compare(Date x, Date y) {
        if (x.year < y.year) {
            return -1;
        } else if (x.year > y.year) {
            return 1;
        } else {
            if (x.month < y.month) {
                return -1;
            } else if (x.month > y.month) {
                return 1;
            } else {
                if (x.day < y.day) {
                    return -1;
                } else if (x.day > y.day) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * Compares this date to another date.
     *
     * @param o the other date to compare
     * @return a negative integer if this date is earlier than the other date,
     * a positive integer if this date is later than the other date,
     * or zero if both dates are the same
     */ // wrapper for static method to implement Comparable
    @Override
    public int compareTo(Date o) {
        return compare(this, o);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
