
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    
    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[31];
        monthCounts = new int [12];
        // Create the reader to obtain the data.
        reader = new LogfileReader("demo.log");
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            int day = entry.getDay() -1;
            int month = entry.getMonth() - 1;
            if (hour >= 0 && hour <24) hourCounts[hour]++;
            if (day >= 0 && day <31)dayCounts[day]++;
            if (month >= 0 && month <12)monthCounts[month]++;
        }
    }
    // the number of accesses
    public int numberOfAccesses() {
        int total = 0;
        for (int count: hourCounts) {
            total += count;
        }
        return total;
    }
    // the busiest hour
    public int busiestHour() {
        int maxHour = 0;
        for (int hour =1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] > hourCounts[maxHour]) {
                maxHour = hour;
            }
        }
        return maxHour;
    }
    // quietest hour
    public int quietestHour() {
        int minHour = 0;
        for ( int hour = 1; hour < hourCounts.length; hour++) {
            if (hourCounts[hour] < hourCounts[minHour]) {
                minHour = hour;
            }
        }
        return minHour;
    }
    // busiest two hour period 
    public String busiestTwoHour() {
        int maxAccesses = 0;
        int startHour = 0;
        
        for (int hour = 0; hour < hourCounts.length - 1; hour++) {
            int twoHourAccesses = hourCounts[hour] + hourCounts[hour+ 1];
            if (twoHourAccesses > maxAccesses) {
                maxAccesses = twoHourAccesses;
                startHour = hour;
            }
        }
        return startHour + ":00 to " + (startHour + 1) + ":59";
    }
    // busiest Day
    public int busiestDay() {
        int maxDay = 0;
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] > dayCounts[maxDay]) {
                maxDay = day;
            }
        }
        return maxDay + 1;
    }
    // quietest day
    public int quietestDay() {
        int minDay = 0;
        for (int day = 1; day < dayCounts.length; day++) {
            if (dayCounts[day] < dayCounts[minDay]) {
                minDay = day;
            }
        }
        return minDay + 1;
    }
    // total accesses per month 
    public int [] totalAccessesPerMonth() {
        return monthCounts.clone();
    }
    // busiest month 
    public int busiestMonth() {
        int maxMonth = 0;
        for (int month = 1; month < monthCounts.length; month++){
            if (monthCounts[month] > monthCounts[maxMonth]) {
                maxMonth = month;
            }
        }
        return maxMonth + 1;
    }
    // quietest month 
    public int quietestMonth() {
        int minMonth = 0;
        for (int month = 1; month < monthCounts.length; month++) {
            if (monthCounts[month] < monthCounts[minMonth]) {
                minMonth = month;
            }
        }
        return minMonth + 1;
    }
    //average accesses per month 
    public double averageAccessesPerMonth() {
        int total = 0;
        for (int count: monthCounts) {
            total += count;
        }
        return total /12.0;
    }
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
