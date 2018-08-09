<template>
    <ul class="layui-nav layui-nav-tree" >
      <li class="layui-nav-item" v-for="item in menulist">
        <a v-if="item.type == 0" href="javascript:void(0)">
          <i v-if="item.icon != null" :class="item.icon"></i>
          <span>{{item.name}}</span>
          <em class="layui-nav-more"></em>
        </a>
        <dl v-if="item.type == 0" class="layui-nav-child">
          <dd v-for="item in item.list" >
            <router-link :to="{path:item.url.match(/(\/\w+)\.html/)[1]}"  v-if="item.type === 1" ><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></router-link>
          </dd>
        </dl>
        <router-link :to="{path:item.url.match(/(\/\w+)\.html/)[1]}"   v-if="item.type === 1" ><i v-if="item.icon != null" :class="item.icon" :data-icon="item.icon"></i> <span>{{item.name}}</span></router-link>
      </li>
    </ul>
</template>
<script>
  export default{
      data(){
        return{
          isquery:true,
        }
      },
      props:{
        menulist:{
        }
      },
      updated(){
      },
      methods:{

      }
  }
</script>
