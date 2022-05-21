package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.exception.archive.ArchiveNotFoundException;
import team.nine.booknutsbackend.exception.board.BoardNotFoundException;
import team.nine.booknutsbackend.exception.board.NoAccessException;
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
        List<Archive> archives = archiveRepository.findAllByOwner(user);
        List<ArchiveResponse> archiveResponseList = new ArrayList<>();

        for (Archive archive : archives) {
            archiveResponseList.add(ArchiveResponse.archiveResponse(archive));
        }
        return archiveResponseList;
    }

    //아카이브 생성
    public Archive saveArchive(Archive archive) {
        return archiveRepository.save(archive);
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
    public void addToArchive(Long archiveId, Long boardId) {
        Archive archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new ArchiveNotFoundException("존재하지 않는 아카이브 아이디입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 번호입니다."));

        ArchiveBoard archiveBoard = new ArchiveBoard();
        archiveBoard.setArchive(archive);
        archiveBoard.setBoard(board);
        archiveBoard.setOwner(archive.getOwner());
        archiveBoardRepository.save(archiveBoard);

        boardService.updateCount(board); //게시글 카운트 데이터 업데이트
    }

    //아카이브 삭제
    @Transactional
    public void delete(Long archiveId, User user) throws NoAccessException {
        Archive archive = archiveRepository.findByArchiveIdAndOwner(archiveId, user)
                .orElseThrow(() -> new NoAccessException("해당 유저는 삭제 권한이 없습니다."));
        List<ArchiveBoard> archiveBoards = archiveBoardRepository.findByArchive(archive);

        for (ArchiveBoard archiveBoard : archiveBoards) {
            archiveBoardRepository.delete(archiveBoard);
            boardService.updateCount(archiveBoard.getBoard());
        }
        archiveRepository.delete(archive);
    }

    //아카이브 안 게시글 삭제
    public void deleteBoardFromArchive(Long archiveId, Long boardId) {
        Archive archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new ArchiveNotFoundException("존재하지 않는 아카이브 아이디입니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 게시글 번호입니다."));

        ArchiveBoard archiveBoard = archiveBoardRepository.findByArchiveAndBoard(archive, board);
        archiveBoardRepository.delete(archiveBoard);

        boardService.updateCount(board); //게시글 카운트 데이터 업데이트
    }

    //아카이브 조회(아카이브명, 내용, 이미지)
    @Transactional(readOnly = true)
    public Archive findByArchiveId(Long archiveId) throws ArchiveNotFoundException {
        return archiveRepository.findById(archiveId)
                .orElseThrow(() -> new BoardNotFoundException("존재하지 않는 아카이브 아이디입니다."));
    }

    //아카이브 수정
    @Transactional
    public Archive update(Archive archive, User user) throws NoAccessException {
        archiveRepository.findByArchiveIdAndOwner(archive.getArchiveId(), user)
                .orElseThrow(() -> new NoAccessException("해당 유저는 수정 권한이 없습니다."));

        return archiveRepository.save(archive);
    }
}