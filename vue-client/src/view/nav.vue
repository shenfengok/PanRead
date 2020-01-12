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
        {path:'/ray',title:'极客专栏',prefix:'zhuanlan'},
        {path:'/ray',title:'极客视频',prefix:'video'},
        {path:'/ray',title:'得到app',prefix:'dedao'}
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
