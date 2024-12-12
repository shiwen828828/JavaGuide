# 一 VUE 基础

## 1 历史介绍

* angular 09年，年份较早，⼀开始⼤家是拒绝 
* react 2013年, ⽤户体验好，直接拉到⼀堆粉丝
* vue 2014年, ⽤户体验好 作者:尤⾬溪 江苏⽆锡⼈ 国⼈骄 傲

## 2 前端框架与库的区别?

* jquery 库 => DOM(操作DOM) + 请求
* 有可能学习了⼀些art-template 库 -> 模板引擎
* 框架
  * 全⽅位功能⻬全
  * 简易的DOM体验 + 发请求 + 模板引擎 + 路由功能
* KFC的世界⾥,库就是⼀个⼩套餐, 框架就是全家桶
* 代码上的不同
  * ⼀般使⽤库的代码，是调⽤某个函数，我们⾃⼰把控 库的代码
  * ⼀般使⽤框架，其框架在帮我们运⾏我们编写好的代码
  * 框架: 初始化⾃身的⼀些⾏为
    * 执⾏你所编写的代码
    * 施放⼀些资源

## 3 vue起步

* 1:引包
  
  ```
      <!-- 1.引包 -->
      <script src="./vue.js"></script>
  ```

* 2:启动
  
  ```
  new Vue({
   el:'#app',//⽬的地
   data:{
   //保存数据的地⽅
   },
   template:`模板内容`
  });
  ```

## 4 插值表达式

* {{ 表达式 }}
  * 对象 (不要连续3个{{ {name:'jack'} }})
  * 字符串 {{ 'xxx' }} 
  * 判断后的布尔值 {{ true }} 
  * 三元表达式 {{ true?'是正确':'错误' }}
* 可以⽤于⻚⾯中简单粗暴的调试
* 要⽤插值表达式 必须要data中声明该属性

```
<body>
    <div id="app">
        <h2>{{ msg }}</h2>
        <h3>{{ 2 }}</h3>
        <h3>{{ "hello" }}</h3>
        <h3>{{ {id:1} }}</h3>
        <h3>{{ 1>2 ? '真的':'假的'}}</h3>
        <h3>{{ txt.split('').reverse().join('') }}</h3>
        <h1>{{ getContent() }}</h1>
        <h1>{{ msg3 }}</h1>
    </div>
    <!-- 1.引包 -->
    <script src="./vue.js"></script>
    <script>
        // console.log(Vue);

        // 2.初始化
        const vm = new Vue({
            el: '#app',
            // 数据属性
            data: {
                msg: 'hello vue',
                txt: 'hello',
                msg2: 'content',
                msg3:'<p>插值语法</p>'
            },
            // 存放的是方法
            methods: {
                getContent() {
                    return this.msg + ' ' + this.msg2;
                }
            }
        });
        console.log(vm.msg);

    </script>
</body>
```

## 5 什么是指令

* 在vue中提供了⼀些对于⻚⾯ + 数据的更为⽅便的输出, 这些操作就叫做指令, 以v-xxx表示
  * ⽐如html⻚⾯中的属性 <div v-xxx></div>div>
* ⽐如在angular中 以ng-xxx开头的就叫做指令
* 在vue中 以v-xxx开头的就叫做指令
* 指令中封装了⼀些DOM⾏为, 结合属性作为⼀个暗号, 暗 号有对应的值,根据不同的值，框架会进⾏相关DOM操作 的绑定

## 6 vue中常⽤的v-指令演示

* v-text:元素的textContent属性,必须是双标签 跟{{ }}效果 是⼀样的 使⽤较少

* v-html: 元素的innerHTML
  
  ```
  <body>
      <div id='app'>
          <h1>{{ msg }}</h1>
          <h2 v-text='msg'></h2>
          <div v-html='htmlMsg'></div>
      </div>
      <script src="./vue.js"></script>
      <script>
          // {{}}和v-text的作用是一样的 都是插入值 直接渲染 innerText
          // v-html既能插入值 又能插入标签 innerHTML
          new Vue({
              el:'#app',
              data:{
                  msg:"插入标签",
                  htmlMsg:'<h3>小马哥</h3>'
              }
          })
      </script>
  </body>
  ```

* v-if : 判断是否插⼊这个元素,相当于对元素的销毁和创建

* v-else-if

* v-else

* v-show 隐藏元素 如果确定要隐藏, 会给元素的style加 上display:none。是基于css样式的切换

* v-if和v-show的区别 (官⽹解释)

v-if 是“真正”的条件渲染，因为它会确保在切换过程中条 件块内的事件监听器和⼦组件适当地被销毁和重建。 v-if 也是惰性的：如果在初始渲染时条件为假，则什么也 不做——直到条件第⼀次变为真时，才会开始渲染条件块。 相⽐之下， v-show 就简单得多——不管初始条件是什么， 元素总是会被渲染，并且只是简单地基于 CSS 进⾏切换。 ⼀般来说， v-if 有更⾼的切换开销，⽽ v-show 有更⾼的 初始渲染开销。因此，如果需要⾮常频繁地切换，则使⽤ v-show 较好；如果在运⾏时条件很少改变，则使⽤ v-if 较好。

```
<body>
    <div id='app'>
        <div v-if = "isShow">
            显示    
        </div>
        <div v-else>
            隐藏
        </div>
        <h3 v-show = 'show'>小马哥</h3>
    </div>
    <script src="./vue.js"></script>
    <script>
        // v-if v-else-if  v-else      v-show
        new Vue({
            el: '#app',
            data: {
               isShow:Math.random() > 0.5,
               show:false
            }
        })
    </script>
</body>
```

* v-bind使⽤
  * 给元素的属性赋值
  * 可以给已经存在的属性赋值 input value
  * 也可以给⾃定义属性赋值 mydata
* 语法 在元素上 v-bind:属性名="常量||变量名"
* 简写形式 :属性名="变量名"

```
<div v-bind:原属性名="变量"></div>
<div :属性名="变量">
</div>
```

* v-on的使⽤
* 处理⾃定义原⽣事件的,给按钮添加click并让使⽤变量的 样式改变
* 普通使⽤ v-on:事件名="表达式||函数名"
* 简写⽅式 @事件名="表达式"
* 事件修饰符
  - `.stop`
  - `.prevent`
  - `.self`
  - `.capture`
  - `.once`
  - `.passive`
* 按键修饰符
  - `.enter`
  - `.tab`
  - `.delete` (捕获“Delete”和“Backspace”两个按键)
  - `.esc`
  - `.space`
  - `.up`
  - `.down`
  - `.left`
  - `.right`
* 系统按键修饰符
  - `.ctrl`
  - `.alt`
  - `.shift`
  - `.meta`

```
    <div id='app'>
        <button v-on:click.once="handleClick">+1</button>
        <button @click='changeClick'>切换</button>
        <input v-on:keyup.up="submit">
    </div>
```

* v-for的使⽤
  * 基本语法 v-for="item in arr" 
  * 对象的操作 v-for="item in obj" 
  * 如果是数组没有id 
    * v-for="(item,index) in arr" :class="index" 
  * v-for的优先级最⾼

```
<body>
    <div id='app'>
        <div>
            <ul>
                <li v-for = '(item,index) in menus' :key = 'item.id'>
                    <h3>{{index}} - id:{{item.id}} 菜名:{{item.name}}</h3>
                </li>
            </ul>
            <ol>
                <li v-for = "(val,key) in obj" :key='key'>
                    {{key}}---{{val}}
                </li>
            </ol>
        </div>
    </div>
    <script src="./vue.js"></script>
    <script>
        new Vue({
            el: '#app',
            data: {
                menus:[
                    {id:1,name:'大腰子'},
                    {id:2,name:'烤鸡翅'},
                    {id:3,name:'烤韭菜'},
                    {id:4,name:'烤大蒜'},
                ],
                obj:{
                    title:'hello 循环',
                    author:'小马哥'
                }
            },
            methods: {
            },
        })
    </script>
</body>
```

* v-model 双向的数据绑定
* 双向数据流（绑定）
  * ⻚⾯改变影响内存(js)
  * 内存(js)改变影响⻚⾯

```
<body>
    <div id='app'>
        <p>{{msg}}</p>
        <input type="text" v-model='msg'>
        <!-- 复选框单选 -->
        <label for="checkbox">{{checked}}</label>
        <input type="checkbox" id='checkbox' v-model='checked'>
        <div class="box">
            <label for="a">黄瓜</label>
            <input type="checkbox" id='a' value='黄瓜' v-model='checkedNames'>
            <label for="b">西红柿</label>
            <input type="checkbox" id='b' value='西红柿' v-model='checkedNames'>
            <label for="c">芸豆</label>
            <input type="checkbox" id='c' value='芸豆' v-model='checkedNames'>
            <br />
            <span>{{checkedNames}}</span>
        </div>
        <label>{{txt}}</label>
        <input v-model.lazy="txt">
        <label>{{msg2}}</label>
        <input v-model.trim="msg2">
    </div>
    <script src="./vue.js"></script>
    <script>
        new Vue({
            el: '#app',
            data: {
                msg: '小马哥',
                msg2: '',
                txt: '',
                checked: false,
                checkedNames: []
            },
            methods: {

            },
        })
    </script>
</body>
```

* v-bind 和 v-model 的区别?
* input v-model="name"
  * 双向数据绑定 ⻚⾯对于input的value改变，能影响内 存中name变量
  * 内存js改变name的值，会影响⻚⾯重新渲染最新值
* input :value="name"
  * 单向数据绑定 内存改变影响⻚⾯改变
* v-model: 其的改变影响其他 v-bind: 其的改变不影响其 他
* v-bind就是对属性的简单赋值,当内存中值改变，还是会 触发重新渲染

## 7 侦听器

* 侦听器
  * 我们需要在状态变化时执行一些“副作用：例如更改 DOM，或是根据异步操作的结果去修改另一处的状态

```
            watch: {
                // key是属于data对象的属性名 value:监视后的行为 newV :新值 oldV:旧值
                'msg':function(newV,oldV){
                    // console.log(newV,oldV);
                    if(newV === '100'){
                        console.log('hello');
                    }

                },
                // 深度监视： Object |Array
                "stus":{
                    deep:'true',
                    handler:function(newV,oldV){
                        console.log(newV[0].name);

                    }
                }
            }
```

## 8 计算属性

* computed 

```
<body>
    <div id='app'>
        {{reverseMsg}}
        <h3>{{fullName}}</h3>
        <button @click='handleClick'>改变</button>
    </div>
    <script src="./vue.js"></script>
    <script>
        new Vue({
            el: '#app',
            data: {
                msg: 'hello world',
                firstName: '小马',
                lastName: '哥'
            },
            methods: {
                handleClick(){
                    this.msg = '计算属性computed';
                    this.lastName = '妹';
                }
            },
            computed: {
                // computed默认只有getter方法
                // 计算属性最大的优点：产生缓存 如果数据没有发生变化 直接从缓存中取
                reverseMsg: function () {
                    return this.msg.split('').reverse().join('')
                },
                fullName: function () {
                    return this.firstName + this.lastName;
                }
            },

        })
    </script>
</body>
```

* setter 方法

```
<body>
    <div id='app'>
        {{content}}
        <input type="text" v-model='content' @input = 'handleInput'>
        <button @click='handleClick'>获取</button>
    </div>
    <script src="./vue.js"></script>
    <script>
        new Vue({
            el: '#app',
            data: {
                msg: '',
            },
            methods: {
               handleInput:function(event){
                   const {value} = event.target;
                   this.content = value;
               },
               handleClick(){
                //    console.log();
                if (this.content) {
                    console.log(this.content);
                }

               }
            },
            computed: {
              content:{
                  set:function(newV){
                      this.msg = newV;
                  },
                  get:function(){
                      return this.msg;
                  }
              }
            },
        })
    </script>
</body>
```

## 9 过滤器

```
<body>
    <div id="app">
        <h3>{{price | myPrice('¥')}}</h3>
        <h3>{{msg|myReverse}}</h3>
    </div>
    <script src="./vue.js"></script>
    <script>
        // 创建全局过滤器
        Vue.filter('myReverse', (val) => {
            return val.split('').reverse().join('');
        })
        // 为数据添油加醋
        // ¥  $20
        new Vue({
            el: '#app',
            data: {
                price: 10,
                msg:'hello 过滤器'
            },
            // 局部过滤器
            filters: {
                myPrice: function (price, a) {
                    return a + price;

                }
            }

        })
    </script>
</body>
```

# 二 组件

## 1 组件基础

### 1.1 什么是组件

其实突然出来的这个名词,会让您不知所以然,如果⼤家使⽤过 bootstrap的同学⼀定会对这个名词不陌⽣,我们其实在很早的时候就 接触这个名词 通常⼀个应⽤会以⼀颗嵌套的组件树的形式来阻⽌:

### 1.2 局部组件

使⽤局部组件的打油诗: 建⼦ 挂⼦ ⽤⼦

```
<body>
    <div id="app">
      <!-- 3 使用组件 -->
      <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>
      // 使用局部组件的打油诗: 建子 挂子 用子
      // 1.创建组件
      // 注意:在组件中这个data必须是一个函数,返回一个对象
      const App = {
        data() {
          return {
            msg: "我是App组件",
          };
        },
        template: `
        <div>
          <h1>{{ msg }}</h1>
        </div>
        `,
      };

      // 2. 创建 Vue 实例并挂载
      const vm = new Vue({
        el: "#app",
        components: {
          App,
        },
      });
    </script>
  </body>
```

### 1.3 全局组件

通过 Vue.component(组件名,{}) 创建全局组件,此时该全局组件可以在 任意模板(template)中使⽤

```
<body>
    <div id="app">
      <!-- 2 使用组件 -->
      <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>

      // 1.创建组件
      // 注意:在组件中这个data必须是一个函数,返回一个对象
      Vue.component("App", {
        data() {
          return {
            msg: "我是App组件",
          };
        },
        template: `
        <div>
          <h1>{{ msg }}</h1>
        </div>
        `,
      });

      // 2. 创建 Vue 实例并挂载
      const vm = new Vue({
        el: "#app",
      });
    </script>
  </body>
```

### 1.4 组件通信

#### 1.4.1 ⽗传⼦

如果⼀个⽹⻚有⼀个博⽂组件,但是如果你不能向这个组件传递某⼀篇 博⽂的标题和内容之类想展示的数据的话,它是没有办法使⽤的.这也 正是prop的由来

**⽗组件往⼦组件通信:通过Prop向⼦组件传递数据**

* 在⼦组件中声明props接收在⽗组件挂载的属性
* 可以在⼦组件的template中任意使⽤
* 在⽗组件绑定⾃定义的属性

```
<body>
    <div id="app">
      <!-- 2 使用组件 -->
      <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>
      // 全局组件

      // 父传子:通过prop来进行通信

      // 1.在子组件中声明props接收在父组件挂载的属性
      // 2.可以在子组件的template中任意使用
      // 3.在父组件绑定自定义的属性
      Vue.component("Children", {
        template: `
        <div>
          <h3>子组件</h3>
          <h1>父组件传过来的值: {{ childData }}</h1>
        </div>
        `,
        props: ["childData"],
      });

      Vue.component("App", {
        data() {
          return {
            msg: "我是App组件",
          };
        },
        template: `
        <div>
          <h1>{{ msg }}</h1>
          <Children :childData="msg"></Children>
        </div>
        `,
      });

      // 2. 创建 Vue 实例并挂载
      const vm = new Vue({
        el: "#app",
      });
    </script>
  </body>
```

#### 1.4.2 ⼦传⽗

⽹⻚上有⼀些功能可能要求我们和⽗组件组件进⾏沟通

**⼦组件往⽗组件通信: 监听⼦组件事件,使⽤事件抛出⼀个值**

* 在⽗组件中 ⼦组件上绑定⾃定义事件
* 在⼦组件中 触发原⽣的事件 在事件函数通过this.$emit触发⾃定 义的事件

```
<body>
    <div id="app">
      <!-- 2 使用组件 -->
      <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>
      // 全局组件
      // 子往父传值
      // 在父组件中 子组件上绑定自定义事件
      // 在子组件中 触发原生的事件 在事件函数通过this.$emit触发自定义的事件
      Vue.component("Children", {
        template: `
          <div>
            <h3>我是一个子组件</h3>   
            <input type="text" @input = 'handleInput'/>
        </div>
        `,
        props: ["childData"],
        methods: {
          handleInput(e) {
            const val = e.target.value;
            // 触发自定义事件
            this.$emit("inputHandler", val);
          },
        },
      });

      Vue.component("App", {
        data() {
          return {
            msg: "我是App组件",
            newVal: "",
          };
        },
        template: `
        <div>
          <h1>{{ msg }}</h1>
          <Children @inputHandler = "input"></Children>
          <h3>{{newVal }}</h3>
        </div>
        `,
        methods: {
          input(val) {
            this.newVal = val;
          },
        },
      });

      // 2. 创建 Vue 实例并挂载
      const vm = new Vue({
        el: "#app",
      });
    </script>
  </body>
```

#### 1.4.3 平⾏组件

在开发中,可能会存在没有关系的组件通信,⽐如有个博客内容显示组 件,还有⼀个表单提交组件,我们现在提交数据到博客内容组件显示,这 显示有点费劲.

**为了解决这种问题,在vue中我们可以使⽤bus,创建中央事件总线**

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>

    </div>
    <script src="./vue.js"></script>
    <script>
        const bus = new Vue();
        // 中央事件总线 bus
        Vue.component('B', {
            data() {
                return {
                    count: 0
                }
            },
            template: `
                <div>{{count}}</div>
            `,
            created(){
                // $on 绑定事件
                bus.$on('add',(n)=>{
                    this.count+=n;
                })
            }
        })

        Vue.component('A', {
            data() {
                return {

                }
            },
            template: `
                <div>
                 <button @click='handleClick'>加入购物车</button> 

                </div>
            `,
            methods:{
                 handleClick(){
                    // 触发绑定的函数 // $emit 触发事件
                     bus.$emit('add',1);
                 }
            }
        })


        const App = {
            data() {
                return {

                }
            },

            template: `
                <div>
                    <A></A>
                    <B></B>
                </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                // 2.挂载子组件
                App
            }

        })
    </script>
</body>
```

#### 1.4.4 其它组件通信⽅式

**⽗组件 provide来提供变量,然后再⼦组件中通过inject来注⼊变量.⽆论组件嵌套多深**

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>
        Vue.component('B', {
            data() {
                return {
                    count: 0
                }
            },
            inject:['msg'],
            created(){
                console.log(this.msg);

            },
            template: `
                <div>
                    {{msg}}
                </div>
            `,
        })

        Vue.component('A', {
            data() {
                return {

                }
            },
            created(){
                // console.log(this.$parent.$parent);
                // console.log(this.$children);
                console.log(this);


            },
            template: `
                <div>
                     <B></B>
                </div>
            `
        })


        const App = {
            data() {
                return {
                    title:"老爹"
                }
            },
            provide(){
                return {
                    msg:"老爹的数据"
                }
            },
            template: `
                <div>
                    <A></A>
                </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                // 2.挂载子组件
                App
            }

        })
    </script>
</body>
```

### 1.5 插槽

#### 1.5.1 匿名插槽

**⼦组件定义 slot 插槽，但并未具名，因此也可以说是默认插槽。只要 在⽗元素中插⼊的内容，默认加⼊到这个插槽中去**

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>

    </div>
    <script src="./vue.js"></script>
    <script>

        Vue.component('MBtn',{
            template:`
                <button>
                    <slot></slot>
                </button>
            `
        })

        const App = {
            data() {
                return {
                    title: "老爹"
                }
            },

            template: `
                <div>
                    <m-btn><a href="#">登录</a></m-btn>
                    <m-btn>注册</m-btn>
                </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                // 2.挂载子组件
                App
            }

        })
    </script>
</body>
```

#### 1.5.2 具名插槽

具名插槽可以出现在不同的地⽅，不限制出现的次数。只要匹配了 name 那么这些内容就会被插⼊到这个 name 的插槽中去

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>

    </div>
    <script src="./vue.js"></script>
    <script>
        // 只要匹配到slot标签的name值 template中的内容就会被插入到这个槽中
        Vue.component('MBtn', {
            template: `
                <button>
                     <slot name='submit'></slot>
                     <slot name='login'></slot>
                     <slot name='register'></slot>
                </button>
            `
        })

        const App = {
            data() {
                return {
                    title: "老爹"
                }
            },

            template: `
                <div>
                     <m-btn>
                         <template slot='submit'>
                             提交
                         </template>
                     </m-btn>

                    <m-btn>
                        <template slot='login'>
                            <a href="#">登录</a>
                        </template>
                    </m-btn>

                    <m-btn>
                        <template slot='register'>
                            注册
                        </template>
                    </m-btn>
                </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                // 2.挂载子组件
                App
            }

        })
    </script>
</body>
```

#### 1.5.3 作⽤域插槽

**通常情况下普通的插槽是⽗组件使⽤插槽过程中传⼊东⻄决定了插槽 的内容。但有时我们需要获取到⼦组件提供的⼀些数据，那么作⽤域 插槽就排上⽤场了**

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>

    </div>
    <script src="./vue.js"></script>
    <script>
        // 已经开发了一个待办事项列表的组件,很多模块都在
        // A B
        // 1.之前数据格式和引用接口不变,正常显示
        // 2.新功能模块增加对勾
        const todoList = {
            data() {
                return {

                }
            },
            props: {
                todos: Array,
                defaultValue: []
            },
            template: `
        <ul>
            <li v-for='item in todos' :key='item.id'>
                <slot :itemValue = 'item'>

                </slot>
                 {{item.title}}

            </li>
        </ul>
        `
        }
        const App = {
            data() {
                return {
                    todoList: [{
                            title: '大哥你好么',
                            isComplate: true,
                            id: 1
                        },
                        {
                            title: '小弟我还行',
                            isComplate: false,
                            id: 2
                        },
                        {
                            title: '你在干什么',
                            isComplate: false,
                            id: 3
                        },
                        {
                            title: '抽烟喝酒烫头',
                            isComplate: true,
                            id: 4
                        }
                    ]
                }
            },
            components: {
                todoList
            },
            template: `
                  <todoList :todos='todoList'>
                     <template v-slot='data'>
                        <input type="checkbox" v-model='data.itemValue.isComplate' />
                    </template>
                  </todoList>
        `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                App
            }

        })
    </script>
</body>
```

## 2 ⽣命周期

“你不需要⽴⻢弄明⽩所有的东⻄，不过随着你的不断学习和使⽤，它 的参考价值会越来越⾼。 当你在做项⽬过程中,遇到了这种问题的时候,再回过头来看这张图

### 2.1 什么是⽣命周期

每个 Vue 实例在被创建时都要经过⼀系列的初始化过程。 例如：从 开始创建、初始化数据、编译模板、挂载Dom、数据变化时更新 DOM、卸载等⼀系列过程。 我们称 这⼀系列的过程 就是Vue的⽣命 周期。 通俗说就是Vue实例从创建到销毁的过程，就是⽣命周期。 同 时在这个过程中也会运⾏⼀些叫做⽣命周期钩⼦的函数，这给了⽤户 在不同阶段添加⾃⼰的代码的机会，利⽤各个钩⼦来完成我们的业务 代码。

### 2.2 ⽣命周期钩⼦

#### 2.2.1 beforCreate

实例初始化之后、创建实例之前的执⾏的钩⼦事件

```
Vue.component('Test',{
 data(){
 return {
 msg:'⼩⻢哥'
 }
 },
 template:`
 <div>
 <h3>{{msg}}</h3>
 </div>
 `,
 beforeCreate:function(){
 // 组件创建之前
 console.log(this.$data);//undefined
 }
})
```

#### 2.2.2 created

实例创建完成后执⾏的钩⼦

```
created() {
 console.log('组件创建', this.$data);
}
```

#### 2.2.3 beforeMount

将编译完成的html挂载到对应的虚拟DOM时触发的钩⼦ 此时⻚⾯并 没有内容。 即此阶段解读为: 即将挂载

```
beforeMount(){
 // 挂载数据到 DOM之前会调⽤
 console.log('DOM挂载之
前',document.getElementById('app'));
}
```

#### 2.2.4 mounted

```
mounted() {
 console.log('DOM挂载完
成',document.getElementById('app'));
}
```

#### 2.2.5 beforeUpdate和updated

```
beforeUpdate() {
 // 在更新DOM之前 调⽤该钩⼦，应⽤：可以获取原始的DOM
 console.log('DOM更新之前',
document.getElementById('app').innerHTML);
},
updated() {
 // 在更新DOM之后调⽤该钩⼦，应⽤：可以获取最新的DOM
 console.log('DOM更新完成',
document.getElementById('app').innerHTML);
}
```

#### 2.2.6 beforeDestroy和destroyed

当⼦组件在v-if的条件切换时,该组价处于创建和销毁的状态

```
beforeDestroy() {
 console.log('beforeDestroy');
},
destroyed() {
 console.log('destroyed');
},
```

#### 2.2.7 activated和deactivated

当配合vue的内置组件  ⼀起使⽤的时候,才会调⽤下⾯此 ⽅法 组件的作⽤它可以缓存当前组件

```
activated() {
 console.log('组件被激活了');
},
deactivated() {
 console.log('组件被停⽤了');
},
```

## 3 组件进阶

### 3.1 异步组件

* 在⼤型应⽤中，我们可能需要将应⽤分割成⼩⼀些的代码块，并且只 在需要的时候才从服务器加载⼀个模块。为了简化，Vue 允许你以⼀ 个⼯⼚函数的⽅式定义你的组件，这个⼯⼚函数会异步解析你的组件 定义。Vue 只有在这个组件需要被渲染的时候才会触发该⼯⼚函数， 且会把结果缓存起来供未来重渲染。例

```
<body>
    <div id="app">
        <App></App>
    </div>
    <script src="./vue.js"></script>
    <script type='module'>
        import xxx from './modules.js';

        const App = {
            data() {
                return {
                    isShow: false
                }
            },
            methods: {
                asyncLoad() {
                    this.isShow = !this.isShow;
                }
            },
            components: {
                Test:()=>import('./Test.js')
            },
            template: `
                 <div>            
                    <button @click='asyncLoad'>异步加载</button>
                    <Test v-if='isShow'></Test>
                 </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                App
            }

        })
    </script>
</body>
```

Test.js

```
export default  {
    data() {
        return {
            msg: '小马哥'
        }
    },
    template: `
                <h3>{{msg}}</h3>
            `
}
```

### 3.2 获取DOM和⼦组件对象

**尽管存在 prop 和事件，有的时候你仍可能需要在 JavaScript ⾥直接 访问⼀个⼦组件。为了达到这个⽬的，你可以通过 ref 特性为这个 ⼦组件赋予⼀个 ID 引⽤。**

```
<body>
    <div id="app">
        <!-- 3.使用子组件 -->
        <App></App>

    </div>
    <script src="./vue.js"></script>
    <script>

        Vue.component('Test', {
            data() {
                return {
                    msg: "小马哥",
                }
            },
            template: `
                <div>
                    <h3>{{msg}}</h3>    
                </div>
            `,
        }

        const App = {
            data() {
                return {
                }
            },
            mounted(){
                // 1.如果给标签添加ref,获取的就是真实的DOM节点
                // 2.如果给子组件添加ref,获取的是当前子组件对象
                console.log(this.$refs.btn);
                // 加载页面,自动获取焦点
                this.$refs.input.focus();
                console.log(this.$refs.test);

            },
            components: {},
            template: `
                 <div>
                    <Test ref='test'></Test>
                    <input type="text" ref='input'/>
                    <button ref='btn'>改变生死</button>
                </div>
            `,
        }
        new Vue({
            el: '#app',
            data: {

            },
            components: {
                App
            }

        })
    </script>
</body>
```

### 3.3 nextTick的⽤法

将回调延迟到下次 DOM 更新循环之后执⾏。在修改数据之后⽴即使 ⽤它，然后等待 DOM 更新

有些事情你可能想不到,vue在更新DOM时是异步执⾏的.只要侦听到 数据变化,Vue将开启⼀个队列,并缓存在同⼀事件循环中发⽣的所有数 据变更.如果同⼀个wather被多次触发,只会被推⼊到队列中⼀次.这种 在缓冲时去除重复数据对于避免不必要的计算和 DOM 操作是⾮常重 要的。然后，在下⼀个的事件循环“tick”中，Vue 刷新队列并执⾏实际 (已去重的) ⼯作

```
<body>
    <div id="app">
        <h3>{{message}}</h3>
    </div>
    <script src="./vue.js"></script>
    <script>
        const vm = new Vue({
            el:'#app',
            data:{
                message:'小马哥'
            }
        })
        vm.message = 'new Message';
        // console.log(vm.$el.textContent);
        // 为了数据变化之后等待vue完成更新DOM,可以在数据变化之后立即使用Vue.nextTick 在当前的回调函数中能获取最新的DOM
        Vue.nextTick(()=>{
           console.log(vm.$el.textContent); 
        })

    </script>
</body>
```

### 3.4 nextTick的应⽤

有个需求: 在⻚⾯拉取⼀个接⼝，这个接⼝返回⼀些数据，这些数据是这个 ⻚⾯的⼀个浮层组件要依赖的，然后我在接⼝⼀返回数据就展示 了这个浮层组件，展示的同时，上报⼀些数据给后台（这些数据 就是⽗组件从接⼝拿的），这个时候，神奇的事情发⽣了，虽然 我拿到数据了，但是浮层展现的时候，这些数据还未更新到组件 上去,上报失败

```
<body>
    <div id='app'>
        <App></App>
    </div>
    <script src="./vue.js"></script>
    <script>
        /* 
        需求:
            在页面拉取一个接口，这个接口返回一些数据，这些数据是这个页面的一个浮层组件要依赖的，
            然后我在接口一返回数据就展示了这个浮层组件，展示的同时，
            上报一些数据给后台（这些数据是父组件从接口拿的），
            这个时候，神奇的事情发生了，虽然我拿到数据了，但是浮层展现的时候，
            这些数据还未更新到组件上去,上报失败
        */
        const Pop = {
            data() {
                return {
                    isShow: false
                }
            },
            props: {
                name: {
                    type: String,
                    default: ''
                },
            },
            template: `
                <div v-if='isShow'>
                    {{name}}
                </div>
            `,
            methods: {
                show() {
                    this.isShow = true; //弹窗组件展示
                    console.log(this.name);

                }
            },
        }
        const App = {
            data() {
                return {
                    name: ''
                }
            },
            created() {
                //    模拟异步请求
                setTimeout(() => {
                    // 数据更新
                    this.name = '小马哥';
                    this.$nextTick(()=>{
                        this.$refs.pop.show();
                    })
                }, 1000);
            },
            components: {
                Pop
            },
            template: `<pop ref='pop' :name='name'></pop>`
        }
        const vm = new Vue({
            el: '#app',
            components: {
                App
            }
        })
    </script>
</body>
```

### 3.5 对象变更检测注意事项

由于JavaScript的限制,Vue不能检测对象属性的添加和删除 对于已经创建的实例,Vue不允许动态添加根级别的响应式属性.但是,可 以通过 Vue.set(object,key,value) ⽅法向嵌套独享添加响应式属性

```
<body>
    <div id="app">
        <h3>
            {{user.name}},{{user.age}},{{user.phone}}
            <button @click='handlerAdd'>添加属性</button>
        </h3>
    </div>
    <script src="./vue.js"></script>
    <script>
        // Vue不能检测对象属性的添加和删除
        new Vue({
            el:"#app",
            data:{
                user:{}
            },
            methods: {
                handlerAdd() {
                    // Vue.$set(object,key,value)添加响应式属性
                    // this.user.age = 20;
                    // this.$set(this.user,'age',20);

                    // 添加多个响应式属性
                    this.user = Object.assign({},this.user,{
                        age:20,
                        phone:18511803134
                    })
                }
            },
            created(){
                setTimeout(() => {
                    this.user = {
                        name:"张三"
                    }
                }, 1250);
            }
        })
    </script>
</body>
```

### 3.6 混⼊mixin偷懒

混⼊(mixin)提供了⼀种⾮常灵活的⽅式,来分发Vue组件中的可复 ⽤功能.⼀个混⼊对象可以包含任意组件选项. ⼀个混⼊对象可以包含任意组件选项。当组件使⽤混⼊对象时， 所有混⼊对象的选项将被“混合”进⼊该组件本身的选项。

```
<body>
    <div id='app'>
        {{msg}}
    </div>
    <script src="./vue.js"></script>
    <script>
        const myMixin = {
            data(){
                return {
                    msg:"123"
                }
            },
            created(){
                this.sayHello();
            },
            methods: {
                sayHello() {
                    console.log('hello mixin');
                }
            },
        }
        // mixin来分发Vue组件中的可复用功能

        new Vue({
            el:"#app",
            data:{
                msg:"小马哥"
            },
            created(){
                console.log(1111);

            },
            mixins:[myMixin]
        })

    </script>
</body>
```

### 3.7 mixin应⽤

有⼀种很难常⻅的情况:有两个⾮常相似的组件,他们共享同样的基本 函数,并且他们之间也有⾜够的不同,这时你站在了⼀个⼗字路⼝：我是 把它拆分成两个不同的组件？还是只使⽤⼀个组件，创建⾜够的属性 来改变不同的情况。 这些解决⽅案都不够完美：如果你拆分成两个组件，你就不得不冒着 如果功能变动你要在两个⽂件中更新它的⻛险，这违背了 DRY 前提。 另⼀⽅⾯，太多的属性会很快会变得混乱不堪，对维护者很不友好， 甚⾄是你⾃⼰，为了使⽤它，需要理解⼀⼤段上下⽂，这会让你感到 失望。 使⽤混合。Vue 中的混合对编写函数式⻛格的代码很有⽤，因为函数 式编程就是通过减少移动的部分让代码更好理解。混合允许你封装⼀ 块在应⽤的其他组件中都可以使⽤的函数。如果被正确的使⽤，他们 不会改变函数作⽤域外部的任何东⻄，所以多次执⾏，只要是同样的 输⼊你总是能得到⼀样的值。这真的很强⼤。 我们有⼀对不同的组件,他们的作⽤是切换⼀个状态布尔值,⼀个模态框 和⼀个提示框.这些提示框和模态框除了在功能,没有其它共同点:它们 看起来不⼀样,⽤法不⼀样,但是逻辑⼀样

```
<body>
    <div id='app'>

    </div>
    <script src="./vue.js"></script>
    <script>
        //    一个是模态框 一个提示框
        // 它们看起来不一样，用法不一样，但是逻辑一样（切换boolean）
        /* 
        // 全局的mixin 要格外小心 因为每个组件实例创建是，它都会被调用
        Vue.mixin({

        })

         */
        const toggleShow = {
            data() {
                return {
                    isShow: false
                }
            },
            methods: {
                toggleShow() {
                    this.isShow = !this.isShow
                }
            }
        }

        const Modal = {
            template: `
                <div v-if='isShow'><h3>模态框组件</h3></div>
            `,
            // 局部的mixin
            mixins: [toggleShow]

        }

        const ToolTip = {
            template: `
            <div v-if='isShow'>
                <h2>提示框组件</h2>
            </div>
         `,
            mixins: [toggleShow]
        }
        new Vue({
            el: "#app",
            data: {

            },
            components: {
                Modal,
                ToolTip
            },
            template: `
                <div>
                    <button @click='handleModel'>模态框</button>
                    <button @click='handleToolTip'>提示框</button>
                    <Modal ref='modal'></Modal>
                    <ToolTip ref='toolTip'></ToolTip>
                </div>
            `,
            methods: {
                handleModel() {
                    this.$refs.modal.toggleShow();
                },
                handleToolTip() {
                    this.$refs.toolTip.toggleShow();
                }
            },
        })
    </script>

</body>
```

## 4 单文件组件

在很多Vue项⽬中,我们使⽤ Vue.component 来定义全局组件，紧接着
⽤ new Vue({ el: '#app '}) 在每个⻚⾯内指定⼀个容器元素。
这种⽅式在很多中⼩规模的项⽬中运作的很好，在这些项⽬⾥
JavaScript 只被⽤来加强特定的视图。但当在更复杂的项⽬中，或者
你的前端完全由 JavaScript 驱动的时候，下⾯这些缺点将变得⾮常明
显：

1. 全局定义强制要求每个 component 中的命名不得重复

2. 字符串模板 缺乏语法⾼亮，在 HTML 有多⾏的时候，需要⽤到丑陋的\

3. 不⽀持 CSS 意味着当 HTML 和 JavaScript 组件化时，CSS 明显被
   遗漏

4. 没有构建步骤 限制只能使⽤ HTML 和 ES5 JavaScript, ⽽不能使⽤
   预处理器，如 Pug (formerly Jade) 和 Babel

⽂件扩展名为 .vue 的 single-file components(单⽂件组件) 为以
上所有问题提供了解决⽅法，并且还可以使⽤ webpack 或
Browserify 等构建⼯具。

### 4.1 vue cli3

#### 4.1.1 基本配置

- 安装nodejs([Node.js — Download Node.js®](https://nodejs.org/en/download/package-manager))
  
  保证Node.js8.9或更⾼版本
  
  终端中输⼊ node -v ,保证已安装成功

- 安装淘宝镜像源(http://npm.taobao.org/)
  
  ```
    npm install -g cnpm --registry=https://registry.npm.taobao.org
  ```
  
    以后的npm可以⽤cnpm代替

- 安装Vue Cli3脚⼿架
  
  ```
  cnpm install -g @vue/cli
  ```

- 检查其版本是否正确
  
  ```
  vue --version
  ```

#### 4.1.2 快速原型开发

使⽤ vue serve 和 vue build 命令对单个 *.vue ⽂件进⾏快速原型
开发，不过这需要先额外安装⼀个全局的扩展：

```
1 npm install -g @vue/cli-service-global
```

ue serve 的缺点就是它需要安装全局依赖，这使得它在不同机器上
的⼀致性不能得到保证。因此这只适⽤于快速原型开发。
