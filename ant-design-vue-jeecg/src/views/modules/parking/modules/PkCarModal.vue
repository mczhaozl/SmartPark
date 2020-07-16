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

        <a-form-item label="车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['carNumber']" placeholder="请输入车牌号"></a-input>
        </a-form-item>
        <a-form-item :labelCol="labelCol" :wrapperCol="wrapperCol" label="车辆类型">
          <j-dict-select-tag placeholder="请选择车辆类型" :triggerChange="true" dictCode="car_type" v-decorator="['type']"/>
        </a-form-item>
        <a-form-item label="车辆颜色" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['carColor']" placeholder="请输入车辆颜色"></a-input>
        </a-form-item>
        <!-- <a-form-item label="用户Id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['userId']" placeholder="请输入用户Id"></a-input>
        </a-form-item> -->
        <a-form-item label="用户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep placeholder="请选择用户" :multi="false" :multiple="false" v-decorator="['userId', {}]"/>
        </a-form-item>
        <a-form-item label="发动机号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['motor']" placeholder="请输入发动机号"></a-input>
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
  import JDictSelectTag from '@/components/dict/JDictSelectTag'
  import {getUserList } from '@/api/api'
 
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

  export default {
    name: "PkCarModal",
    components: { 
      JDictSelectTag,
      JSelectUserByDep
      
   },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
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
          add: "/parking/pkCar/add",
          edit: "/parking/pkCar/edit",
        },
        selectedUser:[],
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
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'carNumber','type','carColor','userId','username','motor','remarks'))
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
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'carNumber','type','carColor','userId','username','motor','remarks'))
      },

      
    }
  }
</script>