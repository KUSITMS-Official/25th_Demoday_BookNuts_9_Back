package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.nine.booknutsbackend.domain.Discussion;
import team.nine.booknutsbackend.exception.Discussion.RoomNotFoundException;
import team.nine.booknutsbackend.repository.DiscussionRepository;

@RequiredArgsConstructor
@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;

    //토론장 개설
    @Transactional
    public Discussion createRoom(Discussion newRoom) {
        return discussionRepository.save(newRoom);
    }

    //토론장 참여 가능 여부
    @Transactional(readOnly = true)
    public Boolean joinRoomCheck(Long roomId, int opinion) {
        Discussion room = find(roomId);
        int status = room.getStatus();
        int sideUser = room.getMaxUser() / 2;
        int curYesUser = room.getCurYesUser();
        int curNoUser = room.getCurNoUser();

        if(status > 0) return false; //0 : 토론 대기 중, 1 : 토론 진행 중, 2 : 토론 종료
        if(opinion == 0) return sideUser > curNoUser; //'반대'로 참여
        else return sideUser > curYesUser; //'찬성'으로 참여
    }

    //토론장 조회
    @Transactional(readOnly = true)
    public Discussion find(Long roomId){
        return discussionRepository.findById(roomId)
                .orElseThrow(() -> new RoomNotFoundException("존재하지 않는 토론장 아이디입니다."));
    }

    //토론 참여하기 - 참여자수 업데이트
    @Transactional
    public void updateJoin(Long roomId, int opinion) {
        Discussion room = find(roomId);

        if(opinion == 0) room.setCurNoUser(room.getCurNoUser() + 1); //'반대'로 참여
        else room.setCurYesUser(room.getCurYesUser() + 1); //'찬성'으로 참여

        discussionRepository.save(room);
    }

    //토론 나가기 - 참여자수 업데이트
    @Transactional
    public void updateExit(Long roomId, int opinion) {
        Discussion room = find(roomId);

        if(opinion == 0) room.setCurNoUser(room.getCurNoUser() - 1); //'반대'유저 나가기
        else room.setCurYesUser(room.getCurYesUser() - 1); //'찬성'유저 나가기

        discussionRepository.save(room);
    }
}
