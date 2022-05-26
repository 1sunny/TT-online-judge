package one.sunny.ttoj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import one.sunny.ttoj.entity.Contest;
import one.sunny.ttoj.pojo.bo.ContestWithAuthorsBo;
import one.sunny.ttoj.pojo.params.oj.ContestQueryParams;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContestMapper extends BaseMapper<Contest> {

    ContestWithAuthorsBo getContestWithAuthorsBoByContestId(Long contestId, Boolean admin);

    List<ContestWithAuthorsBo> getContestWithAuthorsBoByCondition(@Param("params") ContestQueryParams contestQueryParams,@Param("userId") Long userId);
}
