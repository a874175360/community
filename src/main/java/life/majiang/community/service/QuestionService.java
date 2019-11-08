package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QusetionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Qusetion;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    QusetionMapper qusetionMapper;
    
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO =new PaginationDTO();
        Integer totalCount = qusetionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if (page<1){
            page=1;
        }

        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset =size*(page-1);
        List<Qusetion> questionlist = qusetionMapper.list(offset,size);
        List<QuestionDTO> questionDTOSList =new ArrayList<>();
        for (Qusetion qusetion : questionlist) {
            User user = userMapper.findById(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(qusetion,questionDTO);
            questionDTO.setUser(user);
            questionDTOSList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOSList);


        return paginationDTO;
    }

}
