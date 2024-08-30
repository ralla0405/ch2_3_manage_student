package org.fastcampus.student_management.application.course;

import java.util.List;
import org.fastcampus.student_management.application.course.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.domain.Course;
import org.fastcampus.student_management.domain.DayOfWeek;
import org.fastcampus.student_management.domain.Student;
import org.fastcampus.student_management.repo.CourseRepository;

public class CourseService {
  private final CourseRepository courseRepository;
  private final StudentService studentService;

  public CourseService(CourseRepository courseRepository, StudentService studentService) {
    this.courseRepository = courseRepository;
    this.studentService = studentService;
  }

  public void registerCourse(CourseInfoDto courseInfoDto) {
    Student student = studentService.getStudent(courseInfoDto.getStudentName());
    Course course = new Course(student, courseInfoDto.getCourseName(), courseInfoDto.getFee(), courseInfoDto.getDayOfWeek(), courseInfoDto.getCourseTime());
    courseRepository.save(course);
  }

  public List<CourseInfoDto> getCourseDayOfWeek(DayOfWeek dayOfWeek) {
    // 특정 요일에 수업이 있는 수업 리스트 조회 단, 학생 상태가 비활성화 상태이면 수업을 반환해서는 안 됨
    return courseRepository.getCourseDayOfWeek(dayOfWeek)
        .stream()
        .map(CourseInfoDto::new).toList();
  }

  public void changeFee(String studentName, int fee) {
    // 특정 학생의 수강료를 변경 시키면 특정 학생 수업 전체에 적용 되어야 함
    courseRepository.getCourseListByStudent(studentName).forEach(course -> {
      Student student = studentService.getStudent(course.getStudentName());
      Course updatedCourse = new Course(
          student,
          course.getCourseName(),
          fee,
          course.getDayOfWeek(),
          course.getCourseTime());
        courseRepository.save(updatedCourse);
    });
  }
}
