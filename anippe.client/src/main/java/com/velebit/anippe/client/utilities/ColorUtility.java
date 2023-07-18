package com.velebit.anippe.client.utilities;

import com.velebit.anippe.shared.constants.ColorConstants;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.util.StringUtility;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.regex.Pattern;

@Bean
public class ColorUtility {
	public static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^(\\#|0X|0x)?([0-9A-Fa-f]{6})$");
	protected static final Pattern RGB_COLOR_PATTERN = Pattern.compile("^([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})[\\-\\,\\;\\/\\\\\\s]{1}([0-9]{1,3})$");

	public String getConstantValue(String color, int level) {
		String value = "";
		Field colorField;
		try {
			colorField = ColorConstants.class.getDeclaredField(color + level);
			colorField.setAccessible(true);
			value = (String) colorField.get(null);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return value;
	}

	private boolean isDarkMode() {
		//return !ClientSession.get().getCurrentUser().getThemeType().equalsIgnoreCase("default");
        return false;
	}

	public String getGreen() {
		return isDarkMode() ? ColorConstants.Green.Green5 : ColorConstants.Green.Green1;
	}

	public String getOrange() {
		return isDarkMode() ? ColorConstants.Orange.Orange5 : ColorConstants.Orange.Orange1;
	}

	public String getRed() {
		return isDarkMode() ? ColorConstants.Red.Red5 : ColorConstants.Red.Red1;
	}

	public String hexToRgba(String hex, double opacity) {
		if (StringUtility.isNullOrEmpty(hex)) {
			return "rgba(255,255,255,1)";
		}

		int r = Integer.valueOf(hex.substring(1, 3), 16);
		int g = Integer.valueOf(hex.substring(3, 5), 16);
		int b = Integer.valueOf(hex.substring(5, 7), 16);

		return "rgba(" + r + "," + g + "," + b + "," + opacity + ")";
	}

    public String randomHex() {
        Random random = new Random();
        int nextInt = random.nextInt(0xffffff + 1);

        return String.format("#%06x", nextInt);
    }
}
