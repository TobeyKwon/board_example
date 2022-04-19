package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoadController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String boardWriteForm(Model model) {
        model.addAttribute("board", new Board());
        return "boardwrite";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board) {
        boardService.write(board);

        return "redirect:/boardlist";
    }

    @GetMapping
    public String boardList(Model model) {
        List<Board> boardList = boardService.boardList();
        model.addAttribute("boardList", boardList);
        return "boardlist";
    }

    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Integer id, Model model) {
        Board board = boardService.board_detail(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

}
