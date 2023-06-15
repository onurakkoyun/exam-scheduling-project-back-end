package com.examschedulingproject.business.abstracts;

import java.util.List;

import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.entities.concretes.ClassRoom;

public interface IClassRoomService {

	Result add(ClassRoom classRoom);
	Result delete(Long id);
	DataResult<List<ClassRoom>> getAllClassRoom();
}
