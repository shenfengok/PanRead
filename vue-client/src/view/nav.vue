<template>
  <ul>
    <template v-if="menus.length !== 0">

      <li class="page" v-for="(item, index) in menus"><a @click="routerTo(index)" v-title="item.title">{{item.title}}</a></li>
    </template>

  </ul>

</template>

<script>

export default {
  name: 'navpage',
  created () {
    // this.getZlist(false);
  },
  data() {
    return {
      menus: [
        {path:'/jing',title:'极客专栏',prefix:'zhuanlan'},
        {path:'/jing',title:'极客视频',prefix:'video'},
        {path:'/jing',title:'得到app',prefix:'dedao'},
        {path:'/jing',title:'十点读书',prefix:'shidian'}
      ],
      cur :0
    }
  },
  methods:{
    routerTo(index){
      this.$router.push({ path: this.menus[index].path, query: { menu:this.menus[index].title,prefix:this.menus[index].prefix }})
    }
  }

}
</script>
