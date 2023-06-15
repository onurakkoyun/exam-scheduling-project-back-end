package com.examschedulingproject.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examschedulingproject.business.abstracts.IClassRoomService;
import com.examschedulingproject.core.utilities.results.DataResult;
import com.examschedulingproject.core.utilities.results.ErrorResult;
import com.examschedulingproject.core.utilities.results.Result;
import com.examschedulingproject.core.utilities.results.SuccessDataResult;
import com.examschedulingproject.core.utilities.results.SuccessResult;
import com.examschedulingproject.dataAccess.abstracts.IClassRoomDao;
import com.examschedulingproject.entities.concretes.ClassRoom;

@Service
public class ClassRoomManager implements IClassRoomService{
	
	private IClassRoomDao classRoomDao;
	
	@Autowired
	public ClassRoomManager(IClassRoomDao classRoomDao) {
		super();
		this.classRoomDao = classRoomDao;
	}

	@Override
	public Result add(ClassRoom classRoom) {
		this.classRoomDao.save(classRoom);
		return new SuccessResult("Classroom added.");
	}

	@Override
	public Result delete(Long id) {
		if(!classRoomDao.existsById(id)){
            return new ErrorResult("Classroom not found!");
        }
		this.classRoomDao.deleteById(id);
		return new SuccessResult("Classroom with id "+id+" has been deleted success.");
	}

	@Override
	public DataResult<List<ClassRoom>> getAllClassRoom() {
		return new SuccessDataResult<List<ClassRoom>>
		(this.classRoomDao.findAll(), "Classrooms listed.");
	}

}
