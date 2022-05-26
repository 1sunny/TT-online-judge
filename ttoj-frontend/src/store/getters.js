export default {
    user (state){
        return state.user
    },
    userId(state){
        console.log(state.user);
        return !!state.user ? state.user.id : null
    },
    username(state){
        return !!state.user ? state.user.username : null
    },
    auth(state){
        console.log(1);
        return !!state.user ? !!state.user.id : false
    },
    showManageBtn(state){
        console.log(state.permissions);
        return !!state.permissions && state.permissions.length > 0
    },
    showProblemManage(state){
        return !!state.permissions && (state.permissions.indexOf('manage:all:problem') >= 0 || state.permissions.indexOf('manage:own:problem')>= 0)
    },
    showContestManage(state){
        return !!state.permissions && (state.permissions.indexOf('manage:all:contest') >= 0 || state.permissions.indexOf('manage:own:contest')>= 0)
    },
    showProblemTagManage(state){
        return !!state.permissions && state.permissions.indexOf('manage:all:problemTag') >= 0
    },
    showUserManage(state){
        return !!state.permissions && state.permissions.indexOf('manage:all:user') >= 0
    }
}
