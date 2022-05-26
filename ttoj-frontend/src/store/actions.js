import types from '../store/types'
import storage from "../utils/storage";
import {getCurrentUser} from "../api/oj/userApi";

export default {
    [types.Get_LoginUser](context){
        return getCurrentUser()
    },

}
