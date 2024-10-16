package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;

import java.util.List;

public interface MonthlyReportManager {

    void addMonthlyReport(MonthlyReport report);

    MonthlyReport getMonthlyReport(String token, String month, Integer year);

    boolean removeMonthlyReport( int monthlyReportID );

    void createMonthlyReportTable();
}
