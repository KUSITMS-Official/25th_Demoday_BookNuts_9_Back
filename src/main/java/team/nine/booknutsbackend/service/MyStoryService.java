package team.nine.booknutsbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import team.nine.booknutsbackend.domain.Board;
import team.nine.booknutsbackend.domain.User;
import team.nine.booknutsbackend.domain.myStory.MyStory;
import team.nine.booknutsbackend.domain.myStory.MyStoryBoard;
import team.nine.booknutsbackend.dto.MyStoryDto;
import team.nine.booknutsbackend.exception.mystory.MyStoryNotFoundException;
import team.nine.booknutsbackend.repository.MyStoryBoardRepository;
import team.nine.booknutsbackend.repository.MyStoryRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MyStoryService {

    private final MyStoryRepository myStoryRepository;
    private final MyStoryBoardRepository myStoryBoardRepository;

    //내 스토리 조회
    @Transactional(readOnly = true)
    public List<MyStoryDto> allMyStory(User user) {
        List<MyStory> stories = myStoryRepository.findAllByUser(user);
        List<MyStoryDto> myStoryDtoList =new ArrayList<>();

        for(MyStory myStory: stories){
            myStoryDtoList.add(MyStoryDto.myStoryResponse(myStory,user));
        }
        return myStoryDtoList;
    }

    //스토리 그룹핑
    @Transactional(readOnly = true)
    public void grouping(long[] boardlist) {
        MyStory myStory;
        MyStoryBoard myStoryBoard;

        for(Long boardId: boardlist){

        }

        return;
    }

    //특정 스토리 조회
    @Transactional(readOnly = true)
    public List<MyStoryBoard> find(Long mystoryId) throws MyStoryNotFoundException {
        return myStoryBoardRepository.findAllByMyStoryId(mystoryId);
    }
}
