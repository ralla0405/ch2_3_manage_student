package org.fastcampus.student_management.domain;

import java.util.List;

public class CourseList {
    private final List<Course> courses;

    public CourseList(List<Course> courses) {
        this.courses = courses;
    }

    public void changeFee(int fee) {
        for (Course course : courses) {
            if (isWeekend(course.getDayOfWeek())) {
                course.changeFee((int) (fee * 1.5));
                continue;
            }
            course.changeFee(fee);
        }
    }

    private boolean isWeekend(DayOfWeek dayOfWeek) {
        return dayOfWeek.equals(DayOfWeek.SATURDAY) || dayOfWeek.equals(DayOfWeek.SUNDAY);
    }
}
