package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.dto.Request.ArchiveRequest;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.archive.ArchiveNotFoundException;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.repository.ArchiveBoardRepository;
import team.nine.booknutsbackend.repository.ArchiveRepository;
import team.nine.booknutsbackend.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveBoardRepository archiveBoardRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    //아카이브 리스트 조회
    @Transactional(readOnly = true)
    public List<ArchiveResponse> allarchive(User user) {
        List<Archive> archives = archiveRepository.findAllByUser(user);
        List<ArchiveResponse> archiveResponseList = new ArrayList<>();

        for (Archive archive : archives) {
            archiveResponseList.add(ArchiveResponse.archiveResponse(archive));
        }
        return archiveResponseList;
    }

    //아카이브 생성
    public void saveArchive(ArchiveRequest archiveRequest, User user) {
        Archive archive = ArchiveRequest.newArchive(archiveRequest, user);
        archiveRepository.save(archive);
    }

    //특정 아카이브 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> findArchive(Long archiveId, User user) throws ArchiveNotFoundException {
        Archive archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new ArchiveNotFoundException("존재하지 않는 아카이브 아이디입니다."));
        List<ArchiveBoard> archiveBoards = archiveBoardRepository.findByArchive(archive);
        List<BoardResponse> boardList = new ArrayList<>();

        for (ArchiveBoard archiveBoard : archiveBoards) {
            boardList.add(BoardResponse.boardResponse(archiveBoard.getBoard(), user));
        }
        return boardList;
    }

    //아카이브에 추가
    public void addToArchive(Long archiveId, Long boardId, User user) {
        Archive archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new ArchiveNotFoundException("존재하지 않는 아카이브 아이디입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 번호입니다."));

        ArchiveBoard archiveBoard = new ArchiveBoard();
        archiveBoard.setArchive(archive);
        archiveBoard.setBoard(board);
        archiveBoardRepository.save(archiveBoard);

        boardService.updateCount(board); //게시글 카운트 데이터 업데이트
    }
}