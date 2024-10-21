package mosbach.dhbw.de.mymonthlybudget.data.api;

import mosbach.dhbw.de.mymonthlybudget.data.impl.MonthlyReportImpl;
import mosbach.dhbw.de.mymonthlybudget.model.MonthlyReport;
import mosbach.dhbw.de.mymonthlybudget.model.StatistikDTO;


import java.util.List;

public interface MonthlyReportManager {

    void addMonthlyReport(MonthlyReportImpl report);

    MonthlyReport getMonthlyReport(int userID, String month, Integer year);

    boolean removeMonthlyReport( int monthlyReportID );

    void createMonthlyReportTable();

    List<StatistikDTO> getStatistikByYear(int userId, int year);
}
