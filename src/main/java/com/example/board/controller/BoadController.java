package com.example.board.controller;

import com.example.board.entity.Board;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoadController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String boardWriteForm(Model model) {
        log.info("BoadController.boardWriteForm");
        model.addAttribute("board", new Board());
        return "boardwrite";
    }

    @PostMapping("/write")
    public String write(@ModelAttribute Board board, MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        log.info("BoadController.write");
        boardService.write(board, file);
        redirectAttributes.addAttribute("id", board.getBoardId());
        redirectAttributes.addAttribute("message", "등록완료!!");
        return "redirect:/board/{id}";
    }

    @GetMapping
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "BoardId", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword) {
        log.info("BoadController.boardlist");

        Page<Board> boardList = null;
        if(searchKeyword == null)
            boardList = boardService.boardList(pageable);
        else
            boardList = boardService.searchBoard(searchKeyword, pageable);

        int nowPage = boardList.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, boardList.getTotalPages());

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/{id}")
    public String boardDetail(@PathVariable Integer id, String message, Model model) {
        log.info("BoadController.boardDetail");
        Board board = boardService.boardDetail(id);
        model.addAttribute("board", board);
        model.addAttribute("message", message);
        return "board_detail";
    }

    @RequestMapping("/{id}/delete")
    public String boardDelete(@PathVariable Integer id) {
        log.info("BoadController.boardDelete, id={}", id);
        boardService.boardDelete(id);
        return "redirect:/board";
    }

    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Integer id, Model model) {
        log.info("BoadController.updateForm, id={}", id);
        Board board = boardService.boardDetail(id);
        model.addAttribute("board", board);
        return "board_update";
    }

    @PostMapping("/{id}/update")
    public String boardUpdate(@PathVariable Integer id, @ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        log.info("BoadController.boardUpdate, id={}", id);
        boardService.updateBoard(id, board);
        redirectAttributes.addAttribute("id", id);
        redirectAttributes.addAttribute("message", "수정완료!!");
        return "redirect:/board/{id}";
    }
}
