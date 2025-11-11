package com.techflow.ws.sys.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Utils {

	private static final String RANDOM_LICENSE = "0123456789ACEFGHJKLMNPRTUVWXYZ";
	private static final String RANDOM_CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final Random RANDOM = new Random();
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Logger log = LogManager.getLogger(Utils.class);

	public static final String csvStringToString(String csvString) {
		String ret = csvString;
		if(csvString.startsWith("\"") && csvString.endsWith("\"")) {
			ret = ret.substring(1,ret.length()-1);
			ret = ret.replaceAll("\"\"", "\"");
		}
		return ret;
	}
	
	public static final String lowerCase(String str) {
		return (str != null ? str.toLowerCase() : null);
	}
	
	public static final String upperCase(String str) {
		return (str != null ? str.toUpperCase() : null);
	}
	
	public static final String WEEKDAY[] = { "common.label.weekday.sun", "common.label.weekday.mon",
			"common.label.weekday.tue", "common.label.weekday.wed", "common.label.weekday.thu", "common.label.weekday.fri",
			"common.label.weekday.sat", "common.label.weekday.sun" };

	public static String generateRandomKey(int length, int blockLength, String sep) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0, bl=0; i < length; i++, bl++) {
			if(bl >= blockLength) {
				sb.append(sep);
				bl = 0;
			}
			sb.append(RANDOM_LICENSE.charAt(RANDOM.nextInt(RANDOM_LICENSE.length())));
		}
		return sb.toString();
	}
	
	public static String generateRandomString(int length) {
		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(RANDOM_CHARACTERS.charAt(RANDOM.nextInt(RANDOM_CHARACTERS.length())));
		}
		return sb.toString();
	}


	public static Date addHours(Date today, int hoursToAdd) {
		Long ldate = today.getTime() + (hoursToAdd * 60 * 60 * 1000);
		return new Date(ldate);
	}

	
	public static Date addDays(Date today, int daysToAdd) {
		if (today != null) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.DAY_OF_MONTH, daysToAdd);
			return c.getTime();
		} else {
			return null;
		}
	}

	public static Date addMonths(Date today, int monthsToAdd) {
		if (today != null) {
			java.util.Calendar c = java.util.Calendar.getInstance();
			c.setTime(today);
			c.add(Calendar.MONTH, monthsToAdd);
			return c.getTime();
		} else {
			return null;
		}
	}

	
	public static String dateFormatDate(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
		
	}
	
	public static String dateFormat(String dateFormat) {
		return dateFormat(LocalDateTime.now(), dateFormat);
	}

	public static String dateFormat(LocalDateTime date, String dateFormat) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return date.format(formatter);
	}

	public static String dateTimeAddedMinutes(Integer minutes, String dateFormat) {
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nowPlus30Minutes = now.plus(minutes, ChronoUnit.MINUTES);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
		return nowPlus30Minutes.format(formatter);
	}

	public static String hideEmail(String email) {
		int pos = email.indexOf("@");

		return email.substring(0, 1) + "***" + email.substring(pos);
	}

	public static Boolean equals(BigDecimal val1, BigDecimal val2) {
		if (val1 == null && val2 == null)
			return true;
		if (val1 != null && val2 != null) {
			BigDecimal val1round = val1.setScale(2, RoundingMode.CEILING);
			BigDecimal val2round = val2.setScale(2, RoundingMode.CEILING);
			return val1round.equals(val2round);
		}
		return false;
	}
	

	public static Boolean equals(Double val1, Double val2) {
		if (val1 == null && val2 == null)
			return true;
		if (val1 != null && val2 != null && val1.equals(val2))
			return true;
		return false;
	}

	public static Boolean equals(Boolean val1, Boolean val2) {
		if (val1 == null && val2 == null)
			return true;
		if (val1 != null && val2 != null && val1.equals(val2))
			return true;
		return false;
	}

	public static Boolean equals(Long val1, Long val2) {
		if (val1 == null && val2 == null)
			return true;
		if (val1 != null && val2 != null && val1.equals(val2))
			return true;
		return false;
	}

	public static Boolean equals(Date date1, Date date2) {
		if (date1 == null && date2 == null)
			return true;
		if (date1 != null && date2 != null && date1.equals(date2))
			return true;
		return false;
	}

	public static Boolean equals(String str1, String str2) {
		if (str1 == null && str2 == null)
			return true;
		if (str1 != null && str2 != null && str1.equals(str2))
			return true;
		return false;
	}

	public static Boolean equals(Integer val1, Integer val2) {
		if (val1 == null && val2 == null)
			return true;
		if (val1 != null && val2 != null && val1.equals(val2))
			return true;
		return false;
	}

	/*
	public static Boolean equals(CommonIdEntity obj1, CommonIdEntity obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 != null && obj2 != null && obj1.getId().equals(obj2.getId()))
			return true;
		return false;
	}
	
	
	public static Boolean equals(CommonIdIntEntity obj1, CommonIdIntEntity obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1 != null && obj2 != null && obj1.getId().equals(obj2.getId()))
			return true;
		return false;
	}
	 */

	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		return pattern.matcher(email).matches();
	}

	public static String padLeft(Integer input, int size, char paddingChar) {
		return padLeft(input.toString(), size, paddingChar);
	}

	public static String padLeft(String input, int size, char paddingChar) {
		if (input.length() < size) {
			String padding = String.format("%" + (size - input.length()) + "s", "").replace(' ', paddingChar);
			return padding + input;
		}
		return input;

	}

	public static String replaceString(String str, Map<String, String> map, String prefixChar, String suffixChar) {
		String aux = str != null ? str : "";
		if(map != null && map.size() > 0) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				String value;
				if(entry.getValue() == null)
					value = "";
				else
					value = entry.getValue();
				aux = aux.replace(prefixChar + entry.getKey() + suffixChar, value).replace(prefixChar + entry.getKey().toLowerCase() + suffixChar, value).replace(prefixChar + entry.getKey().toUpperCase() + suffixChar, value);
			}
		}
		return aux;
	}

	public static Date dateZeroFirstTime(Date currentDate) {
		ZonedDateTime zdate = ZonedDateTime.from(currentDate.toInstant());
		zdate = zdate.toLocalDate().atStartOfDay(zdate.getZone());
		return Date.from(zdate.toInstant());
	}
	
	public static Date dateFromString(String date) {
		if(date.length() >= 10) {
			String timeFormat = " HH:mm:ss.SSSZ"; 
			date = date.replace("T", " ");
			if(date.charAt(4) == '-' || date.charAt(4) == '/') {
				char sep = date.charAt(4);
				return dateFromString("yyyy" + sep + "MM" + sep + "dd" + timeFormat.substring(0, date.length()-10), date);
			}
			else
			if(date.charAt(2) == '-' || date.charAt(2) == '/')
			{
				char sep = date.charAt(2);
				return dateFromString("dd" + sep + "MM" + sep + "yyyy" + timeFormat.substring(0, date.length()-10), date);
			}
		}
		return null;
	}
	
	public static Date dateFromString(String format, String date) {
		try {
			if(date == null) return null;
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.parse(date);
		}
		catch(Exception ex) {
			return null;
		}
	}
	
	public static Date date12(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		Date retDate = localDateTimeToDate(startOfDay);
		return Utils.addHours(retDate, 12);
	}

	public static Date dateStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date dateEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	public static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static long differenceMinutes(Date start, Date end) {
		long diffMillis = Math.abs(start.getTime() - end.getTime());
		return TimeUnit.MINUTES.convert(diffMillis, TimeUnit.MILLISECONDS);

	}
	
	public static long differenceDays(Date start, Date end) {
		long diffMillis = Math.abs(start.getTime() - end.getTime());
		return TimeUnit.DAYS.convert(diffMillis, TimeUnit.DAYS);
	}

	public static long differenceSeconds(Date start, Date end) {
		long diffMillis = Math.abs(start.getTime() - end.getTime());
		return TimeUnit.SECONDS.convert(diffMillis, TimeUnit.MILLISECONDS);

	}

	public static long differenceMillis(Date start, Date end) {
		long diffMillis = Math.abs(start.getTime() - end.getTime());
		return diffMillis;
	}

	 public static String removeLeadingZeros(String str) {
        // Check if the string is null or empty
        if (str == null || str.isEmpty()) {
            return str;
        }

        // Check if the string is a valid number
        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            // If not a valid number, return the original string
            return str;
        }

        // Remove leading zeros for integer part of the number
        String[] parts = str.split("\\.");
        parts[0] = parts[0].replaceFirst("^0+", "");

        // If integer part is empty, set it to "0"
        if (parts[0].isEmpty()) {
            parts[0] = "0";
        }

        // If there's a fractional part, concatenate it back
        if (parts.length > 1) {
            return parts[0] + '.' + parts[1];
        } else {
            // If there's no fractional part, return the integer part only
            return parts[0];
        }
    }
	
	public static String numbersOnly(String input) {
		if (input == null) {
			return null;
		}
		return input.replaceAll("[^0-9]", "");
	}

	public static Path createDirectory(String directory) {

		try {
			Path fileStorageLocation = Paths.get(directory).toAbsolutePath().normalize();
			if (!new File(directory).exists()) {
				Files.createDirectories(fileStorageLocation);
			}
			return fileStorageLocation;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String fileExtension(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1)).get();
	}
	
	public static Integer currentDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static Integer currentMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	
	public static Integer currentMonth() {
		return currentMonth(new Date());
	}

	public static byte[] convertToUTF8ByteArray(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}

	public static String convertToUTF8(String input) {
		return new String(convertToUTF8ByteArray(input), StandardCharsets.UTF_8);
	}

	public static String readUrl(String address, Charset encoding) throws IOException {
		URL url = new URL(address);
		try (InputStream in = url.openStream())
	    {
	        byte[] bytes = in.readAllBytes();
	        return new String(bytes, encoding);
	    }
	}

	public static String readFile(String path, Charset encoding) {
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(path));
			return new String(encoded, encoding);
		}
		catch(Exception ex) {
			log.error(ex.getMessage());
			return null;
		}
	}
	
	public static Map<String, String>  dateTimeVars(String zone) {
		Map<String, String> vars = new HashMap<>();
		if(zone == null || zone.isBlank())
			zone = "UTC";
		
		LocalDateTime now = LocalDateTime.now(ZoneId.of(zone));
		
		vars.put("dd", padLeft(now.getDayOfMonth(),2,'0'));
		vars.put("MM", padLeft(now.getMonthValue(),2,'0'));
		vars.put("yyyy", String.valueOf(now.getYear()));
		vars.put("HH", padLeft(now.getHour(),2,'0'));
		vars.put("mm", padLeft(now.getMinute(),2,'0'));
		vars.put("ss", padLeft(now.getSecond(),2,'0'));
		vars.put("z", padLeft(now.getDayOfYear(),3,'0'));
		
		vars.put("yyyyMMdd", vars.get("yyyy") + vars.get("MM") + vars.get("dd"));
		vars.put("HHmmss", vars.get("HH") + vars.get("mm") + vars.get("ss"));
		
		return vars;
	}
	
	public static Map<String, String>  dateTimeVars(Date date, String zone) {
		Map<String, String> vars = new HashMap<>();
		if(zone == null || zone.isBlank())
			zone = "UTC";
		
		LocalDateTime now = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of(zone));
		
		vars.put("dd", padLeft(now.getDayOfMonth(),2,'0'));
		vars.put("MM", padLeft(now.getMonthValue(),2,'0'));
		vars.put("yyyy", String.valueOf(now.getYear()));
		vars.put("HH", padLeft(now.getHour(),2,'0'));
		vars.put("mm", padLeft(now.getMinute(),2,'0'));
		vars.put("ss", padLeft(now.getSecond(),2,'0'));
		vars.put("z", padLeft(now.getDayOfYear(),3,'0'));
		
		vars.put("yyyyMMdd", vars.get("yyyy") + vars.get("MM") + vars.get("dd"));
		vars.put("HHmmss", vars.get("HH") + vars.get("mm") + vars.get("ss"));
		
		return vars;
	}

	
    public static Date dateAddHours(Date date, int hours) {
    	if(date == null) return null;
        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        localDateTime = localDateTime.plusHours(hours);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
	
	public static Date utcDate(Date date) {
		return dateAddHours(date, 12);
	}
	
	public static Integer intValue(String value) {
		if(value == null) return null;
		String intString = value.replaceAll("[^0-9]", "");
		return intString.isEmpty() ? null : Integer.parseInt(intString);
	}
	
	public static String sqlLikeString(String key) {
		if(key == null || key.isEmpty()) return "";
		if(key.contains("*") || key.contains("%")) {
			key = key.replace("*", "%");
		}
		else {
			key = "%" + key + "%";
		}
		return key;
	}

	public static Long longValue(String value) {
		if(value == null) return null;
		String intString = value.replaceAll("[^0-9]", "");
		return intString.isEmpty() ? null : Long.parseLong(intString);
	}


	public static byte[] readFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }
	
	public static String readResource(String resource) throws IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream inputStream = classLoader.getResourceAsStream(resource);
		 try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
		    StringBuilder content = new StringBuilder();
		    String line;
		    while ((line = reader.readLine()) != null) {
		      content.append(line).append("\n");
		    }
		    return content.toString();
		 }		
	}
	
	public static String weekDayToString( List<Integer> list) {
		String str = "";

		for(Integer i : list) {
			str += String.valueOf(i);
		}
		
		return str;
	}
	
	public static List<Integer> weekDayFromString( String weekdays ) {
		List<Integer> list = new ArrayList<>();
		
		if(weekdays != null && weekdays.length() > 0) {
			for(int i = 0; i < weekdays.length(); i++) {
				list.add( Integer.parseInt( String.valueOf(weekdays.charAt(i)) ) );
			}
		}
		
		return list;
	}
	
	public static Date lastDayOfMonth() {
		return localDateToDate(lastDayOfMonth(LocalDate.now()));
	}
	
	public static Date lastDayOfMonth(Date localDate) {
		return localDateToDate(lastDayOfMonth( dateToLocalDate(localDate) ));
	}
	
	public static LocalDate lastDayOfMonth(LocalDate currentDate) {
		return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
	}
	
	public static Date localDateToDate(LocalDate localDate) {
	    ZoneId defaultZone = ZoneId.systemDefault();
	    ZonedDateTime zonedDateTime = localDate.atStartOfDay(defaultZone);
	    Instant instant = zonedDateTime.toInstant();
	    Date date = Date.from(instant);
	    return date;
	}
	
	 public static LocalDate dateToLocalDate(Date date) {
	    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	 }

	 public static String convertBytesToString(long bytes) {
        double kb = 1024.0;
        double mb = kb * 1024.0;
        double gb = mb * 1024.0;
        double tb = gb * 1024.0;

        if (bytes < kb) {
            return bytes + " B";
        } else if (bytes < mb) {
            return Math.round(bytes / kb) + " KB";
        } else if (bytes < gb) {
            return Math.round(bytes / mb) + " MB";
        } else if (bytes < tb) {
            return Math.round(bytes / gb) + " GB";
        } else {
            return Math.round(bytes / tb) + " TB";
        }
    }
	
	public static <T> T[] listLimit(ArrayList<T> list, Integer limit) {
		return list.stream()
		.limit(limit)
		.toArray(t -> (T[]) new Object[limit]); // Specify array type and size
	}


	public static Date convertToDate(int ymd) {
		if (ymd < 1000000 || ymd > 99991231) {
		  throw new IllegalArgumentException("Invalid date format: Year must be between 1000 and 9999, month between 1 and 12, day between 1 and 31");
		}
		int year = ymd / 10000;
		int month = (ymd % 10000) / 100;
		int day = ymd % 100;
		
		// Use Calendar class for date construction and validation
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month - 1); // Month in Calendar starts from 0
		cal.set(Calendar.DAY_OF_MONTH, day);
		
		return cal.getTime();
	  }

	public static String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
			//URI uri = new URI(url);
        	//return uri.toASCIIString();
		}
		catch(Exception ex)  {
			return url;
		}
	}

	public static int dayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Calendar.DAY_OF_WEEK returns values from 1 (Sunday) to 7 (Saturday).
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int dayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

	public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY); // 0-23
    }

	public static boolean isImageURLValid(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD"); // Use HEAD request for efficiency
            int responseCode = connection.getResponseCode();

            // Check for successful response codes (2xx) and common image content types.
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String contentType = connection.getContentType();
                if (contentType != null && (contentType.startsWith("image/"))) {
                    return true;
                } else {                    
                    return false;
                }

            } else {
                return false;
            }

        } catch (IOException e) {
             System.out.println("Error checking image URL: " + e.getMessage());
            return false;
        }
    }

	// Ano Bissexto
	private static boolean isLeapYear(int year) {
        if (year % 4 != 0) {
            return false;
        } else if (year % 100 != 0) {
            return true;
        } else {
            return year % 400 == 0;
        }
    }

	public static int convertToJulian(String dateStr) throws IllegalArgumentException {
        if (dateStr == null || dateStr.length() != 8) {
            throw new IllegalArgumentException("Input string must be 8 characters in yyyyMMdd format");
        }
        
        try {
			return convertoToJulian(Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(4, 6)), Integer.parseInt(dateStr.substring(6, 8)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Input string contains non-numeric characters", e);
        }
    }

    private static int convertoToJulian(int year, int month, int day) {
        // Validate the date components
		if (month < 1 || month > 12 || day < 1 || day > 31) {
			throw new IllegalArgumentException("Invalid month or day value");
		}
		
		// Calculate day of year
		int[] monthDays = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		
		// Adjust for leap year if February is included
		if (isLeapYear(year)) {
			monthDays[2] = 29;
		}
		
		int dayOfYear = day;
		for (int i = 1; i < month; i++) {
			dayOfYear += monthDays[i];
		}
		
		// Julian date format: yyyyddd (where ddd is day of year, padded with leading zeros)
		return year * 1000 + dayOfYear;
    }

	public static List<Integer> intListFromString(String str, String sep) {
		List<Integer> list = new ArrayList<>();
		if(str != null && !str.isBlank()) {
			String[] parts = str.split(sep);
			for(String part : parts) {
				try {
					list.add(Integer.parseInt(part.trim()));
				}
				catch(Exception ex) {}
			}
		}
		return list;
	}
    
	public static Instant parseToInstant(String dateStr, String format) {
		if (dateStr == null || dateStr.isEmpty()) return null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		LocalDateTime ldt = LocalDateTime.parse(dateStr, formatter);
		return ldt.toInstant(ZoneOffset.UTC);
	}
}
