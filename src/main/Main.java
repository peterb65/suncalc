package main;

import java.text.DateFormat;
import java.util.Calendar;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.luckycatlabs.sunrisesunset.SunriseSunsetCalculator;
import com.luckycatlabs.sunrisesunset.dto.Location;

public class Main {
	CommandLine cmd = null;

	public static void main(String[] args) {
		new Main().doIt(args);
		
	}

	private void doIt(String[] args) {
		CommandLineParser parser = new BasicParser();
		Options options = new Options();
		options.addOption("t", false,  "Output times for sunrise and sunset");
		options.addOption("r", false, "Output time for sunrise");
		options.addOption("s", false, "Output time for sunset");
		options.addOption("q", false, "Quiet, only output the time without a describing label");
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	    if (cmd.hasOption("t")) printRiseAndSetTimes();
	    if (cmd.hasOption("r")) printRiseTime();
	    if (cmd.hasOption("s")) printSetTime();
	}

	private void printSetTime() {
	    SunriseSunsetCalculator calculator = getCalculator();
	    Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());
	    printTime("sunset", officialSunset);
	}

	private void printTime(String label, Calendar officialSunset) {
		DateFormat tFormat = DateFormat.getTimeInstance();
	    if (!cmd.hasOption("q")) System.out.print(label + ":");
	    System.out.println(tFormat.format(officialSunset.getTime()));
	}

	private void printRiseTime() {
	    SunriseSunsetCalculator calculator = getCalculator();
	    Calendar officialSunrise = calculator.getOfficialSunriseCalendarForDate(Calendar.getInstance());
	    printTime("sunrise", officialSunrise);
	}

	private void printRiseAndSetTimes() {
		printRiseTime();
		printSetTime();
	}

	private static SunriseSunsetCalculator getCalculator() {
		Location location = new Location("59.32944", "18.06861");
	    SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "Sweden/Stockholm");
		return calculator;
	}

	private static void printUsage() {
		System.out.println(
			"arguments: \n" +
			"           -t : print sunrise and sunset\n" + 
			"           -r : print sunrise\n" + 
			"           -s : print sunset");
	}
}