package com.member.controller;

import com.member.entity.Board;
import com.member.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "id")); // ID 역순 정렬
        Page<Board> boardPage = boardService.readAllPage(pageable);
        model.addAttribute("boardPage", boardPage);
        return "board/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/newForm"; // 이게 newForm.html로 가는 경로
    }

    @PostMapping("/new")
    public String create(@ModelAttribute Board board) {
        board.setCreatedAt(LocalDateTime.now()); // 등록일 직접 지정
        boardService.register(board);
        return "redirect:/board/list";
    }

    @GetMapping("/edit/{id}")
    public String updateForm(@PathVariable("id") String id, Model model) {
        Long boardId = Long.parseLong(id);
        Board board = boardService.read(boardId);
        model.addAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute Board board) {
        log.info("Updating board: " + board);
        boardService.register(board);
        return "redirect:/board/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        Long boardId = Long.parseLong(id);
        boardService.delete(boardId);
        return "redirect:/board/list";
    }
}
