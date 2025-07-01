package com.member.service;

import com.member.entity.Board;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Log4j2
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    // 등록 테스트
    @Test
    void testRegister() {
        Board board = new Board();
        board.setTitle("테스트 제목");
        board.setContent("테스트 내용입니다.");
        board.setWriter("손오공");

        boardService.register(board);
        log.info("등록 완료: {}", board);
        assertThat(board.getId()).isNotNull();
    }

    // 단건 조회 테스트
    @Test
    void testRead() {
        Board board = boardService.read(1L);
        log.info("조회 결과: {}", board);
        assertThat(board).isNotNull();
    }

    // 전체 조회 테스트
    @Test
    void testReadAll() {
        List<Board> list = boardService.readAll();
        list.forEach(board -> log.info("> {}", board));
        assertThat(list.size()).isGreaterThanOrEqualTo(0);
    }

    // 삭제 테스트
    @Test
    void testDelete() {
        boardService.delete(1L);
        log.info("1번 게시글 삭제 시도 완료");
    }

    // 페이징 테스트
    @Test
    void testReadAllPage() {
        var pageable = PageRequest.of(0, 3);
        var page = boardService.readAllPage(pageable);
        page.getContent().forEach(board -> log.info("페이지 항목> {}", board));
        assertThat(page.getContent().size()).isLessThanOrEqualTo(3);
    }

    // 수정 테스트
    @Test
    void testUpdate() {
        // 먼저 게시글 등록
        Board board = new Board();
        board.setTitle("수정 전 제목");
        board.setContent("수정 전 내용");
        board.setWriter("원래작성자");
        boardService.register(board);

        Long id = board.getId();

        // 수정용 객체 생성
        Board updatedBoard = new Board();
        updatedBoard.setTitle("수정된 제목");
        updatedBoard.setContent("수정된 내용");
        updatedBoard.setWriter("변경된작성자");

        // 수정 수행
        boardService.update(id, updatedBoard);

        // 다시 조회하여 검증
        Board result = boardService.read(id);
        log.info("수정 결과: {}", result);

        assertThat(result.getTitle()).isEqualTo("수정된 제목");
        assertThat(result.getContent()).isEqualTo("수정된 내용");
        assertThat(result.getWriter()).isEqualTo("변경된작성자");
    }
}
