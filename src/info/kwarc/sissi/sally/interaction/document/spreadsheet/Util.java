package info.kwarc.sissi.sally.interaction.document.spreadsheet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	
	public static RangeCoordinates convertRange(String range) {
		Pattern p = Pattern.compile("([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)");
		Matcher m = p.matcher(range);

		if (m.find()) {
			return new RangeCoordinates(Integer.parseInt(m.group(2))-1, convertRangeCharacter(m.group(1)), Integer.parseInt(m.group(4))-1, convertRangeCharacter(m.group(3)));
		} else
			return null;

	}
	
	public static CellSpaceInformation convertCellPosition(String position)  {
		Pattern p = Pattern.compile("([A-Z]+)([0-9]+)");
		Matcher m = p.matcher(position);

		if (m.find()) {
			return new CellSpaceInformation(Integer.parseInt(m.group(2))-1, convertRangeCharacter(m.group(1)));
		} else
			return null;	
	}
	
	public static int convertRangeCharacter(String str) {
		int index = 0;
		for (int i = 1; i <= str.length(); i++)
			index += (Character.getNumericValue(str.charAt(i-1)) - Character.getNumericValue('A') + 1) * Math.pow(26, str.length() - i);
		return (index-1);
	}
	
	
	public static CellInformation[][] extendArray(CellInformation[] array, int intoDimension) {
		CellInformation[][] extendedArray;
		
		if (intoDimension == 1) {
			extendedArray = new CellInformation[array.length][1];
			for (int i = 0; i < array.length; i++)
				extendedArray[i][0] = array[i];
		} else if (intoDimension == 2) {
			extendedArray = new CellInformation[1][array.length];
			for (int i = 0; i < array.length; i++)
				extendedArray[0][i] = array[i];
		} else
			throw new java.lang.IllegalArgumentException("Wrong parameter intoDimension. Must be 1 or 2");
		return extendedArray;
	}
	
	public static Boolean isHorizontal(CellInformation[] cellInformation) {
		if (cellInformation[0].getCellCoordinates().getRow() == cellInformation[cellInformation.length-1].getCellCoordinates().getRow())
			return true;
		else
			return false;
	}
	
	public static <T> Boolean forall(java.util.Iterator<T> it, Condition<T> condition) {
		Boolean result = true;
		while (result && it.hasNext()) {
			if (!condition.satisfied(it.next()))
				result = false;
		}
		return result;
	}
	
}
