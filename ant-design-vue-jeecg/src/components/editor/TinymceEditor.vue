<template>
  <div class="tinymce-editor">
    <editor v-model="myValue"
            :init="init"
            :disabled="disabled"
            @onClick="onClick">
    </editor>
    <!-- 预览功能，如不需要，可将下面一行代码注释删除 -->
<!--    <div v-html='myValue'></div>-->
  </div>
</template>
<script>
  import tinymce from 'tinymce/tinymce'
  import Editor from '@tinymce/tinymce-vue'
  import 'tinymce/themes/silver'
  import 'tinymce/icons/default/icons'
  // 编辑器插件plugins
  // 更多插件参考：https://www.tiny.cloud/docs/plugins/
  import 'tinymce/plugins/image'// 插入上传图片插件
  import 'tinymce/plugins/media'// 插入视频插件
  import 'tinymce/plugins/table'// 插入表格插件
  import 'tinymce/plugins/link' //超链接插件
  import 'tinymce/plugins/code' //代码块插件
  import 'tinymce/plugins/lists'// 列表插件
  import 'tinymce/plugins/contextmenu'  //右键菜单插件
  import 'tinymce/plugins/wordcount' // 字数统计插件
  import 'tinymce/plugins/colorpicker' //选择颜色插件
  import 'tinymce/plugins/textcolor'  //文本颜色插件
  import 'tinymce/plugins/fullscreen' //全屏
  import 'tinymce/plugins/help'
  import 'tinymce/plugins/charmap'
  import 'tinymce/plugins/paste'
  import 'tinymce/plugins/print'
  import 'tinymce/plugins/preview'
  import 'tinymce/plugins/hr'
  import 'tinymce/plugins/anchor'
  import 'tinymce/plugins/pagebreak'
  import 'tinymce/plugins/spellchecker'
  import 'tinymce/plugins/searchreplace'
  import 'tinymce/plugins/visualblocks'
  import 'tinymce/plugins/visualchars'
  import 'tinymce/plugins/insertdatetime'
  import 'tinymce/plugins/nonbreaking'
  import 'tinymce/plugins/autosave'
  import 'tinymce/plugins/fullpage'
  import 'tinymce/plugins/toc'

  export default {
    name: 'TinymceEditor',
    components: {
      Editor
    },
    props: {
      value: {
        type: String,
        default: ''
      },
      disabled: {
        type: Boolean,
        default: false
      },
      plugins: {
        type: [String, Array],
        default: 'lists image media table wordcount code fullscreen help  toc fullpage autosave nonbreaking insertdatetime visualchars visualblocks searchreplace spellchecker pagebreak link charmap paste print preview hr anchor'
      },
      toolbar: {
        type: [String, Array],
        default: 'newdocument undo redo | formatselect visualaid|cut copy paste selectall| bold italic underline strikethrough |codeformat blockformats| superscript subscript  | forecolor backcolor | alignleft aligncenter alignright alignjustify | outdent indent |  removeformat code  bullist numlist | lists image media table link |fullscreen help toc fullpage restoredraft nonbreaking insertdatetime visualchars visualblocks searchreplace spellchecker pagebreak anchor charmap  pastetext print preview hr'
      }
    },
    data () {
      return {
        init: {
          language_url: '/tinymce/langs/zh_CN.js',  //public目录下
          language: 'zh_CN',
          skin_url: '/tinymce/skins/ui/oxide', //public目录下
          content_css: '/tinymce/skins/content/default/content.css',
          height: 300,
          plugins: this.plugins,  // 父组件传入 或者 填写个默认的插件 要选用什么插件都可以 去官网可以查到
          toolbar: this.toolbar,  // 工具栏 我用到的也就是lists image media table wordcount 这些 根据需求而定
          images_upload_url: '', //上传路径
          branding: false,  //是否禁用“Powered by TinyMCE”
          // 此处为图片上传处理函数，这个直接用了base64的图片形式上传图片，
          // 如需ajax上传可参考https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_handler

          // 官网抄的图片上传 项目如果用了vue-resource可以用$http 因为比较懒就没改
          images_upload_handler: (blobInfo, success, failure) => {
            var xhr, formData;
            xhr = new XMLHttpRequest();
            xhr.withCredentials = false;
            xhr.open('POST', `${config.webPath}` + "basic/upload/");

            xhr.onload = function() {
              var json;
              if (xhr.status != 200) {
                failure('HTTP Error: ' + xhr.status);
                return;
              }
              json = JSON.parse(xhr.responseText);

              // if (!json || typeof json.img_url != 'string') {
              //   failure('Invalid JSON: ' + xhr.responseText);
              //   return;
              // }
              console.log(json)
              success(json.img_url);
            };

            formData = new FormData();
            formData.append('file', blobInfo.blob(), blobInfo.filename());
            console.log('formData', formData)

            xhr.send(formData);
          }
        },
        myValue: this.value
      }
    },
    mounted () {
      tinymce.init({})
    },
    methods: {
      onClick (e) {
        this.$emit('onClick', e, tinymce)
      }
    },
    watch: {
      value (newValue) {
        this.myValue = newValue
      },
      myValue (newValue) {
        this.$emit('input', newValue)
      }
    }
  }

</script>
