package one.sunny.ttoj.pojo.params.oj;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeParams {
    private String src;
    private Integer max_cpu_time;
    private Integer max_memory;
    private String test_case_id;
    private LanguageConfig language_config;
    private Boolean output;

    public JudgeParams(String language,
                       String src,
                       Integer max_cpu_time,
                       Integer max_memory,
                       String test_case_id,
                       Boolean output){
        this.setSrc(src);
        this.setMax_cpu_time(max_cpu_time);
        this.setMax_memory(max_memory);
        this.setTest_case_id(test_case_id);
        this.setOutput(output);
        this.setLanguage_config(new LanguageConfig(language));
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class LanguageConfig {
        private String template;
        private Compile compile;
        private Run run;

        public LanguageConfig(String type){
            this.setCompile(new Compile("cpp"));
            this.setRun(new Run("cpp"));
            this.setTemplate("");
        }
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class Compile {
            public String src_name;
            public String exe_name;
            public Integer max_cpu_time;
            public Integer max_real_time;
            public Integer max_memory;
            public String compile_command;

            public Compile(String type) {
                if (type == "cpp") {
                    this.setSrc_name("main.cpp");
                    this.setExe_name("main");
                    this.setMax_cpu_time(10000);
                    this.setMax_real_time(20000);
                    this.setMax_memory(1073741824);
                    this.setCompile_command("/usr/bin/g++ -DONLINE_JUDGE -O2 -w -fmax-errors=3 -std=c++14 {src_path} -lm -o {exe_path}");
                }
            }
        }
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        class Run {
            private String command;
            private Map seccomp_rule;
            private List<String> env;

            public Run(String type){
                if(type == "cpp"){
                    this.setCommand("{exe_path}");
                    HashMap<String, String> map = new HashMap<>();
                    map.put("Standard IO", "c_cpp");
                    map.put("File IO", "c_cpp_file_io");
                    this.setSeccomp_rule(map);
                    List<String> env = new ArrayList<>();
                    env.add("LANG=en_US.UTF-8");
                    env.add("LANGUAGE=en_US:en");
                    env.add("LC_ALL=en_US.UTF-8");
                    this.setEnv(env);
                }
            }
        }
    }
}
