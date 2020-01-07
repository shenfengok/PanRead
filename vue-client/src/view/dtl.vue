<template>
  <div>
    <main>
      <header><h1>workspace</h1>
        <h2>{{this.$route.query.title}}</h2></header>
      <section>
        <ul>
          <template v-if="content !== ''">
            <li v-html="content" itemscope itemtype="https://schema.org/SoftwareApplication">
            </li>
          </template>
          <template v-else>
            <li itemscope itemtype="https://schema.org/SoftwareApplication">no items
            </li>
          </template>
          <li  >

            <a itemprop="url" style="display: inline" @click="getlist(false)"><span
              class="desc"
              itemprop="description">上一页</span></a>
            <a itemprop="url"  style="display: inline"  @click="getlist(true)"><span
              class="desc"
              itemprop="description">下一页</span></a>
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
      // this.getdtl();
      this.getcnt("/zhuanlan-all/01-%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E7%AE%97%E6%B3%95%E4%B9%8B%E7%BE%8E/01-%E5%BC%80%E7%AF%87%E8%AF%8D%20(1%E8%AE%B2)/00%E4%B8%A8%E5%BC%80%E7%AF%87%E8%AF%8D%E4%B8%A8%E4%BB%8E%E4%BB%8A%E5%A4%A9%E8%B5%B7%EF%BC%8C%E8%B7%A8%E8%BF%87%E2%80%9C%E6%95%B0%E6%8D%AE%E7%BB%93%E6%9E%84%E4%B8%8E%E7%AE%97%E6%B3%95%E2%80%9D%E8%BF%99%E9%81%93%E5%9D%8E.html")
    },
    data() {
      return {
        content: 'waiting ...',
        cur :0
      }
    },
    methods :{

      getdtl (is_next) {
        let that = this;
        this.axios.get('/api/dtl?id=' + this.$route.query.id + '&cur=' + this.$route.query.subId)
          .then((res) => {
            if (res.data) {
              let data = res.data;
              this.content = res.data;
              that.getcnt('');
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      getcnt(url){
        this.axios.get(url)
          .then((res) => {
            if (res.data) {
              let data = res.data;


              // this.content = data.split('<div class="_50pDbNcP_0">')[1];
              this.content = res.data;
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      router3(subId){
        this.$router.push({ path: '/dtl', query: {  id:this.$route.query.id,subId:subId }})
      }


    }
  }
</script>

