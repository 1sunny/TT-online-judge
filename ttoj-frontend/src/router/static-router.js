import Layout from "../views/Layout";

export default [
    {
        path: '/',
        redirect: 'problems',
        component: Layout,
        children: [
            {
                path: 'problems',
                component: () => import('../views/Problem/ProblemList/ProblemList'),
            },
        ]
    },
    {
        path: '/problems/:problemId',
        redirect: '',
        component: Layout,
        meta: {
          fullScreen: true
        },
        children: [
            {
                path: '',
                redirect: '',
                component: () => import('../views/Problem/ProblemDetail/ProblemDetail'),
                children: [
                    {
                        path: '',
                        name: 'ProblemDescription',
                        meta: {
                            fullScreen: true
                        },
                        component: () => import('../views/Problem/ProblemDetail/ProblemDescription')
                    },
                    {
                        path: 'comments',
                        name: 'ProblemComments',
                        meta: {
                            fullScreen: true
                        },
                        component: () => import('../views/Problem/ProblemDetail/ProblemComments')
                    },
                    {
                        path: 'solutions',
                        name: 'ProblemSolutions',
                        meta: {
                            fullScreen: true
                        },
                        component: () => import('../views/Problem/ProblemDetail/ProblemSolutions')
                    },
                    {
                        path: 'submitRecord',
                        name: 'ProblemSubmitRecord',
                        meta: {
                            requiredAuth: true,
                            fullScreen: true
                        },
                        component: () => import('../views/Problem/ProblemDetail/ProblemSubmitRecord')
                    }
                ]
            },
        ]
    },
    {
        path: '/contest',
        redirect: '',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import('../views/Contest/ContestList/ContestList'),
            },
        ]
    },
    {
        path: '/contest/:contestId',
        redirect: '',
        component: Layout,
        meta: {
            fullScreen: true
        },
        children: [
            {
                path: '',
                redirect: 'contestOverview',
                component: () => import('../views/Contest/ContestDetail/ContestDetail'),
                children: [
                    {
                        path: 'contestOverview',
                        name: 'contestOverview',
                        meta: {
                            title: '比赛说明',
                            fullScreen: true
                        },
                        component: () => import('../views/Contest/ContestDetail/ContestOverview/ContestOverview'),
                    },
                    {
                        path: 'contestProblems',
                        name: 'contestProblems',
                        meta: {
                            title: '题目',
                            fullScreen: true
                        },
                        component: () => import('../views/Contest/ContestDetail/ContestProblems/ContestProblems'),
                    },
                    {
                        path: 'contestRankings',
                        name: 'contestRankings',
                        meta: {
                            title: '排名',
                            fullScreen: true
                        },
                        component: () => import('../views/Contest/ContestDetail/ContestRankings/ContestRankings'),
                    },
                    {
                        path: 'contestSubmissions',
                        name: 'contestSubmissions',
                        meta: {
                            title: '所有提交',
                            fullScreen: true
                        },
                        component: () => import('../views/Contest/ContestDetail/ContestSubmissions/ContestSubmissions'),
                    },
                    {
                        path: 'contestMy',
                        name: 'contestMy',
                        meta: {
                            fullScreen: true
                        },
                        component: () => import('../views/Contest/ContestDetail/ContestMySubmissions/ContestMySubmissions'),
                    },
                    {
                        path: 'contestProblems/:problemDisplayId',
                        name: 'contestProblem',
                        meta: {
                            fullScreen: true,
                        },
                        component: () => import('../views/Problem/ProblemDetail/ProblemDetail'),
                        redirect: '',
                        children: [
                            {
                                path: '',
                                name: 'contestProblem',
                                meta: {
                                    fullScreen: true
                                },
                                component: () => import('../views/Problem/ProblemDetail/ProblemDescription')
                            },
                            // {
                            //     path: 'explanation',
                            //     name: 'contestProblem',
                            //     component: () => import('../views/Problem/ProblemExplanation')
                            // }
                        ]
                    }
                ]
            }
        ]
    },
    {
        path: '/forum',
        component: Layout,
        redirect: '',
        children: [
            {
                path: '',
                component: () => import('../views/Forum/Forum'),
            }
        ]
    },
    {
        path: '/forum/article/:articleId',
        component: Layout,
        redirect: '',
        children: [
            {
                path: '',
                component: () => import('../views/Forum/Article/Article'),
            },
        ],
    },
    {
        path: '/home',
        redirect: '',
        meta: {
            needFooter: true
        },
        component: Layout,
        children: [
            {
                path: '',
                component: () => import('../views/Home/Home')
            }
        ]
    },
    {
        path: '/user/:username',
        redirect: '',
        component: Layout,
        children: [
            {
                path: '',
                redirect: '',
                component: () => import('../views/User/UserProfile'),
                children: [
                    {
                        path: '',
                        name: 'UserInfo',
                        component: () => import('../views/User/UserProfile/UserInfo'),
                    },
                    {
                        path: 'submissions',
                        name: 'RecentSubmissions',
                        component: () => import('../views/User/UserProfile/UserRecentSubmissions'),
                    },
                    {
                        path: 'contests',
                        name: 'ContestRecords',
                        component: () => import('../views/User/UserProfile/UserContestRecords'),
                    },
                    {
                        path: 'solutions',
                        name: 'Solutions',
                        component: () => import('../views/User/UserProfile/UserSolutions'),
                    },
                    {
                        path: 'notes',
                        name: 'Notes',
                        component: () => import('../views/User/UserProfile/UserNotes'),
                    },
                    {
                        path: 'settings',
                        name: 'Settings',
                        component: () => import('../views/User/UserProfile/UserSettings'),
                    },
                ]
            },
        ]
    },
    {
        path: '/manage',
        redirect: '',
        component: Layout,
        meta:{
            requiredAuth: true,
            fullScreen: true
        },
        children: [
            {
                path: '',
                redirect: '',
                component: () => import('../views/Manage/Manage'),
                name: 'dynamicRoutesParent',
                children: [
                    {
                        path: '',
                        meta:{
                            name: 'ManageDashboard',
                            requiredAuth: true,
                            fullScreen: true
                        },
                        component: () => import('../views/Manage/ManageDashboard'),
                    },
                ]
            },

        ]
    },
    {
        path: '/test',
        redirect: '',
        component: Layout,
        children: [
            {
                path: '',
                component: () => import('../views/Test')
            },
            {
                path: '2',
                component: () => import('../views/Test2')
            },
            {
                path: '3',
                component: () => import('../views/Test3')
            }
        ]
    },
    // {
    //     path: '*',
    //     redirect: '',
    //     component: Layout,
    //     children: [
    //         {
    //             path: '',
    //             component: () => import('../views/Exception/404')
    //         }
    //     ]
    // }
]

