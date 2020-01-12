<template>
  <div>
    <main>
      <header><h1 @click="back()">Workspace</h1>
        <h2>{{this.curr.title}}--{{this.$route.query.pname}}</h2></header>
      <section>
        <ul>
          <template v-if="this.curr.title !== ''">

            <iframe :src="frame_path" style="width: 100%;height: 700px;border:0;overflow-x:visible" ></iframe>
          </template>
          <template v-else>
            <li itemscope itemtype="https://schema.org/SoftwareApplication">no items
            </li>
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
    name: 'list',
    created () {
      this.getdtl(this.$route.query.subId);
      // this.getcnt("/zhuanlan-all/01-%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E7%AE%97%E6%B3%95%E4%B9%8B%E7%BE%8E/01-%E5%BC%80%E7%AF%87%E8%AF%8D%20(1%E8%AE%B2)/00%E4%B8%A8%E5%BC%80%E7%AF%87%E8%AF%8D%E4%B8%A8%E4%BB%8E%E4%BB%8A%E5%A4%A9%E8%B5%B7%EF%BC%8C%E8%B7%A8%E8%BF%87%E2%80%9C%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E7%AE%97%E6%B3%95%E2%80%9D%E8%BF%99%E9%81%93%E5%9D%8E.html")
    },
    data() {
      return {
        curr:{},prev:{},next:{},
        cur :0,
        frame_path:''
      }
    },
    methods :{

      getdtl (cur) {
        let that = this;
        this.axios.get('/api/video/dtl?id=' + this.$route.query.id + '&cur=' + cur)
          .then((res) => {
            if (res.data) {

              this.curr = res.data.cur ||{};
              this.prev = res.data.prev ||{};
              this.next = res.data.next ||{};
              this.frame_path ='https://pan.baidu.com/mbox/streampage?from_uk=228435709&msg_id=471981340980807638&' +
                'fs_id='+res.data.cur.fsid+'&to=658103785633267975&type=2&name='+res.data.cur.file_name+'&path='+res.data.cur.path;
              this.loghis(cur);
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
      loghis(cur){
        let url = '/api/loghis?id=' + this.$route.query.id + '&cur='
          + cur +'&type=1pname='+this.$route.query.pname +'&cname='
          +this.curr.title  +'&prefix=video';
        console.log(url)
        this.axios.get(url)
          .then((res) => {
            console.log(res)
          })
          .catch(err => {
            console.log(err)
          })
      },
      back(){
        this.$router.go(-1);//返回上一层
      }


    }
  }
</script>

