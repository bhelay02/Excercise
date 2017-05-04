package test.jpmorgan.Excercise;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class Utils {

    final String[] differentWorkWeekCurrency = {"AED","SAR"};
    final String filePath = "/sampledata.csv";
    final String BUY = "B";
    final String SELL = "S";
    final String DATEFORMAT = "dd MMM yyyy";


    /*
     * This method checks for a particular date if it's a working day based upon the currency.
     */
	
    boolean isWorkingDay(String currency, Calendar date){
		
		System.out.println(date.getTime());
		if(currency == null || currency.isEmpty() || date==null)
			return false;
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);

		if(Arrays.stream(differentWorkWeekCurrency).anyMatch(x -> x.equals(currency))){
			if(dayOfWeek >= Calendar.SUNDAY && dayOfWeek <= Calendar.THURSDAY)
				return true;
		}
		else {
			if(dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY)
				return true;
		}
		return false;
		
	}
    
    
    /*
     * This method checks for a next working date based upon the currency.
     */
	
	Calendar nextWorkingDay(String currency, Calendar date){
		if(currency == null || currency.isEmpty() || date==null)
			return null;
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		
		if(Arrays.stream(differentWorkWeekCurrency).anyMatch(x -> x.equals(currency))){
			if(!(dayOfWeek >= Calendar.SUNDAY && dayOfWeek <= Calendar.THURSDAY))
				if(dayOfWeek == Calendar.FRIDAY)
					date.add(Calendar.DAY_OF_MONTH, 2);
				else
					date.add(Calendar.DAY_OF_MONTH, 1);
		}
		else {
			if(!(dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.FRIDAY))
				if(dayOfWeek == Calendar.SATURDAY)
					date.add(Calendar.DAY_OF_MONTH, 2);
				else
					date.add(Calendar.DAY_OF_MONTH, 1);
		}
		return date;
		
	}
	
	 /*
     * This method loads the data from a sample input file.
     */
	
	 List<String[]> loadSampleData(){
    	
    	List<String[]> data = new ArrayList<String[]>();
    	String csvFile="";
    	
		try {
			csvFile = getClass().getResource(filePath).toURI().getPath();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		
		String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	br.readLine();
            while ((line = br.readLine()) != null) {
                String[] dataLine = line.split(cvsSplitBy);
                data.add(dataLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
	
	
	
	
}
