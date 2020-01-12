<template>
  <div>
    <main>
      <header><h1 @click="back()">{{this.$route.query.prefix}}</h1>
        <h2>{{this.curr.title}}</h2></header>
      <section>
        <ul>
          <template  v-if="this.showPlay">
            <li itemscope itemtype="https://schema.org/SoftwareApplication" v-html="this.playAudio">

            </li>
          </template>

          <template >

            <iframe id="contentFrame" security="restricted" sandbox="allow-top-navigation allow-same-origin allow-forms allow-scripts" :src="this.frame_path" style="width: 100%;height: 700px;border:0;" ></iframe>
          </template>

          <li  style="padding-top: 18px;">
            <a itemprop="url"  style="display: inline"  @click="gotonext()"><span
              class="desc"
              itemprop="description">{{ this.next.title ?'下一篇:' + this.next.title :''}}</span></a>
            <a itemprop="url" style="display: inline" @click="gotoprev()"><span
              class="desc"
              itemprop="description">{{this.prev.title ?'上一篇:' + this.prev.title :''}}</span></a>

          </li>
        </ul>
      </section>
    </main>
    <div id="fail"><span class="noJsMsg">Works best with JavaScript enabled!</span><a class="noBrowserMsg"
                                                                                      href="http://browsehappy.com">Works
      best in modern browsers!</a></div>
    <div class="paypal">
      <form class="paypal-form" action="//www.paypal.com/cgi-bin/webscr" method="post" target="_blank"><input
        type="hidden"
        name="cmd"
        value="_s-xclick"><input
        type="hidden" name="lc" value="US"><input type="hidden" name="hosted_button_id" value="8WSPKWT7YBTSQ"></form>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'raydtl',
    created () {
      this.getdtl(this.$route.query.subId);
    },
    data() {
      return {
        curr:{},prev:{},next:{},
        cur :0,
        frame_path:'',
        showPlay :false,
        playUrl :'',
        playAudio :''
      }
    },
    methods :{

      getdtl (cur) {
        let that = this;
        this.axios.get('/api/'+this.$route.query.prefix+'/dtl?id=' + this.$route.query.id + '&cur=' + cur)
          .then((res) => {
            if (res.data) {

              this.curr = res.data.cur ||{};
              this.prev = res.data.prev ||{};
              this.next = res.data.next ||{};
              let finfo = JSON.parse(res.data.cur.file_info);
              if(finfo.mp4){
                this.renderVideo(finfo);
              }else if(finfo.html &&this.$route.query.prefix ==='zhuanlan' ){
                this.renderhtml(this.findContentUrl(finfo),this.findAudioPath(finfo));
              }else{//其他
                if(finfo.html){
                  this.renderRaw(finfo.html);
                } else if(finfo.pdf ){
                  this.renderRaw(finfo.pdf );
                }else if(finfo.png){
                  this.renderRaw( finfo.png);
                }
                let ad = this.findAudioPath(finfo);
                if(!finfo.mp4 &&　ad){
                  this.showPlay = true;
                  this.playUrl = this.playUUrl(this.findAudioPath(finfo));
                  this.playAudio = "<audio  controls autoplay loop id='audios' style='width:480px;'><source src='"+this.playUrl +"' /></audio>"
                }
              }



              this.loghis(cur);
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      renderVideo(finfo){
        this.frame_path ='https://pan.baidu.com/mbox/streampage?from_uk=228435709&msg_id=471981340980807638&' +
          'fs_id='+finfo.mp4.fsid+'&to=658103785633267975&type=2&name='+finfo.mp4.file_name+'&path='+finfo.mp4.path;

      },
      renderRaw(ent){
        this.frame_path = this.playUUrl(ent.path);
      },

      findContentUrl(file_info){
       if(file_info.html){
         return file_info.html.path;
       }
       if(file_info.pdf){
         return file_info.pdf.path;
       }
        if(file_info.png){
          return file_info.png.path;
        }
      },
      findAudioPath(file_info){
        if(file_info.m4a){
          return file_info.m4a.path;
        }
        return file_info.mp3.path;

      },
      renderhtml(url,audio){

        this.axios.get( this.panUrl(url))
          .then((res) => {
            if (res.data) {
              let doc = document.getElementById('contentFrame').contentWindow.document;
              if(this.$route.query.prefix ==='zhuanlan'){
                doc.documentElement.innerHTML = this.processData(res.data,audio);
              }
              document.getElementById('contentFrame').contentWindow.scrollTo(0,0);
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      gotoprev(){
        this.getdtl(this.prev.id);
      },
      gotonext(){
        this.getdtl(this.next.id);
      },
      processData(data,audio){
        let myaudio = "<audio  controls autoplay loop id='audios' style='width:480px;'><source src='"+this.playUUrl(audio)+"' /></audio><br>" ;
        let str = data.replace(/_28dOln0j_0/g,'_28dOln0j_01x').replace(/-webkit-line-clamp:5;/g,'').replace(/<div class=\"_2r3UB1GX_0\"><span>展开<\/span><i class=\"iconfont\"><\/i><\/div>/g,'')


        return this.findUrl(str).replace('<div class="_7Xrmrbox_0">防止断更 请务必加首发微信：1716143665</div>',myaudio)
          .replace('<div class="_7Xrmrbox_0">下载APP</div>',myaudio).replace('_28dOln0j_01x','_28dOln0j_01xx')
      },
      panUrl(url){
        return '/pan/'+this.$route.query.prefix+'-all'+url;
      },
      playUUrl(audio){
        return 'http://codingbaby.f3322.net:3333/pan/'+this.$route.query.prefix+'-all'+audio;
      },
      loghis(cur){
        let url = '/api/loghis?id=' + this.$route.query.id + '&cur='
          + cur +'&pname='+this.$route.query.pname +'&cname='
          +this.curr.title +'&prefix='+this.$route.query.prefix;
        console.log(url)
        this.axios.get(url)
          .then((res) => {
            console.log(res)
          })
          .catch(err => {
            console.log(err)
          })
      },
      findUrl(str){
        let reg = /<div class=\"_3Jbcj4Iu_0 _2QmGFWqF_0\"><img data-savepage-src=\"(.*)" src=\"(.*)\" class="_1-ZfmNK8_0">/;
        let url = reg.exec(str)[1].trim();
        return str.replace(reg,'<div class="_3Jbcj4Iu_0 _2QmGFWqF_0"><img  src="'+url+'" class="_1-ZfmNK8_0">');
      },
      back(){
        this.$router.go(-1);//返回上一层
      }


    }
  }
</script>

