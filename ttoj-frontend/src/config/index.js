export default {
    /**
     * @description 配置显示在浏览器标签的title
     */
    title: 'TTOJ',
    /**
     * @description token在Cookie中存储的天数，默认1天
     */
    cookieExpires: 1,
    /**
     * @description 是否使用国际化，默认为false
     *              如果不使用，则需要在路由中给需要在菜单中展示的路由设置meta: {title: 'xxx'}
     *              用来在菜单中显示文字
     */
    useI18n: false,
    /**
     * @description api请求基础路径
     */
    baseUrl: "http://39.103.197.80:9991/",
    /**
     * @description 需要加载的插件
     */
    plugin: {

    },
    /*
    支持的语言
     */
    supportLanguages: [
        {value: 'C', title: 'C'},
        {value: 'Cpp', title: 'C++'},
        {value: 'Java', title: 'Java'},
        {value: 'Python2', title: 'Python2'},
        {value: 'Python3', title: 'Python3'}
        ],
    /*
    判题结果
     */
    judgeResult:[
        {value: 'Accepted', title: '答案正确'},
        {value: 'WrongAnswer', title: '答案错误'},
        {value: 'TimeLimitExceeded', title: '运行超时'},
        {value: 'RealTimeLimitExceeded', title: '输出时间超限'},
        {value: 'MemoryLimitExceeded', title: '内存超限'},
        {value: 'RuntimeError', title: '运行错误'},
        {value: 'SystemError', title: '内部错误'},
        {value: 'CompileError', title: '编译错误'}
    ],
    /*
    比赛界面固定标签页
     */
    commonTagName: [
        'contestOverview',
        'contestRankings',
        'contestMy',
        'contestSubmissions',
        'contestProblems'
    ],
    /*
    特殊角色
     */
    roles: ['题目创作者', '比赛举办方', '管理员'],
    /*
    需要控制宽度的路由
     */
    /*
    最近比赛的天数
     */
    recentContestDays: 3,

    /*
    各种提示信息
     */
    msg:{
        timeout: '请求超时'
    },
    /**
     * 难度的颜色
     */
    level:[
        '入门',
        '简单',
        '中等',
        '困难',
        '极难',
    ],
    levelColor:{
        '入门': '#03a89e',
        '简单': '#2db55d',
        '中等': '#e1b800',
        '困难': '#ef4743',
        '极难': '#cc3333',
    },
    /**
     * 支持的比赛规则
     */
    supportRuleTypes: [
        {title: 'ACM', value: 1, rule: 'ACM规则'},
        {title: 'IOI', value: 2, rule: 'IOI'},
        {title: 'OI', value: 3, rule: 'OI'}
    ],
    /**
     * tooltip提示信息
     */
    hint:{
        // 比赛出题人
        contestAuthor: '将某个用户设为比赛出题人后,该用户自动拥有题目创作者身份\n' +
                       '同时可以管理该比赛,在比赛中的提交也不会被普通用户看到,也不进入排名'
    },
    /**
     * 提示信息颜色
     */
    msgColor:{
        error: 'f56c6c',
        success: '#e1f3d8',
        warn: '#e6a23c',
        info: '#909399'
    }
}
