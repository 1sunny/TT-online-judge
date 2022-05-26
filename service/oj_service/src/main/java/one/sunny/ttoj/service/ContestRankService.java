package one.sunny.ttoj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import one.sunny.ttoj.entity.ContestRank;
import one.sunny.ttoj.pojo.params.oj.ContestRankingsParams;

import java.util.Map;

public interface ContestRankService extends IService<ContestRank> {

    Map<String, Object> getRankingsByCondition(ContestRankingsParams contestRankingsParams);
}
