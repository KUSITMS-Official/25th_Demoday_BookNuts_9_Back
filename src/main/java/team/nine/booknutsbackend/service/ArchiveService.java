package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Series.Series;
import team.nine.booknutsbackend.domain.Series.SeriesBoard;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.archive.Archive;
import team.nine.booknutsbackend.domain.archive.ArchiveBoard;
import team.nine.booknutsbackend.dto.Request.ArchiveRequest;
import team.nine.booknutsbackend.dto.Request.SeriesRequest;
import team.nine.booknutsbackend.dto.Response.ArchiveResponse;
import team.nine.booknutsbackend.dto.Response.BoardResponse;
import team.nine.booknutsbackend.dto.Response.SeriesResponse;
import team.nine.booknutsbackend.exception.archive.ArchiveNotFoundException;
import team.nine.booknutsbackend.exception.series.SeriesNotFoundException;
import team.nine.booknutsbackend.repository.ArchiveBoardRepository;
import team.nine.booknutsbackend.repository.ArchiveRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ArchiveService {

    private final ArchiveRepository archiveRepository;
    private final ArchiveBoardRepository archiveBoardRepository;
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

    //아카이브 추가
    public void saveArchive(ArchiveRequest archiveRequest, User user) {
        Archive archive = ArchiveRequest.newArchive(archiveRequest, user);
        archiveRepository.save(archive);

//        ArchiveBoard archiveBoard = new ArchiveBoard();
//        archiveBoard.setArchive(archive);
//        archiveBoard.setBoard(boardService.findBoard(archiveRequest.getBoardId()));
//        archiveBoardRepository.save(archiveBoard);
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
}
