<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    switchFullscreen
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['title',validatorRules.title]" placeholder="请输入标题"></a-input>
        </a-form-item>
        <a-form-item label="作者" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['author',validatorRules.author]" placeholder="请输入作者"></a-input>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag v-decorator="['type',validatorRules.type]" triggerChange dictCode="zx_type" placeholder="请选择类型"/>
        </a-form-item>
        <a-form-item label="缩略图" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-image-upload v-decorator="['thumbnail',validatorRules.thumbnail]"></j-image-upload>
        </a-form-item>

        <a-form-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-editor v-model="content"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remarks']" placeholder="请输入备注"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'
  import JEditor from '@/components/jeecg/JEditor'
  import JImageUpload from '@/components/jeecg/JImageUpload'


  export default {
    name: "PkTrafficInfoModal",
    components: {
      JEditor,
      JImageUpload
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        type:'',
        model: {},
        content:'',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/parking/pkTrafficInfo/add",
          edit: "/parking/pkTrafficInfo/edit",
        },
        validatorRules: {
          title: {
            rules: [{ required: true, message: '请输入标题'}]
          },
          author: {
            rules: [{ required: true, message: '请输入作者'}]
          },
          type: {
            rules: [{ required: true, message: '请选择类型'}]
          },
          thumbnail: {
            rules: [{ required: true, message: '请上传缩略图'}]
          }
        }
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.content = this.model.content;
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'title','author','type','thumbnail','content','count','remarks'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            this.model.content = this.content;
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      onClickEditor(){

      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'title','author','type','thumbnail','content','count','remarks'))
      },

      
    }
  }
</script>