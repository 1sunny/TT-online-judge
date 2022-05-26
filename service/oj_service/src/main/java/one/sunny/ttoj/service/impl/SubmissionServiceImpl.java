package one.sunny.ttoj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import one.sunny.ttoj.entity.Submission;
import one.sunny.ttoj.mapper.SubmissionMapper;
import one.sunny.ttoj.service.SubmissionService;
import org.springframework.stereotype.Service;

@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionMapper, Submission> implements SubmissionService {

}
