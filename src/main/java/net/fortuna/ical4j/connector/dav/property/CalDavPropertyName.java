/**
 * Copyright (c) 2012, Ben Fortuna
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  o Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 *  o Neither the name of Ben Fortuna nor the names of any other contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.fortuna.ical4j.connector.dav.property;

import org.apache.jackrabbit.webdav.property.DavPropertyName;
import org.apache.jackrabbit.webdav.security.report.PrincipalMatchReport;
import org.apache.jackrabbit.webdav.version.report.ReportType;
import org.apache.jackrabbit.webdav.xml.Namespace;

/**
 * $Id$
 * 
 * Created on 19/11/2008
 * 
 * @author Ben
 * 
 */
public class CalDavPropertyName {

    /**
     *
     */
    public static final String PROPERTY_FILTER = "filter";
    /**
     * Purpose: Provides a human-readable description of the calendar collection. RFC : rfc4791
     */
    private static final String PROPERTY_CALENDAR_DESCRIPTION = "calendar-description";

    /**
     * Purpose: Identify the calendars that contribute to the free-busy information for the owner of the scheduling
     * https://tools.ietf.org/html/draft-desruisseaux-caldav-sched-04
     *
     * THIS PROPERTY WAS REMOVED IN DRAFT 05 AND THE OFFICIAL RFC (6638)
     */
    private static final String PROPERTY_FREE_BUSY_SET = "calendar-free-busy-set";

    /**
     * Purpose: Identifies the URL of any WebDAV collections that contain calendar collections owned by the associated
     * principal resource. RFC : rfc4791
     */
    private static final String PROPERTY_CALENDAR_HOME_SET = "calendar-home-set";

    /**
     * The property to identify a "calendar" resource-type for the collection.
     */
    private static final String PROPERTY_RESOURCETYPE_CALENDAR = "calendar";

    /**
     * Identify the URL of the scheduling Inbox collection owned by the associated principal resource.
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String PROPERTY_SCHEDULE_INBOX_URL = "schedule-inbox-URL";

    /**
     * Identify the URL of the scheduling Outbox collection owned by the associated principal resource.
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String PROPERTY_SCHEDULE_OUTBOX_URL = "schedule-outbox-URL";

    /**
     * Specifies the calendar component types (e.g., VEVENT, VTODO, etc.) that calendar object resources can contain in
     * the calendar collection.
     */
    private static final String PROPERTY_SUPPORTED_CALENDAR_COMPONENT_SET = "supported-calendar-component-set";

    /**
     * Specifies a supported component type (e.g., VEVENT, VTODO, etc.)
     */
    private static final String PROPERTY_COMPONENT = "comp";

    /**
     * The CALDAV:calendar-timezone property is used to specify the time zone the server should rely on to resolve
     * "date" values and "date with local time" values (i.e., floating time) to "date with UTC time" values.
     */
    private static final String PROPERTY_CALENDAR_TIMEZONE = "calendar-timezone";

    /**
     *
     */
    private static final String PROPERTY_SUPPORTED_CALENDAR_DATA = "supported-calendar-data";

    /**
     *
     */
    private static final String PROPERTY_MAX_RESOURCE_SIZE = "max-resource-size";

    /**
     *
     */
    private static final String PROPERTY_MIN_DATE_TIME = "min-date-time";

    /**
     *
     */
    private static final String PROPERTY_MAX_DATE_TIME = "max-date-time";

    /**
     *
     */
    private static final String PROPERTY_MAX_INSTANCES = "max-instances";

    /**
     *
     */
    private static final String PROPERTY_MAX_ATTENDEES_PER_INSTANCE = "max-attendees-per-instance";

    /**
     *
     */
    private static final String PROPERTY_CALENDAR_QUERY = "calendar-query";

    /**
     * Servers MAY reject requests to create a
     * scheduling object resource with an iCalendar "UID" property value
     * already in use by another scheduling object resource owned by the
     * same user in other calendar collections.  Servers SHOULD report
     * the URL of the scheduling object resource that is already making
     * use of the same "UID" property value in the DAV:href element.
     *
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String UNIQUE_SCHEDULING_OBJECT_RESOURCE = "unique-scheduling-object-resource";

    /**
     * All the calendar components in a
     * scheduling object resource MUST contain the same "ORGANIZER"
     * property value when present
     *
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SAME_ORGANIZER_IN_ALL_COMPONENTS = "same-organizer-in-all-components";

    /**
     * Servers MAY impose restrictions on modifications allowed by an "Organizer".
     *
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String ALLOWED_ORGANIZER_SCHEDULING_OBJECT_CHANGE = "allowed-organizer-scheduling-object-change";

    /**
     * Servers MAY impose restrictions on modifications allowed by an "Attendee",
     * subject to the allowed changes specified in Section 3.2.2.1
     *
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String ALLOWED_ATTENDEE_SCHEDULING_OBJECT_CHANGE = "allowed-attendee-scheduling-object-change";

    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String DEFAULT_CALENDAR_NEEDED = "default-calendar-needed";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String VALID_SCHEDULE_DEFAULT_CALENDAR_URL = "valid-schedule-default-calendar-URL";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String VALID_SCHEDULING_MESSAGE = "valid-scheduling-message";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String VALID_ORGANIZER = "valid-organizer";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_DELIVER = "schedule-deliver";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_DELIVER_INVITE = "schedule-deliver-invite";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_DELIVER_REPLY = "schedule-deliver-reply";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_QUERY_FREEBUSY = "schedule-query-freebusy";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_SEND = "schedule-send";
    /**
     * https://tools.ietf.org/html/rfc6638
     */
    private static final String SCHEDULE_SEND_INVITE = "schedule-send-invite";

    /**
     * The CALDAV:schedule-send-reply privilege controls the sending of
     * scheduling messages by "Attendees".
     *
     * https://tools.ietf.org/html/rfc6638#section-6.2.3
     */
    private static final String SCHEDULE_SEND_REPLY = "schedule-send-reply";

    /**
     * The CALDAV:schedule-send-freebusy privilege controls the use of the
     * POST method to submit scheduling messages that specify the scheduling
     * method "REQUEST" with a "VFREEBUSY" calendar component.
     *
     * https://tools.ietf.org/html/rfc6638#section-6.2.4
     */
    private static final String SCHEDULE_SEND_FREEBUSY = "schedule-send-freebusy";

    /**
     * Determines whether the calendar object resources in a
     *  calendar collection will affect the owner's busy time information.
     *
     * https://tools.ietf.org/html/rfc6638#section-9.1
     */
    private static final String PROPERTY_SCHEDULE_CALENDAR_TRANSP = "schedule-calendar-transp";

    /**
     * Specifies a default calendar for an "Attendee" where new
     *  scheduling object resources are created.
     *
     * https://tools.ietf.org/html/rfc6638#section-9.2
     */
    private static final String PROPERTY_SCHEDULE_DEFAULT_CALENDAR_URL = "schedule-default-calendar-URL";

    /**
     * Indicates whether a scheduling object resource has had a
     * "consequential" change made to it.
     *
     * https://tools.ietf.org/html/rfc6638#section-9.3
     */
    private static final String SCHEDULE_TAG = "schedule-tag";

    /**
     * Contains the set of responses for a POST method request (for scheduling)
     *
     * https://tools.ietf.org/html/rfc6638#section-10.1
     */
    private static final String SCHEDULE_RESPONSE = "schedule-response";

    /**
     * Contains a single response for a POST method request (for scheduling)
     *
     * https://tools.ietf.org/html/rfc6638#section-10.2
     */
    private static final String RESPONSE = "response";

    /**
     * The calendar user address that the enclosing response for a POST method request is for.
     *
     * https://tools.ietf.org/html/rfc6638#section-10.3
     */
    private static final String PROPERTY_RECIPIENT = "recipient";

    /**
     * The iTIP "REQUEST-STATUS" property value for a scheduling response.
     *
     * https://tools.ietf.org/html/rfc6638#section-10.4
     */
    private static final String PROPERTY_REQUEST_STATUS = "request-status";

    /**
     * Defines a "VAVAILABILITY" component that will be used in calculating
     * free-busy time when an iTIP free-busy request is targeted at the
     * calendar user who owns the Inbox.
     *
     * http://tools.ietf.org/html/draft-daboo-calendar-availability-03
     */
    private static final String CALENDAR_AVAIBILITY = "calendar-availability";

    /**
     * Enumerates the sets of component restrictions the server is
     * willing to allow the client to specify in MKCALENDAR or extended
     * MKCOL requests.
     *
     * http://tools.ietf.org/html/draft-daboo-caldav-extensions-01
     */
    private static final String SUPPORTED_CALENDAR_COMPONENT_SETS = "supported-calendar-component-sets";

    /**
     * A default alarm applied to "VEVENT" components whose "DTSTART" property value
     * type is "DATE-TIME"
     *
     * http://tools.ietf.org/html/draft-daboo-valarm-extensions-04
     */
    private static final String DEFAULT_ALARM_VEVENT_DATETIME = "default-alarm-vevent-datetime";

    /**
     * A default alarm applied to "VEVENT" components whose "DTSTART" property value type is "DATE"
     *
     * http://tools.ietf.org/html/draft-daboo-valarm-extensions-04
     */
    private static final String DEFAULT_ALARM_VEVENT_DATE = "default-alarm-vevent-date";

    /**
     * A default alarm applied to "VTODO" components whose "DUE" or "DTSTART"
     * property value type is "DATE-TIME"
     *
     * http://tools.ietf.org/html/draft-daboo-valarm-extensions-04
     */
    private static final String DEFAULT_ALARM_VTODO_DATETIME = "default-alarm-vtodo-datetime";

    /**
     * A default alarm applied to "VTODO" components whose "DUE" or "DTSTART"
     * property value type is "DATE", or when neither of those properties is present
     *
     * http://tools.ietf.org/html/draft-daboo-valarm-extensions-04
     */
    private static final String DEFAULT_ALARM_VTODO_DATE = "default-alarm-vtodo-date";

    /**
     * Default namespace.
     */
    public static final Namespace NAMESPACE = Namespace.getNamespace("C", "urn:ietf:params:xml:ns:caldav");
    public static final ReportType FREEBUSY_QUERY = ReportType.register("free-busy-query", NAMESPACE,
            PrincipalMatchReport.class);
    /**
     *
     */
    public static final ReportType CALENDAR_QUERY = ReportType.register("calendar-query", NAMESPACE,
            PrincipalMatchReport.class);

    /**
     * 
     */
    public static final DavPropertyName CALENDAR_DESCRIPTION = DavPropertyName.create(
            PROPERTY_CALENDAR_DESCRIPTION, NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName CALENDAR_TIMEZONE = DavPropertyName.create(
            PROPERTY_CALENDAR_TIMEZONE, NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName SUPPORTED_CALENDAR_COMPONENT_SET = DavPropertyName.create(
            PROPERTY_SUPPORTED_CALENDAR_COMPONENT_SET, NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName SUPPORTED_CALENDAR_DATA = DavPropertyName.create(
            PROPERTY_SUPPORTED_CALENDAR_DATA, NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName MAX_RESOURCE_SIZE = DavPropertyName.create(
            PROPERTY_MAX_RESOURCE_SIZE, NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName MIN_DATE_TIME = DavPropertyName.create(PROPERTY_MIN_DATE_TIME,
            NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName MAX_DATE_TIME = DavPropertyName.create(PROPERTY_MAX_DATE_TIME,
            NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName MAX_INSTANCES = DavPropertyName.create(PROPERTY_MAX_INSTANCES,
            NAMESPACE);

    /**
     * 
     */
    public static final DavPropertyName MAX_ATTENDEES_PER_INSTANCE = DavPropertyName.create(
            PROPERTY_MAX_ATTENDEES_PER_INSTANCE, NAMESPACE);

    /**
     *
     */
    public static final String PROPERTY_CALENDAR_DATA = "calendar-data";
    /**
     * 
     */
    public static final DavPropertyName CALENDAR_DATA = DavPropertyName.create(PROPERTY_CALENDAR_DATA,
            NAMESPACE);

    /**
     * Property from a draft (draft-desruisseaux-ischedule-01)
     */
    public static final DavPropertyName RECIPIENT = DavPropertyName.create(PROPERTY_RECIPIENT,
            NAMESPACE);

    /**
     * Property from a draft (draft-desruisseaux-ischedule-01)
     */
    public static final DavPropertyName REQUEST_STATUS = DavPropertyName.create(
            PROPERTY_REQUEST_STATUS, NAMESPACE);

    public static final DavPropertyName COMPONENT = DavPropertyName.create(PROPERTY_COMPONENT,
            NAMESPACE);

    /**
     * Purpose: Identify the calendars that contribute to the free-busy information for the owner of the scheduling
     * https://tools.ietf.org/html/draft-desruisseaux-caldav-sched-04
     * 
     *  THIS PROPERTY WAS REMOVED IN DRAFT 05 AND THE OFFICIAL RFC (6638)
     *  
     */
    public static final DavPropertyName FREE_BUSY_SET = DavPropertyName.create(PROPERTY_FREE_BUSY_SET,
            NAMESPACE);

    /**
     * Purpose: Identifies the URL of any WebDAV collections that contain calendar collections owned by the associated
     * principal resource. RFC : rfc4791
     */
    public static final DavPropertyName CALENDAR_HOME_SET = DavPropertyName.create(PROPERTY_CALENDAR_HOME_SET,
            NAMESPACE);

    /**
     * Identify the calendar addresses of the associated principal resource.
     * https://tools.ietf.org/html/rfc6638
     */
    public static final String PROPERTY_USER_ADDRESS_SET = "calendar-user-address-set";
    /**
     * Identify the calendar addresses of the associated principal resource.
     * http://tools.ietf.org/html/rfc6638
     */
    public static final DavPropertyName USER_ADDRESS_SET = DavPropertyName.create(
            PROPERTY_USER_ADDRESS_SET, NAMESPACE);

    /**
     * Identifies the calendar user type of the associated principal resource. Its value is the same as the iCalendar "CUTYPE".
     * https://tools.ietf.org/html/rfc6638
     */
    public static final String PROPERTY_USER_TYPE= "calendar-user-type";
    /**
     * Identifies the calendar user type of the associated principal resource. Its value is the same as the iCalendar "CUTYPE".
     * https://tools.ietf.org/html/rfc6638
     */
    public static final DavPropertyName USER_TYPE = DavPropertyName.create(
            PROPERTY_USER_TYPE, NAMESPACE);

    /**
     * Identify the URL of the scheduling Inbox collection owned by the associated principal resource.
     * http://tools.ietf.org/html/rfc6638
     */
    public static final DavPropertyName SCHEDULE_INBOX_URL = DavPropertyName.create(
            PROPERTY_SCHEDULE_INBOX_URL, NAMESPACE);

    /**
     * Identify the URL of the scheduling Outbox collection owned by the associated principal resource.
     * http://tools.ietf.org/html/rfc6638
     */
    public static final DavPropertyName SCHEDULE_OUTBOX_URL = DavPropertyName.create(
            PROPERTY_SCHEDULE_OUTBOX_URL, NAMESPACE);

    /**
     * Determines whether the calendar object resources in a calendar collection will affect the owner's freebusy.
     */
    public static final DavPropertyName SCHEDULE_CALENDAR_TRANSP = DavPropertyName.create(
            PROPERTY_SCHEDULE_CALENDAR_TRANSP, NAMESPACE);

    /**
     * Specifies a default calendar for an attendee that will automatically have new scheduling messages deposited into
     * it when they arrive.
     */
    public static final DavPropertyName SCHEDULE_DEFAULT_CALENDAR_URL = DavPropertyName.create(
            PROPERTY_SCHEDULE_DEFAULT_CALENDAR_URL, NAMESPACE);

}
