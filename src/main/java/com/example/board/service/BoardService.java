package com.example.board.service;

import com.example.board.entity.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void write(Board board, MultipartFile file) throws IOException {
        File newFile = createFile(file);
        file.transferTo(newFile);

        board.setFilename(newFile.getName());
        board.setFilepath("/files/" + newFile.getName());

        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board boardDetail(Integer id) {
        return boardRepository.getById(id);
    }

    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void updateBoard(Integer id, Board updateParam) {
        Board board = boardRepository.getById(id);
        board.setTitle(updateParam.getTitle());
        board.setContent(updateParam.getContent());
        log.info("{}", board);
    }

    public Page<Board> searchBoard(String keyWord, Pageable pageable) {
        return boardRepository.findByTitleContaining(keyWord, pageable);
    }

    private File createFile(MultipartFile file) {
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        return new File(projectPath, fileName);
    }


}
