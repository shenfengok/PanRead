<template>
  <div>
    <main>
      <header><h1>History</h1>
        <h2>latest updates ...</h2></header>
      <section>
        <ul>
          <template v-if="zlist.length !== 0">
          <li itemscope itemtype="https://schema.org/SoftwareApplication" v-for="(item, index) in zlist"><a itemprop="url"><span
            class="name" itemprop="name"  @click="routerTo(index)"> {{item.pname}}</span><span @click="routerToS(index)" class="version"
                                                                  itemprop="softwareVersion">{{item.cname}}</span><span
            class="desc"
            itemprop="description"  @click="routerToS(index)">{{item.up_time}}</span></a>
          </li>
          </template>
          <template v-else>
            <li itemscope itemtype="https://schema.org/SoftwareApplication">no items
            </li>
          </template>
          <li  >

            <a itemprop="url" style="display: inline" @click="getZlist(false)"><span
              class="desc"
              itemprop="description">上一页</span></a>
            <a itemprop="url"  style="display: inline"  @click="getZlist(true)"><span
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
  name: 'home',
  created () {
    this.getZlist(false);
  },
  data() {
    return {
      zlist: [],
      cur :0
    }
  },
  methods :{

    getZlist (is_next) {
      if(is_next){
        this.cur = this.cur + 1;
      }else{
        this.cur = this.cur - 1;
        if(this.cur < 0){
          this.cur =0;
        }
      }
      this.axios.get('/api/history.php?start=' + this.cur)
        .then((res) => {
          if (res.data) {
            this.zlist = res.data
          } else {
            console.log(res)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },
    routerTo(index){

      this.$router.push({ path: '/raylist', query: { id: this.zlist[index].pid,title:this.zlist[index].pname ,prefix:this.zlist[index].prefix }})
    },
    routerToS(index){
      this.$router.push({ path: '/raydtl', query: {  id:this.zlist[index].pid,subId:this.zlist[index].cid,pname:this.zlist[index].pname,title:this.zlist[index].cname,prefix:this.zlist[index].prefix }})
    }

  }
}
</script>

