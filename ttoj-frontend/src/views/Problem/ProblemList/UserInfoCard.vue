<template>
  <div>
    <el-card class="box-card" v-if="auth">
      <div slot="header" class="clearfix">
        <div class="text-label-2 font-medium">
          <Icon type="md-contact"/>
          {{ username }}
        </div>
      </div>
      <div style="display: flex;align-items: center;justify-content: space-between">
        <div>
          <div class="contentTitle">
            <Icon type="md-pulse"/>
            <span class="info-text">Rating: {{ user.rating }}</span>
          </div>
          <div class="contentTitle">
            <Icon type="md-thumbs-up"/>
            <span class="info-text">贡献值: {{ user.contribution }}</span>
          </div>
          <div class="contentTitle">
            <Icon type="md-checkmark" color="#2DB55D"/>
            <span class="info-text">过题数: {{ user.acNum }}</span>
          </div>
          <div class="contentTitle">
            <Icon type="ios-paw"/>
            <el-link type="primary" disabled class="info-text">提交记录</el-link>
          </div>
          <div class="contentTitle">
            <Icon type="md-list-box"/>
            <el-link type="primary" disabled class="info-text">比赛记录</el-link>
          </div>
          <div class="contentTitle">
            <Icon type="md-settings"/>
            <el-link type="primary" disabled class="info-text">设置</el-link>
          </div>
        </div>
        <div style="margin-right: 15px">
          <div style="display: flex;flex-direction: column;align-items: center">
            <el-image
                style="width: 70px; height: 70px;border-radius: 50%"
                :src="$config.baseUrl+user.avatar"
                :fit="'contain'">
            </el-image>
            <div style="margin-top: 6px">{{ user.username }}</div>
          </div>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "UserInfoCard",
  data() {
    return {}
  },
  computed: {
    ...mapGetters([
      'userId',
      'username',
      'auth',
      'user',
    ]),
  },
}
</script>

<style scoped>
.info-text{
  margin-left: 5px;
}
</style>
