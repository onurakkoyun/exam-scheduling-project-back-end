package com.examschedulingproject.entities.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentExamsWithDetailsDto {
	private Long id;
	private String 	courseName;
	private String examType;
	private LocalDate examDate;
	private String startTime;
	private String endTime;
	private String description;
}
