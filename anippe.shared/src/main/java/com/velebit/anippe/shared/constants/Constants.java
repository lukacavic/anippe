package com.velebit.anippe.shared.constants;

public class Constants {
    public static final String DEFAULT_USER_PASSWORD = "12345678";
    public static final Integer MAX_UPLOAD_SIZE = 5 * 1024 * 1024; // 5MB

    public static class SequenceType {
        public static final String CLIENT = "CLIENT";
        public static final String USER = "USER";
        public static final String TICKET ="TICKET";

    }

    public static class Related {
        public static final Integer TICKET = 1;
        public static final Integer CLIENT = 2;
        public static final Integer PROJECT = 3;
        public static final Integer LEAD = 4;
        public static final Integer NOTE = 5;
        public static final Integer TICKET_REPLY = 6;
    }

    public static class SequenceFormat {
        public static final String Format1 = "PREFIXSEQUENCE";
        public static final String Format2 = "PREFIX-SEQUENCE";
        public static final String Format3 = "SEQUENCEPREFIX";
        public static final String Format4 = "SEQUENCE-PREFIX";
        public static final String Format5 = "YEAR/PREFIX/SEQUENCE";
        public static final String Format6 = "YEAR-PREFIX-SEQUENCE";
        public static final String Format7 = "SEQUENCE/PREFIX/YEAR";
        public static final String Format8 = "SEQUENCE-PREFIX-YEAR";
    }

    public static class EmailStatus {
        public static final Integer PREPARE_SEND = 1;
        public static final Integer SENT = 2;
        public static final Integer ERROR = 3;
    }

    public static class TicketStatus {
        public static final Integer CREATED = 1;
        public static final Integer IN_PROGRESS = 2;
        public static final Integer ANSWERED = 3;
        public static final Integer ON_HOLD = 4;
        public static final Integer CLOSED = 5;
    }

    public static class Priority {
        public static final Integer LOW = 1;
        public static final Integer NORMAL = 2;
        public static final Integer HIGH = 3;
        public static final Integer URGENT = 4;
    }

    public static class ProjectType {
        public static final Integer INTERNAL = 1;
        public static final Integer FOR_CLIENT = 2;
    }

    public static class ProjectStatus {
        public static final Integer OPEN = 1;
        public static final Integer COMPLETED = 2;
        public static final Integer HOLD = 3;
        public static final Integer CANCELED = 4;
    }

    public static class TaskStatus {
        public static final Integer CREATED = 1;
        public static final Integer IN_PROGRESS = 2;
        public static final Integer TESTING = 3;
        public static final Integer AWAITING_FEEDBACK = 4;
        public static final Integer COMPLETED = 5;
    }
}
