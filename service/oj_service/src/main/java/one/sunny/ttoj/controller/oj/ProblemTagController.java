package one.sunny.ttoj.controller.oj;


import one.sunny.commonutils.R;
import one.sunny.ttoj.service.ProblemTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/problem-tag")
public class ProblemTagController {
    @Autowired
    private ProblemTagService problemTagService;

    @GetMapping("tree")
    public R getProblemTag(){
        return problemTagService.getProblemTagTree();
    }

    @GetMapping("{problemId}/tags")
    public R getProblemTagsByProblemId(@PathVariable("problemId") Long problemId){
        return problemTagService.getProblemTagsByProblemId(problemId);
    }
}

