package one.sunny.ttoj.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.digest.MD5;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import one.sunny.baseservice.exception.TTOJException;
import one.sunny.commonutils.Constants;
import one.sunny.commonutils.ErrorCode;
import one.sunny.commonutils.MyFileUtil;
import one.sunny.ttoj.entity.Problem;
import one.sunny.ttoj.entity.UserAcProblem;
import one.sunny.ttoj.mapper.ProblemMapper;
import one.sunny.ttoj.pojo.bo.LoginUserBo;
import one.sunny.ttoj.pojo.bo.ProblemWithTagsBo;
import one.sunny.ttoj.pojo.dto.TestCaseDto;
import one.sunny.ttoj.pojo.params.manage.ManageProblemSaveParams;
import one.sunny.ttoj.pojo.params.oj.ProblemQueryParams;
import one.sunny.ttoj.pojo.vo.manage.ManageProblemVo;
import one.sunny.ttoj.pojo.vo.oj.ProblemVo;
import one.sunny.ttoj.service.ProblemService;
import one.sunny.ttoj.service.UserAcProblemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j(topic = "TTOJ-ProblemService")
@Service
public class ProblemServiceImpl extends ServiceImpl<ProblemMapper, Problem> implements ProblemService {
    @Autowired
    private ProblemMapper problemMapper;
    @Value("${testCaseSaveLocation}")
    private String testCaseSaveLocation;

    @Autowired
    private UserAcProblemService userAcProblemService;

    public Problem copy(ManageProblemSaveParams manageProblemSaveParams) {
        Problem problem = new Problem();
        BeanUtils.copyProperties(manageProblemSaveParams, problem);
        return problem;
    }

    public <T> ProblemVo copy(T t) {
        ProblemVo problemVo = new ProblemVo();
        BeanUtils.copyProperties(t, problemVo);
        return problemVo;
    }

    public <T> List<ProblemVo> copyList(List<T> list) {
        List<ProblemVo> problemVos = new ArrayList<>();
        for (T t : list) {
            problemVos.add(copy(t));
        }
        return problemVos;
    }

    public <T> ManageProblemVo adminCopy(T t) {
        ManageProblemVo manageProblemVo = new ManageProblemVo();
        BeanUtils.copyProperties(t, manageProblemVo);
        return manageProblemVo;
    }

    public <T> List<ManageProblemVo> adminCopyList(List<T> list) {
        List<ManageProblemVo> manageProblemVos = new ArrayList<>();
        for (T t : list) {
            manageProblemVos.add(adminCopy(t));
        }
        return manageProblemVos;
    }

    @Override
    public Map<String, Object> getProblemsByCondition(ProblemQueryParams problemQueryParams, Boolean admin) {
        //1、取出查询条件
        Integer currentPage = problemQueryParams.getCurrentPage();
        Integer pageSize = problemQueryParams.getPageSize();
        String authorName = problemQueryParams.getAuthorName();
        String name = problemQueryParams.getName();
        Boolean visible = problemQueryParams.getVisible();
        String level = problemQueryParams.getLevel();
        //2、判断条件是否为空，如不为空拼写sql
        LambdaQueryWrapper<Problem> problemWrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(authorName)) {
            problemWrapper.like(Problem::getAuthorName, authorName);
        }
        if (!StringUtils.isEmpty(name)) {
            problemWrapper.like(Problem::getName, name);
        }
        if (visible != null) {
            problemWrapper.eq(Problem::getVisible, visible);
        }
        if (level != null) {
            problemWrapper.eq(Problem::getLevel, level);
        }
        // 不是管理员, 只能查看 visible = true 的题目
        if (!admin){
            problemWrapper.eq(Problem::getVisible, true);
        }
        //details里面可能存放了当前登录用户的详细信息，也可以通过cast后拿到
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = null;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            LoginUserBo loginUserBo = (LoginUserBo) authentication.getPrincipal();
            List<String> permissions = loginUserBo.getPermissions();
            userId = loginUserBo.getUser().getId();
            // 如果只能管理自己的题目
            if (admin && !permissions.contains(Constants.MANAGE_ALL_PROBLEM)){
                problemWrapper.eq(Problem::getAuthorId, userId);
            }
        }
        Page<Problem> page = new Page<>(currentPage, pageSize);
        problemMapper.selectPage(page, problemWrapper);
        List<Problem> problems = page.getRecords();
        long total = page.getTotal();
        Map<String, Object> problemsByConditionMap = new HashMap<>();
        problemsByConditionMap.put("total", total);
        if (admin) {
            List<ManageProblemVo> manageProblemVos = adminCopyList(problems);
            problemsByConditionMap.put("problems", manageProblemVos);
            return problemsByConditionMap;
        } else {
            List<ProblemVo> problemVos = copyList(problems);
            for (ProblemVo problemVo : problemVos) {
                problemVo.setAlreadyPassed(false);
            }
            //如果没有用户信息, 不查询题目通过情况
            if (userId != null){
                for (ProblemVo problemVo : problemVos) {
                    Long problemId = problemVo.getId();
                    LambdaQueryWrapper<UserAcProblem> userAcProblemWrapper = new LambdaQueryWrapper<>();
                    userAcProblemWrapper.eq(UserAcProblem::getUserId, userId)
                            .eq(UserAcProblem::getProblemId, problemId);
                    if (userAcProblemService.count(userAcProblemWrapper) > 0) {
                        problemVo.setAlreadyPassed(true);
                    }
                }
            }
            problemsByConditionMap.put("problems", problemVos);
            return problemsByConditionMap;
        }
    }

    /**
     * 权限没写...................
     * @param id
     * @param admin
     * @param <K>
     * @return
     */
    @Override
    public <K> K getProblemById(Long id, Boolean admin) {
        ProblemWithTagsBo problemWithTagsBo = problemMapper.selectProblemWithTagsById(id);
        Assert.notNull(problemWithTagsBo, "题目不存在");
        if (admin) {
            ManageProblemVo manageProblemVo = adminCopy(problemWithTagsBo);
            return (K) manageProblemVo;
        } else {
            ProblemVo problemVo = copy(problemWithTagsBo);
            return (K) problemVo;
        }
    }

    @Override
    public Boolean updateProblemVisibility(Long problemId, Boolean visible) {
        LambdaUpdateWrapper<Problem> problemUpdateWrapper = new LambdaUpdateWrapper<>();
        Problem problem = new Problem();
        problem.setId(problemId);
        problem.setVisible(visible);
        problemUpdateWrapper.eq(Problem::getId, problemId).set(Problem::getVisible, visible);
        int update = problemMapper.update(problem, problemUpdateWrapper);
        if (update > 0) {
            return true;
        }
        throw new TTOJException(ErrorCode.UPDATE_ERROR.getCode(), ErrorCode.UPDATE_ERROR.getMsg());
    }

    /**
     * 使用数据库中题目主键 ID 作为测试文件目录名称
     */
    public Boolean saveTestCase(File testcase, String testCaseDir) {
        // 保存文件部分
        testCaseDir = testCaseSaveLocation + testCaseDir;
        log.info("测试文件路径{}, 测试文件大小{}", testCaseDir, testcase.length());
        File outFile = new File(testCaseDir);
        File unzip = ZipUtil.unzip(testcase, outFile);
        // 读目录
        String[] fileList = unzip.list();
        log.info("文件目录{}", fileList.length);
        if (fileList == null || fileList.length % 2 != 0) {
            throw new TTOJException(ErrorCode.FILE_ERROR.getCode(), ErrorCode.FILE_ERROR.getMsg() + "文件数量不为偶数");
        }
        int testCaseNumber = fileList.length / 2;
        // 制作info文件
        String infoFront = "{\"test_case_number\": " + testCaseNumber + ", " +
                "\"spj\": false,\"" +
                "test_cases\": {";
        String infoMid = "";
        String infoEnd = "}}";
        String inputFileSuffix = ".in";
        String outputFileSuffix = ".out";

        for (int i = 0; i < fileList.length / 2; i++) {
            TestCaseDto testCase = new TestCaseDto();
            // 读取对应下标文件
            String inputFilename = (i + 1) + inputFileSuffix;
            File inputFile = new File(testCaseDir + "/" + inputFilename);
            if (inputFile == null) {
                throw new TTOJException(ErrorCode.FILE_ERROR.getCode(), ErrorCode.FILE_ERROR.getMsg() + inputFilename + "缺失");
            }
            testCase.setInput_name(inputFilename);
            testCase.setInput_size((int) inputFile.length());
            String outputFilename = (i + 1) + outputFileSuffix;
            File outputFile = new File(testCaseDir + "/" + outputFilename);
            String content = FileUtil.readString(outputFile, StandardCharsets.UTF_8);
            if (outputFile == null) {
                throw new TTOJException(ErrorCode.FILE_ERROR.getCode(), ErrorCode.FILE_ERROR.getMsg()+ outputFilename + "缺失");
            }
            testCase.setOutput_name(outputFilename);
            testCase.setOutput_size((int) outputFile.length());
            testCase.setOutput_md5(MD5.create().digestHex16(content));
            testCase.setStripped_output_md5(MyFileUtil.md5FileByTrimLines(outputFile));

            infoMid += "\"" + (i + 1) + "\":" + JSONObject.toJSONString(testCase) + (i == testCaseNumber - 1 ? "" : ",");
        }
        String info = infoFront + infoMid + infoEnd;
        log.info("info: {}", info);
        // 写入info文件
        File infoFile = FileUtil.appendString(info, testCaseDir + "/info", StandardCharsets.UTF_8);
        log.info("infoFile: {}", infoFile);
        if (infoFile == null) {
            throw new TTOJException(ErrorCode.FILE_ERROR.getCode(), ErrorCode.FILE_ERROR.getMsg() + "info文件制作失败");
        }
        return true;
    }

    @Override
    public Boolean saveProblem(File testcase, String params) {
        // 参数判断
        if (testcase == null) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), "测试用例文件参数错误");
        }
        if (params == null) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), "题目参数错误");
        }
        ManageProblemSaveParams manageProblemSaveParams = JSON.parseObject(params, ManageProblemSaveParams.class);
        // 存入数据库
        Problem problem = copy(manageProblemSaveParams);
        problem.setVote(0);
        problem.setSubmitNum(0);
        problem.setAcNum(0);
        problem.setDeleted(0);
        problem.setSolutionNum(0);
        problem.setCommentNum(0);
        problem.setAuthorId(0L);
        problem.setMemoryLimit(problem.getMemoryLimit());
        String displayId = problem.getDisplayId().trim();
        String problemName = problem.getName().trim();
        LambdaQueryWrapper<Problem> problemWrapper = new LambdaQueryWrapper<>();
        problemWrapper.eq(Problem::getName, problemName).or().eq(Problem::getDisplayId, displayId);
        Long count = problemMapper.selectCount(problemWrapper);
        if (count > 0){
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), ErrorCode.PARAM_ERROR.getMsg() + "题目显示ID已存在或题目名字已存在");
        }
        problem.setDisplayId(displayId);
        int insert = problemMapper.insert(problem);
        if (insert == 0) {
            throw new TTOJException(ErrorCode.INSERT_ERROR.getCode(), "题目保存失败");
        }
        Long problemId = problem.getId();
        String testCaseDir = String.valueOf(problemId);
        problem.setTestCaseDir(testCaseDir);
        problemMapper.updateById(problem);
        saveTestCase(testcase, testCaseDir);
        return true;
    }

    @Override
    public Boolean updateProblem(File testcase, String params) throws IOException {
        // 参数判断
        ManageProblemSaveParams manageProblemSaveParams = JSON.parseObject(params, ManageProblemSaveParams.class);
        // 修改数据库中题目
        Problem problem = copy(manageProblemSaveParams);
        Long problemId = problem.getId();
        if (problemId == null) {
            throw new TTOJException(ErrorCode.PARAM_ERROR.getCode(), "题目id为空");
        }
        String testCaseDir = String.valueOf(problemId);
        int update = problemMapper.updateById(problem);
        if (update == 0) {
            throw new TTOJException(ErrorCode.UPDATE_ERROR.getCode(), "题目更新失败");
        }
        if (testcase != null) {
            boolean deleted = FileUtil.del(testCaseSaveLocation + testCaseDir);
            if (!deleted) {
                throw new TTOJException(ErrorCode.FILE_ERROR.getCode(), "旧的测试用例删除失败");
            }
            return saveTestCase(testcase, testCaseDir);
        }
        return true;
    }
}
