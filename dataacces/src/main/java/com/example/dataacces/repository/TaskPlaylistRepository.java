package com.example.dataacces.repository;

import com.example.dataacces.entity.TaskPlayList;
import com.example.dataacces.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskPlaylistRepository extends JpaRepository<TaskPlayList, Long> {

    List<TaskPlayList> findTaskCategoriesById(Long id);

    List<TaskPlayList> findByUser_Id(Long userId);

    TaskPlayList findByTitleAndUser_Id(String title, Long userId);

}
