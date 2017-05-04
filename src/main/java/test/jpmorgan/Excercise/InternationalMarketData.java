package test.jpmorgan.Excercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class InternationalMarketData {

	List<String[]> inputData;
	Utils utils = new Utils();
	Map<Date,Double> incomingStDay = new TreeMap<Date,Double>();
	Map<Date,Double> outgoingStDay = new TreeMap<Date,Double>();
	Map<String,Double> outgoingEntityRank = new HashMap<String,Double>();
	Map<String,Double> incomingEntityRank = new HashMap<String,Double>();

	public static void main(String[] args) throws ParseException {
		InternationalMarketData data = new InternationalMarketData();
		data.processData();

	}
	
	/*
	 *  This method prints the output to console for Amount in USD settled incoming everyday for the input data.
	 */
	
	private void usdIncomingAmountSettledEveryday(){
		
		System.out.println("\n"+"***************** Amount in USD settled incoming everyday *****************");
		System.out.println("\n"+"Date     " + "           value "+"\n");
		for (Map.Entry<Date, Double> entry : incomingStDay.entrySet()) {
	        String key = new SimpleDateFormat(utils.DATEFORMAT).format(entry.getKey());
	        double value = entry.getValue();
	        System.out.println(key + "         " + value);
	    }	
	}
	
	
	/*
	 *  This method prints the output to console for Amount in USD settled outgoing everyday for the input data.
	 */
	
    private void usdOutgoingAmountSettledEveryday(){
    	System.out.println("\n"+"***************** Amount in USD settled outgoing everyday *****************");
		System.out.println("\n"+"Date     " + "           value "+"\n");
		for (Map.Entry<Date, Double> entry : outgoingStDay.entrySet()) {
	        String key = new SimpleDateFormat(utils.DATEFORMAT).format(entry.getKey());
	        double value = entry.getValue();
	        System.out.println(key + "         " + value);
	    }	
		
	}

    /*
	 *  This method prints the output to console for  Entities based/ranked on outgoing for the input data.
	 */
	
    private void rankingEntitiesOutgoing(){
    	System.out.println("\n"+"*****************     Entities based/ranked on outgoing   *****************");

		ValueComparator comparator = new ValueComparator();

		outgoingEntityRank.entrySet()
    	                  .stream()
  	                      .sorted(Map.Entry.comparingByValue(comparator))
  	                      .forEach(System.out::println);
	}
    
    /*
   	 *  This method prints the output to console for  Entities based/ranked on incoming for the input data.
   	 */
    private void rankingEntitiesIncoming(){
    	
    	System.out.println("\n"+"*****************    Entities based/ranked on incoming    *****************");

		ValueComparator comparator = new ValueComparator();

    	incomingEntityRank.entrySet()
    	                  .stream()
  	                      .sorted(Map.Entry.comparingByValue(comparator))
  	                      .forEach(System.out::println);
    
   	}
    
    
    /*
   	 *  This method takes the input file path to process data from , processes the input data and prints the output to console through helper methods.
   	 */
    public void processData() throws ParseException{
    	inputData = utils.loadSampleData();
    	
    	SimpleDateFormat date = new SimpleDateFormat(utils.DATEFORMAT);
	 
		for(String[] input:inputData){
			
			
			String entity       =  input[0];
            String instruction  =  input[1];	
			double agreedFx     =  Double.parseDouble(input[2]);
            String currency     =  input[3];
			Date settlementDate =  date.parse(input[5]);
			double units        =  Double.parseDouble(input[6]);
			double pricePerUnit =  Double.parseDouble(input[7]);


			double settlementVal = pricePerUnit*units*agreedFx;
			double rankVal = settlementVal;

			
			Calendar cl = Calendar.getInstance();
			cl.setTime(settlementDate);
			
			/*
			 * update the settlement date if required to next working day based upon the currency.
			 */
			settlementDate = utils.isWorkingDay(currency, cl) ? settlementDate : utils.nextWorkingDay(currency, cl).getTime();
			
			
			if(instruction.equals(utils.SELL)){
				double value = (incomingStDay.containsKey(settlementDate)) ? (incomingStDay.get(settlementDate)+settlementVal):settlementVal;				
				incomingStDay.put(settlementDate, value);
				
				rankVal = (incomingEntityRank.containsKey(entity)) ? (incomingEntityRank.get(entity)+rankVal) :rankVal;
			    incomingEntityRank.put(entity, rankVal);
			
			}
			else if(instruction.equals(utils.BUY))
			{
				
				double value = (outgoingStDay.containsKey(settlementDate)) ? (outgoingStDay.get(settlementDate)+settlementVal):settlementVal;				
				outgoingStDay.put(settlementDate, value);

				rankVal = (outgoingEntityRank.containsKey(entity)) ? (outgoingEntityRank.get(entity)+rankVal) :rankVal;
				outgoingEntityRank.put(entity, rankVal);		
				
			}
			else{		
			}
		}
		
		usdIncomingAmountSettledEveryday();
		usdOutgoingAmountSettledEveryday();
		rankingEntitiesIncoming();
		rankingEntitiesOutgoing();
    	
    }
}


class ValueComparator implements Comparator<Double> {
	@Override
	public int compare(Double o1, Double o2) {
		if(o1<o2)
			return 1;
		if(o1>o2)
			return -1;
		return 0;
	}
}
