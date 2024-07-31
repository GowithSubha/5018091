// LoggerTest.java

public class LoggerTest {
    public static void main(String[] args) {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        System.out.println("Are both loggers of the same reference? " + (logger1 == logger2));

        logger1.setLogLevel(LogLevel.TRACE);
        System.out.println("\nDefining TRACE level...");

        logger1.log(LogLevel.TRACE, "This is trace level log message");
        logger1.log(LogLevel.DEBUG, "This is debug level log message");
        logger1.log(LogLevel.INFO, "This is info level log message");
        logger1.log(LogLevel.WARN, "This is warn level log message");
        logger1.log(LogLevel.ERROR, "This is error level log message");

        System.out.println("Displaying all logged messages...");
        logger1.displayLogs();
    }
}