package com.member.service;

import com.member.entity.Board;
import com.member.repository.BoardRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 등록
    public void register(Board board) {
        board.setCreatedAt(LocalDateTime.now());  // 등록일 자동 세팅
        boardRepository.save(board);
    }

    // 수정
    public Board update(Long id, Board updatedBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글을 찾을 수 없습니다: " + id));

        board.setTitle(updatedBoard.getTitle());
        board.setContent(updatedBoard.getContent());
        board.setWriter(updatedBoard.getWriter());
        board.setCreatedAt(LocalDateTime.now()); // 수정 시점으로 갱신

        return boardRepository.save(board);
    }


    // 단건 조회
    public Board read(Long boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found with id: " + boardId));
    }

    // 전체 조회
    public List<Board> readAll() {
        return boardRepository.findAllByOrderByCreatedAtDesc();
    }

    // 페이징 조회
    public Page<Board> readAllPage(Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());
        return boardRepository.findAll(pageable);
    }

    // 삭제
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }
}
