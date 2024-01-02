package banking_software_system;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	private static String[] single_digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight",
			"nine" };
	private static String[] two_digits = { "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
			"seventeen", "eighteen", "nineteen" };
	private static String[] ten_multiples = { "", "", "twenty", "thirty", "fourty", "fifty", "sixty", "seventy",
			"eighty", "ninety" };

	public static int betweenDays(String date1, String date2) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date myDate1 = dateFormat.parse(date1);
		Date myDate2 = dateFormat.parse(date2);

		long diff = Math.abs(myDate1.getTime() - myDate2.getTime()) / (1000 * 60 * 60 * 24);

		return (int) diff;
	}

	public static String dateConverter(Date date) {
		String myDate = date.toString();
		String[] tokens = myDate.split(" ");

		myDate = (monthConverter(tokens[1]) + "/" + tokens[2] + "/" + tokens[5]);

		return myDate;
	}

	public static String monthConverter(String month) {
		month = month.toLowerCase().substring(0, 3);
		String convertedMonth = " ";
		switch (month) {
		case "jan":
			convertedMonth = "01";
			break;
		case "feb":
			convertedMonth = "02";
			break;
		case "mar":
			convertedMonth = "03";
			break;
		case "apr":
			convertedMonth = "04";
			break;
		case "may":
			convertedMonth = "05";
			break;
		case "jun":
			convertedMonth = "06";
			break;
		case "jul":
			convertedMonth = "07";
			break;
		case "aug":
			convertedMonth = "08";
			break;
		case "sep":
			convertedMonth = "09";
			break;
		case "oct":
			convertedMonth = "10";
			break;
		case "nov":
			convertedMonth = "11";
			break;
		case "dec":
			convertedMonth = "12";
			break;

		}
		return convertedMonth;

	}

	public static String convertCurrency(double amount) {
		String wholePart = getWholePart(amount);
		String fraction = getFractionPart(amount);

		String wholePartInWords = convertDoubleDigits(wholePart);
		String amountInWords = wholePartInWords + " and " + fraction + "/100";
		return amountInWords;
	}

	private static String convertSingleDigit(String wholePart) {
		int wholePartInDigit = Integer.parseInt(wholePart);
		return single_digits[wholePartInDigit];

	}

	private static String convertDoubleDigits(String wholePart) {
		int amount = Integer.parseInt(wholePart);

		if (amount <= 9) {
			return convertSingleDigit(wholePart);
		}

		if (amount / 20 == 0) {
			return two_digits[amount % 10];
		}
		String onesDigit = wholePart.substring(1);
		String tensDigit = wholePart.substring(0, 1);

		if (onesDigit.equals("0")) {
			return ten_multiples[Integer.parseInt(tensDigit)];

		} else {
			return ten_multiples[Integer.parseInt(tensDigit)] + " " + single_digits[Integer.parseInt(onesDigit)];
		}

	}

	private static String getWholePart(double amount) {

		String amountStr = String.valueOf(amount);
		String[] tokens = amountStr.split("\\.");
		return tokens[0];

	}

	private static String getFractionPart(double amount) {

		String amountStr = String.valueOf(amount);
		String[] tokens = amountStr.split("\\.");
		return tokens[1];

	}
}
