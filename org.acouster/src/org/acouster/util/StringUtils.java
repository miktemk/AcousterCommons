package org.acouster.util;

/** What .NET has that java does not... */
public class StringUtils
{
	/** immitates .NET */
	public static boolean isNullOrEmpty(String snd) {
		if (snd == null)
			return true;
		if ("".equals(snd))
			return true;
		return false;
	}
	/** Immitates javascript int parser */
	public static int parseInt(String num)
	{
		if (num == null)
			return 0;
		num = num.trim();
		if (num.startsWith("0x"))
			return parseHexInt(num);
		try {
			return (int)Long.parseLong(num);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	public static int parseHexInt(String hex)
	{
		if (hex.startsWith("0x"))
			hex = hex.substring(2);
		try {
			return (int)Long.parseLong(hex, 16);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}
	public static String intToHexPad(int rgb)
	{
		return "0x" + Integer.toHexString(rgb);
	}
	public static boolean equals(String x, String y) {
		if (x == null && y == null)
			return true;
		if (x == null && y != null)
			return false;
		if (x != null && y == null)
			return false;
		return x.equals(y);
	}
	public static boolean equalsIgnoreCase(String x, String y) {
		if (x == null && y == null)
			return true;
		if (x == null && y != null)
			return false;
		if (x != null && y == null)
			return false;
		return x.equalsIgnoreCase(y);
	}
}
