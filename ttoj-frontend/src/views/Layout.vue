<template>
  <div style="min-height: 100vh;
              height: 100%;
              width: 100%;
              background-color: #fff;
              ">
    <Header id="header"/>
    <router-view style="padding-top: 46px"/>
    <Footer v-if="$route.matched[0] && $route.matched[0].meta.needFooter"/>
  </div>
</template>

<script>
import Header from "@/components/Header";
import Footer from "@/components/footer/Footer";

export default {
  name: 'Layout',
  components: {
    Header,
    Footer
  },
  onShow() {
    console.log(this.$route);
  },
}
</script>

<style>

</style>
