package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
	@Select("select * from course where course_id = #{id}")
	@Results(value= {
			@Result(property="course_id", column="course_id"),
			@Result(property="name", column="name"),
			@Result(property="credits", column="credits"),
			@Result(property="students", column="course_id",
					javaType = List.class,
					many=@Many(select="selectCourseMember"))
	})
	CourseModel selectCourseById(@Param("id") String id);
	
	@Select("SELECT S.npm, S.name FROM student S, studentcourse SC WHERE S.npm=SC.npm and SC.course_id = #{id}")
	StudentModel selectCourseMember(@Param("id") String id);
	
	
    @Select("select npm, name, gpa from student where npm = #{npm}")
    @Results(value= {
    	@Result(property="npm", column="npm"),
    	@Result(property="name", column="name"),
    	@Result(property="gpa", column="gpa"),
    	@Result(property="courses", column="npm",
    			javaType = List.class,
    			many=@Many(select="selectCourses"))
    })
    StudentModel selectStudent (@Param("npm") String npm);
    
    
    @Select("select npm, name, gpa from student")
    @Results(value= {
    	@Result(property="npm", column="npm"),
    	@Result(property="name", column="name"),
    	@Result(property="gpa", column="gpa"),
    	@Result(property="courses", column="npm",
    			javaType = List.class,
    			many=@Many(select="selectCourses"))
    })
    List<StudentModel> selectAllStudents();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);
    
    @Delete("DELETE FROM student where npm = #{npm}")
    void deleteStudent (String npm);
    
    @Update("UPDATE student SET name = #{name}, npm = #{npm}, gpa= #{gpa} WHERE npm = #{npm}")
    void updateStudent (StudentModel student);
    
    @Select("select course.course_id, name, credits "+
    		"from studentcourse join course "+
    		"on studentcourse.course_id = course.course_id "+
    		"where studentcourse.npm = #{npm}")
    List<CourseModel> selectCourses(@Param("npm") String npm);
  
}

