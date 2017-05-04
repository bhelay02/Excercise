package test.jpmorgan.Excercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {
	
	private Utils utils;
	private SimpleDateFormat dateformat;
	
	@Before
	public void setUp() {
		utils=new Utils();
		dateformat = new SimpleDateFormat("dd MMM yyyy");
	}
	
	@Test
	public void testIsWorkingDayWithWorkingDayAED() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("03 MAY 2017"));
		Assert.assertTrue(utils.isWorkingDay("AED", cl));
		
	}
	@Test
	public void testIsWorkingDayWithWeekendAED() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("05 MAY 2017"));
		Assert.assertFalse(utils.isWorkingDay("AED", cl));
		
	}
	@Test
	public void testIsWorkingDayWithWorkingDayGBP() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("03 MAY 2017"));
		Assert.assertTrue(utils.isWorkingDay("AED", cl));
		
	}
	@Test
	public void testIsWorkingDayWithWeekendGBP() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("06 MAY 2017"));
		Assert.assertFalse(utils.isWorkingDay("AED", cl));
		
	}
	
	@Test
	public void testNextWorkingDayWithWorkingDayAED() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("03 May 2017"));	
		Date d1 = utils.nextWorkingDay("AED", cl).getTime();
		String actuals = dateformat.format(d1).toString();
		Assert.assertEquals("03 May 2017", actuals);
		
	}
	@Test
	public void testNextWorkingDayWithWeekendAED() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("06 May 2017"));	
		Date d1 = utils.nextWorkingDay("AED", cl).getTime();
		String actuals = dateformat.format(d1).toString();
		Assert.assertEquals("07 May 2017", actuals);
		
	}
	@Test
	public void testNextWorkingDayWithWorkingDayGBP() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("03 May 2017"));	
		Date d1 = utils.nextWorkingDay("GBP", cl).getTime();
		String actuals = dateformat.format(d1).toString();
		Assert.assertEquals("03 May 2017", actuals);
		
	}
	@Test
	public void testNextWorkingDayWeekendGBP() throws ParseException
	{

		Calendar cl = Calendar.getInstance();
		cl.setTime(new SimpleDateFormat(utils.DATEFORMAT).parse("06 May 2017"));	
		Date d1 = utils.nextWorkingDay("GBP", cl).getTime();
		String actuals = dateformat.format(d1).toString();
		Assert.assertEquals("08 May 2017", actuals);		
	}


}
