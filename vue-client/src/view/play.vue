
<template>
<div id="mplay">
  <audio :src="currSong" autoplay="" controls="" @ended="nextSong"></audio>
  <ul>
    <li v-for="(item,index) in songs">
      <button @click="play(index)">歌曲：{{ item.song }}</button>
      <p>作者：{{ item.author }}</p>
    </li>
  </ul>


</div>
</template>
<style>
  ul{
    list-style: none;
  }
  li{
    border-bottom: 1px solid gray;
  }
</style>
<script>
  export default {
    name: 'play',
    data(){return {
      //defaultSong:"../static/muscis/星月神话.mp3",
      indexSong:0,
      songs:[
        {song:"星月神话.mp3",author:"董敏",src:"../static/muscis/星月神话.mp3"},
        {song:"九张机.mp3",author:"叶清炫",src:"../static/muscis/九张机.mp3"},
        {song:"红颜旧.mp3",author:"刘涛",src:"../static/muscis/红颜旧.mp3"},
        {song:"赤血长殿.mp3",author:"王凯",src:"../static/muscis/赤血长殿.mp3"},
        {song:"风吹麦浪.mp3",author:"李建",src:"../static/muscis/风吹麦浪.mp3"},
        {song:"恋人心.mp3",author:"魏新雨",src:"../static/muscis/恋人心.mp3"},

      ]
    }},
    methods:{
      play(index){
        this.indexSong=index
      },
      nextSong(){
        if (indexSong==songs.length-1){
          this.indexSong=-1;
        }
        this.indexSong++;
        //this.defaultSong=this.songs[this.indexSong].src;
      },
      addSong(){
        this.songs.push({song:"小酒窝.mp3",author:"林俊杰",src:"../static/muscis/小酒窝.mp3"});
      }
    },
    computed:{
      //生命周期时使用
      currSong(){
        console.log(111);
        return this.songs[this.indexSong].src
      }
    }

  }
</script>
