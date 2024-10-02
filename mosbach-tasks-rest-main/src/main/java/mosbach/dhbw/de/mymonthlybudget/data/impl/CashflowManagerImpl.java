package mosbach.dhbw.de.mymonthlybudget.data.impl;

import mosbach.dhbw.de.mymonthlybudget.data.api.Cashflow;
import mosbach.dhbw.de.mymonthlybudget.data.api.CashflowManager;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CashflowManagerImpl implements CashflowManager {


    private final String fileName = "src/main/resources/tasks.properties";


    private static CashflowManagerImpl cashflowManagerImpl = null;

    private CashflowManagerImpl(){};

    public static CashflowManagerImpl getCashflowManagerImpl(){
        if(cashflowManagerImpl == null) cashflowManagerImpl = new CashflowManagerImpl();
        return cashflowManagerImpl;
    }

    @Override
    public void addCashflow(Cashflow newCashflow) {
            List<Cashflow> temp = getAllCashflows();
            temp.add(newCashflow);
            setAllCashflows(temp);
    }

    @Override
    public List<Cashflow> getAllCashflows() {
        //Datei holen
        Properties properties =  new Properties();
        List<Cashflow> cashflows = new ArrayList<>();
        int i = 1;
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try(InputStream resourceStream = loader.getResourceAsStream(fileName)){
                properties.load(resourceStream);
            }
            while(properties.containsKey("Cashflow." + i + ".Type")) {
                String type = properties.getProperty("Task." + i + ".Type");
                String category = properties.getProperty("Task." + i + ".Category");
                Double amount = Double.parseDouble(properties.getProperty("Task." + i + ".Amount"));
                String date = properties.getProperty("Task." + i + ".Date");
                String paymentMethod = properties.getProperty("Task." + i + ".PaymentMethod");
                String repetition = properties.getProperty("Task." + i + ".Repetition");
                String comment = properties.getProperty("Task." + i + ".Comment");
                i++;

                cashflows.add(new
                        CashflowImpl(type, category,amount,date,paymentMethod,repetition,comment)

                );
            }
        } catch (IOException e) {
            Logger.getLogger( "GetAllCashflowReader").log(Level.INFO,
                    "File is not available");
            throw new RuntimeException(e);
        }
        //Datei öffnen
        //durchgehen und in einer Liste speichhern
        //Liste zurückgeben



        return cashflows;
    }
    public void setAllCashflows(List<Cashflow> cashflows){

        Properties properties = new Properties();
        int i = 1;

        for( Cashflow t : cashflows){
            properties.setProperty("Cashflow." + i + ".Type", t.getType());
            properties.setProperty("Cashflow." + i + ".Category", t.getCategory());
            properties.setProperty("Cashflow." + i + ".Amount", t.getAmount()+"");
            properties.setProperty("Cashflow." + i + ".Date", t.getDate());
            properties.setProperty("Cashflow." + i + ".PaymentMethod", t.getPaymentMethod());
            properties.setProperty("Cashflow." + i + ".Repetition", t.getRepetition());
            properties.setProperty("Cashflow." + i + ".Comment", t.getComment());
        }
        try { //hier fehlt was soll noch geschickt werden
            properties.store(new FileOutputStream(fileName), null);
        } catch (Exception e) {
            Logger.getLogger("SetAllCashflowWriter").log(Level.INFO, "File cannot be written to disk");
        }
    }
}
