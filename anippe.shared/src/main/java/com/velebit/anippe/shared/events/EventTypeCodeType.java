package com.velebit.anippe.shared.events;

import com.velebit.anippe.shared.constants.Constants.EventType;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class EventTypeCodeType extends AbstractCodeType<Long, Long> {
    public static final long ID = 0L;
    private static final long serialVersionUID = 1L;

    @Override
    public Long getId() {
        return ID;
    }

    @Order(1000)
    @ClassId("8893e1e4-7b6c-46c2-8c84-42c914ec29d5")
    public static class FreeDaysCode extends AbstractCode<Long> {

        public static final Long ID = EventType.FREE_DAY;
        private static final long serialVersionUID = 1L;

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("FreeDays");
        }

        @Override
        public Long getId() {
            return ID;
        }
    }

    @Order(1000)
    @ClassId("8893e1e4-7b6c-46c2-8c84-42c914ec29d5")
    public static class VacationCode extends AbstractCode<Long> {

        public static final Long ID = EventType.VACATION;
        private static final long serialVersionUID = 2L;

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Vacation");
        }

        @Override
        public Long getId() {
            return ID;
        }
    }

    @Order(1000)
    @ClassId("8893e1e4-7b6c-46c2-8c84-42c914ec29d5")
    public static class MeetingCode extends AbstractCode<Long> {

        public static final Long ID = EventType.MEETING;
        private static final long serialVersionUID = 3L;

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Meeting");
        }

        @Override
        public Long getId() {
            return ID;
        }
    }

    @Order(1000)
    @ClassId("8893e1e4-7b6c-46c2-8c84-42c914ec29d5")
    public static class OtherCode extends AbstractCode<Long> {

        public static final Long ID = EventType.OTHER;
        private static final long serialVersionUID = 4L;

        @Override
        protected String getConfiguredText() {
            return TEXTS.get("Other");
        }

        @Override
        public Long getId() {
            return ID;
        }
    }
}
