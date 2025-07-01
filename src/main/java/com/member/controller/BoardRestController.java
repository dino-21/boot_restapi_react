package com.member.controller;

import com.member.entity.Board;
import com.member.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

    // 게시글 전체 조회 (페이징 포함)
    @GetMapping
    public ResponseEntity<Page<Board>> getAllBoards(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.readAllPage(pageable);
        return ResponseEntity.ok(boardPage);
    }

    //데이터만 받기
    @GetMapping("/list")
    public List<Board> getBoardList() {
        return boardService.readAll();  // → 페이징 없이 전체 글만 리턴
    }

    // 게시글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoard(@PathVariable Long id) {
        Board board = boardService.read(id);
        return ResponseEntity.ok(board);
    }

    // 게시글 등록
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBoard(@RequestBody Board board) {
        boardService.register(board);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "게시글이 등록되었습니다.");
        response.put("data", board);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBoard(@PathVariable Long id, @RequestBody Board updatedBoard) {
        Board board = boardService.update(id, updatedBoard);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "게시글이 수정되었습니다.");
        response.put("data", board);

        return ResponseEntity.ok(response);
    }

    //  게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}
