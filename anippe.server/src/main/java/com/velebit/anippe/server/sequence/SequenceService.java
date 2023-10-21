package com.velebit.anippe.server.sequence;

import com.velebit.anippe.server.ServerSession;
import com.velebit.anippe.shared.beans.Sequence;
import com.velebit.anippe.shared.constants.Constants;
import com.velebit.anippe.shared.sequence.ISequenceService;
import org.eclipse.scout.rt.platform.holders.IntegerHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.time.LocalDate;

public class SequenceService implements ISequenceService {

	public synchronized Integer generateSequence(String identifier, String code) {

		Integer sequenceValue = 0;

		// Prema identifikatoru gledamo da li postoji sekvenca
		Sequence existing = getExistingSequence(identifier, code);

		// Ako postoji, vracamo vrijednost + 1
		if (existing.getSequence() != null) {
			incrementSequence(identifier, code);

			sequenceValue = existing.getSequence() + 1;
		} else {
			// Ako ne postoji, kreiramo novu i vraćamo 1 kao pocetak.
			sequenceValue = createSequence(identifier, code);
		}

		return sequenceValue;
	}

	private static Integer createSequence(String identifier, String code) {
		IntegerHolder sequenceCount = new IntegerHolder();
		String stmt = "INSERT INTO sequence (identifier, sequence, code, organisation_id) VALUES (:identifier, 1, :code, :organisationId) RETURNING sequence INTO :sequenceCount";
		SQL.selectInto(stmt, new NVPair("identifier", identifier), new NVPair("code", code), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("sequenceCount", sequenceCount));

		return sequenceCount.getValue();
	}

	private static void incrementSequence(String identifier, String code) {
		String stmt = "UPDATE sequence SET sequence = sequence + 1 WHERE identifier = :identifier AND code = :code AND organisation_id = :organisationId";
		SQL.update(stmt, new NVPair("identifier", identifier), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()), new NVPair("code", code));
	}

	private static Sequence getExistingSequence(String identifier, String code) {
		Sequence sequence = new Sequence();
		String stmt = "SELECT identifier, sequence, code FROM sequence WHERE identifier = :identifier AND code = :code AND organisation_id = :organisationId INTO :{sequence.identifier},:{sequence.sequence}, :{sequence.code}";
		SQL.selectInto(stmt, new NVPair("sequence", sequence), new NVPair("identifier", identifier), new NVPair("code", code), new NVPair("organisationId", ServerSession.get().getCurrentOrganisation().getId()));

		return sequence;
	}

	@Override
	public Integer getSequence(String identifier, String code) {
		return generateSequence(identifier, code);
	}

	@Override
	public Integer getSequenceYearly(String identifier, String code) {
		return generateSequence(identifier, code + LocalDate.now().getYear());
	}

	@Override
	public Integer getSequenceMonthlyYearly(String identifier, String code) {
		return generateSequence(identifier, code + LocalDate.now().getYear() + "/" + LocalDate.now().getMonthValue());
	}

	@Override
	public Integer getSequence(String identifier, String code, String formatType) {
		switch (formatType) {
			case Constants.SequenceFormat.Format1:
				return getSequence(identifier, code);
			case Constants.SequenceFormat.Format2:
				return getSequence(identifier, code);
			case Constants.SequenceFormat.Format3:
				return getSequence(identifier, code);
			case Constants.SequenceFormat.Format4:
				return getSequence(identifier, code);
			case Constants.SequenceFormat.Format5:
				return getSequenceYearly(identifier, code);
			case Constants.SequenceFormat.Format6:
				return getSequenceYearly(identifier, code);
			case Constants.SequenceFormat.Format7:
				return getSequenceYearly(identifier, code);
			case Constants.SequenceFormat.Format8:
				return getSequenceYearly(identifier, code);
			default:
				break;
		}

		return null;
	}

}
