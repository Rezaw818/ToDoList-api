package com.example.dataacces.repository;

import com.example.dataacces.entity.Task;
import com.example.dataacces.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task , Long> {

    List<Task> findByPlayList_Id(Long playListId);
}
