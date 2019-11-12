package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QusetionExtMapper;
import life.majiang.community.mapper.QusetionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Qusetion;
import life.majiang.community.model.QusetionExample;
import life.majiang.community.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    QusetionExtMapper qusetionExtMapper;
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO =new PaginationDTO();
        Integer totalCount = (int)qusetionMapper.countByExample(new QusetionExample());
        paginationDTO.setPagination(totalCount,page,size);

        if (page<1){
            page=1;
        }

        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset =size*(page-1);
        List<Qusetion> questionlist = qusetionMapper.selectByExampleWithRowbounds(new QusetionExample(),new RowBounds(offset,size));
        List<QuestionDTO> questionDTOSList =new ArrayList<>();
        for (Qusetion qusetion : questionlist) {
            User user = userMapper.selectByPrimaryKey(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(qusetion,questionDTO);
            questionDTO.setUser(user);
            questionDTOSList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOSList);


        return paginationDTO;
    }

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO =new PaginationDTO();
        QusetionExample example = new QusetionExample();
        example.createCriteria()
                .andCreatorEqualTo(userId);
        Integer totalCount = (int)qusetionMapper.countByExample(example);
        paginationDTO.setPagination(totalCount,page,size);

        if (page<1){
            page=1;
        }

        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset =size*(page-1);

        QusetionExample qusetionExample =new QusetionExample();
        qusetionExample.createCriteria()
                .andCreatorEqualTo(userId);
        List<Qusetion> questionlist = qusetionMapper.selectByExampleWithRowbounds(qusetionExample,new RowBounds(offset,size));

        List<QuestionDTO> questionDTOSList =new ArrayList<>();

        for (Qusetion qusetion : questionlist) {
            User user = userMapper.selectByPrimaryKey(qusetion.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(qusetion,questionDTO);
            questionDTO.setUser(user);
            questionDTOSList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOSList);


        return paginationDTO;
    }

    public QuestionDTO getById(Integer id) {
        Qusetion qusetion = qusetionMapper.selectByPrimaryKey(id);
        if (qusetion==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(qusetion,questionDTO);
        User user = userMapper.selectByPrimaryKey(qusetion.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Qusetion qusetion) {
        if (qusetion.getId()==null){
            //创建
            qusetion.setGmtCreate(System.currentTimeMillis());
            qusetion.setGmtModified(qusetion.getGmtCreate());
            qusetionMapper.insertSelective(qusetion);
        }else {
            //更新
            qusetion.setGmtModified(qusetion.getGmtCreate());
            Qusetion updatequsetion = new Qusetion();
            updatequsetion.setGmtModified(System.currentTimeMillis());
            updatequsetion.setDescription(qusetion.getDescription());
            updatequsetion.setTag(qusetion.getTag());
            QusetionExample qusetionExample = new QusetionExample();
            qusetionExample.createCriteria()
                    .andIdEqualTo(qusetion.getId());
            int i = qusetionMapper.updateByExampleSelective(updatequsetion,new QusetionExample());
            if (i !=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Qusetion qusetion = new Qusetion();
        qusetion.setId(id);
        qusetion.setViewCount(1);
        qusetionExtMapper.incView(qusetion);
    }
}
