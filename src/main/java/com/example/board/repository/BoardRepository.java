package com.example.board.repository;

import com.example.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
