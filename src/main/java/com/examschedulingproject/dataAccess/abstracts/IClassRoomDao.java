package com.examschedulingproject.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.examschedulingproject.entities.concretes.ClassRoom;

public interface IClassRoomDao extends JpaRepository<ClassRoom, Long>{

}
