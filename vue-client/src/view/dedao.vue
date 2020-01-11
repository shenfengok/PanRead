<template>
  <div>
    <main>
      <header><h1>Dedao</h1>
        <h2>latest updates ...</h2></header>
      <section>
        <ul>
          <template v-if="dlist.length !== 0">
          <li itemscope itemtype="https://schema.org/SoftwareApplication" v-for="(item, index) in dlist"><a itemprop="url" @click="routerTo(item.id,item.title)"><span
            class="name" itemprop="name"> {{item.title}}</span><span class="version"
                                                                  itemprop="softwareVersion">{{item.finish ?'complete':'updating'}}</span><span
            class="desc"
            itemprop="description">{{item.ititle}}</span></a>
          </li>
          </template>
          <template v-else>
            <li itemscope itemtype="https://schema.org/SoftwareApplication">no items
            </li>
          </template>
          <li  >

            <a itemprop="url" style="display: inline" @click="getDlist(false)"><span
              class="desc"
              itemprop="description">上一页</span></a>
            <a itemprop="url"  style="display: inline"  @click="getDlist(true)"><span
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
  name: 'video',
  created () {
    this.getDlist(false);
  },
  data() {
    return {
      dlist: [],
      cur :0
    }
  },
  methods :{

    getDlist (is_next) {
      if(is_next){
        this.cur = this.cur + 1;
      }else{
        this.cur = this.cur - 1;
        if(this.cur < 0){
          this.cur =0;
        }
      }
      this.axios.get('/api/'+this.$route.query.prefix+'list?start=' + this.cur)
        .then((res) => {
          if (res.data) {
            this.dlist = res.data
          } else {
            console.log(res)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    routerTo(id,title){
      this.$router.push({ path: '/'+this.$route.query.prefix+'list', query: { id: id,title:title ,prefix:this.$route.query.prefix }})
    }


  }
}
</script>

