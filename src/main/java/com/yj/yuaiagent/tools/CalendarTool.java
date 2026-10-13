package com.yj.yuaiagent.tools;

import com.yj.yuaiagent.domain.CalendarEvent;
import com.yj.yuaiagent.service.CalendarEventService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日程管理工具
 */
@Component
public class CalendarTool {

    @Resource
    private CalendarEventService calendarEventService;

    private static final Long CURRENT_USER_ID = 1L; // TODO 从上下文取

    @Tool(description = "Create a calendar event")
    public String createEvent(
            @ToolParam(description = "Event title") String title,
            @ToolParam(description = "Start time, format yyyy-MM-dd HH:mm:ss") String startTime,
            @ToolParam(description = "End time, format yyyy-MM-dd HH:mm:ss. Optional, defaults to 30 minutes after start time") String endTime,
            @ToolParam(description = "Location. Optional") String location) {
        try {
            boolean ok = calendarEventService.createEvent(
                    CURRENT_USER_ID, title, startTime, endTime, location);
            return ok ? "Event created successfully" : "Failed to create event";
        } catch (Exception e) {
            return "Failed to create event: " + e.getMessage();
        }
    }

    @Tool(description = "Query calendar events for a specific day")
    public String listEvents(
            @ToolParam(description = "Date, format yyyy-MM-dd") String date) {
        try {
            List<CalendarEvent> events =
                    calendarEventService.listByDate(CURRENT_USER_ID, date);

            if (events.isEmpty()) {
                return "No events on " + date;
            }

            StringBuilder sb = new StringBuilder("Events on " + date + ":\n");
            for (CalendarEvent e : events) {
                sb.append(String.format(
                        "- [%s - %s] %s%s (ID=%d)\n",
                        e.getStartTime().substring(11, 16),
                        e.getEndTime().substring(11, 16),
                        e.getTitle(),
                        e.getLocation() != null ? " @ " + e.getLocation() : "",
                        e.getId()
                ));
            }
            return sb.toString();
        } catch (Exception e) {
            return "Failed to query events: " + e.getMessage();
        }
    }

    @Tool(description = "Update an existing calendar event")
    public String updateEvent(
            @ToolParam(description = "Event ID") Long id,
            @ToolParam(description = "New title") String title,
            @ToolParam(description = "New start time, format yyyy-MM-dd HH:mm:ss") String startTime,
            @ToolParam(description = "New end time, format yyyy-MM-dd HH:mm:ss. Optional, defaults to 30 minutes after start time") String endTime,
            @ToolParam(description = "New location. Optional") String location) {
        try {
            boolean ok = calendarEventService.updateEvent(
                    CURRENT_USER_ID, id, title, startTime, endTime, location);
            return ok ? "Event updated successfully" : "Failed to update event";
        } catch (Exception e) {
            return "Failed to update event: " + e.getMessage();
        }
    }

    @Tool(description = "Cancel a calendar event by ID")
    public String deleteEvent(
            @ToolParam(description = "Event ID") Long id) {
        try {
            boolean ok = calendarEventService.cancelEvent(CURRENT_USER_ID, id);
            return ok ? "Event cancelled successfully" : "Failed to cancel event";
        } catch (Exception e) {
            return "Failed to cancel event: " + e.getMessage();
        }
    }

    @Tool(description = "Check whether a time range conflicts with existing events")
    public String checkConflict(
            @ToolParam(description = "Start time, format yyyy-MM-dd HH:mm:ss") String startTime,
            @ToolParam(description = "End time, format yyyy-MM-dd HH:mm:ss") String endTime) {
        try {
            List<CalendarEvent> conflicts = calendarEventService.checkConflict(
                    CURRENT_USER_ID, startTime, endTime, null);

            if (conflicts.isEmpty()) {
                return "No conflict in this time range.";
            }

            StringBuilder sb = new StringBuilder("Conflicts found:\n");
            for (CalendarEvent e : conflicts) {
                sb.append(String.format(
                        "- [%s - %s] %s (ID=%d)\n",
                        e.getStartTime().substring(11, 16),
                        e.getEndTime().substring(11, 16),
                        e.getTitle(),
                        e.getId()
                ));
            }
            return sb.toString();
        } catch (Exception e) {
            return "Failed to check conflict: " + e.getMessage();
        }
    }
}