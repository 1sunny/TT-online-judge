<template>
<div>
  <button @click="$router.push('/test/1')">1</button>
  <button @click="$router.push('/test/2')">2</button>
  <button @click="$router.push('/test/3')">3</button>
</div>
</template>

<script>
export default {
  name: 'CcIntervalExample',
  data() {
    return {
      a: 0
    }
  },
  created() {
    console.log('created');
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
