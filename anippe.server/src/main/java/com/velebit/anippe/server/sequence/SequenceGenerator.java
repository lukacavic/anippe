package com.velebit.anippe.server.sequence;

import com.velebit.anippe.shared.constants.Constants;
import org.eclipse.scout.rt.platform.Bean;
import org.eclipse.scout.rt.platform.util.StringUtility;

import java.time.LocalDate;

@Bean
public class SequenceGenerator {

	public String generate(Integer sequence, String prefix, String formatType, Integer paddingZero) {
		if (formatType == null) {
			return defaultFormat(sequence, prefix);
		}

		// Nadodaj nule prema parametru.
		String sequenceCode = sequence.toString();
		if (paddingZero != null && paddingZero > 0) {
			sequenceCode = String.format("%0" + paddingZero + "d", sequence);
		}

		String value = "";

		switch (formatType) {
			case Constants.SequenceFormat.Format1:
				value = prefix + sequenceCode;
				break;

			case Constants.SequenceFormat.Format2:
				value = prefix + "-" + sequenceCode;
				break;

			case Constants.SequenceFormat.Format3:
				value = sequenceCode + prefix;
				break;

			case Constants.SequenceFormat.Format4:
				value = sequence + prefix;
				break;
			case Constants.SequenceFormat.Format5:
				value = LocalDate.now().getYear() + "/" + prefix + "/" + sequenceCode;
				break;

			case Constants.SequenceFormat.Format6:
				value = LocalDate.now().getYear() + "-" + prefix + "-" + sequenceCode;
				break;

			case Constants.SequenceFormat.Format7:
				value = sequenceCode + "/" + prefix + "/" + LocalDate.now().getYear();
				break;

			case Constants.SequenceFormat.Format8:
				value = sequenceCode + "-" + prefix + "-" + LocalDate.now().getYear();
				break;

			default:
				value = defaultFormat(sequence, prefix);
				break;
		}

		return value;
	}

	private String defaultFormat(Integer sequence, String prefix) {
		return StringUtility.join(prefix, sequence.toString());
	}

}
