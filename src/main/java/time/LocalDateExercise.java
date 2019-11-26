package time;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoField;

/**
 * @author nickbi
 * @date create on 2019/11/26
 */
public class LocalDateExercise {
	public static void main(String[] args) {

	}

	public static void printLocalDate() {
		//当前年月日
		LocalDate localDate = LocalDate.now();
		System.out.println("local Date now: " + localDate);
		//指定年月日
		LocalDate of = LocalDate.of(2019, 11, 26);
		System.out.println("local date of: " + of);
		final int year = localDate.getYear();
		final int year1 = localDate.get(ChronoField.YEAR);
		final Month month = localDate.getMonth();
		final int month1 = localDate.get(ChronoField.MONTH_OF_YEAR);
		final int day = localDate.getDayOfMonth();
		final int day1 = localDate.get(ChronoField.DAY_OF_MONTH);
	}
}
