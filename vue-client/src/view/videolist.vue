<template>
  <div>
    <main>
      <header><h1 @click="back()">Workspace</h1>
        <h2>{{this.$route.query.title}}</h2></header>
      <section>
        <ul>
          <template v-if="list.length !== 0">
          <li itemscope itemtype="https://schema.org/SoftwareApplication" v-for="(item, index) in list"><a itemprop="url" @click="router2(item.id)"><span
            class="name" itemprop="name"> {{item.title}}</span><span class="version"
                                                                  itemprop="softwareVersion">Created</span><span
            class="desc"
            itemprop="description">{{item.add_time}}</span></a>
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
      this.getlist(false);
    },
    data() {
      return {
        list: [],
        cur :0
      }
    },
    methods :{

      getlist (is_next) {
        if(is_next){
          this.cur = this.cur + 1;
        }else{
          this.cur = this.cur - 1;
          if(this.cur < 0){
            this.cur =0;
          }
        }
        this.axios.get('/api/video/list?id=' + this.$route.query.id + '&cur=' + this.cur)
          .then((res) => {
            if (res.data) {
              this.list = res.data
            } else {
              console.log(res)
            }
          })
          .catch(err => {
            console.log(err)
          })
      },
      router2(subId){
        this.$router.push({ path: '/videodtl', query: {  id:this.$route.query.id,subId:subId,title:this.$route.query.title }})
      },
      back(){
        this.$router.go(-1);//返回上一层
      }



    }
  }
</script>

