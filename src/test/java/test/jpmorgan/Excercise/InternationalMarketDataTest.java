package test.jpmorgan.Excercise;

import java.text.ParseException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InternationalMarketDataTest {

	private InternationalMarketData data;
	@Before
	public void setup()
	{
		data = new InternationalMarketData();
	}
	
	@Test
	public void testProcessData(){
         try {
			data.processData();
		} catch (ParseException e) {
            Assert.assertFalse(true);
			e.printStackTrace();
		}
	}
         
}
