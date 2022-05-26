package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.ttoj.entity.ContestAuthor;
import one.sunny.ttoj.mapper.ContestAuthorMapper;
import one.sunny.ttoj.service.ContestAuthorService;
import org.springframework.stereotype.Service;

@Service
public class ContestAuthorServiceImpl extends ServiceImpl<ContestAuthorMapper, ContestAuthor> implements ContestAuthorService {

}
