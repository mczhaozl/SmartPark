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

        <a-form-item label="预约车牌号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['carNumber']" placeholder="请输入预约车牌号"></a-input>
        </a-form-item>
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['startTime']" placeholder="请输入开始时间"></a-input>
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['endTime']" placeholder="请输入结束时间"></a-input>
        </a-form-item>
        <a-form-item label="停车场ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['lotId']" placeholder="请输入停车场ID"></a-input>
        </a-form-item>
        <a-form-item label="停车场" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['lotName']" placeholder="请输入停车场"></a-input>
        </a-form-item>
        <a-form-item label="预约人ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['userId']" placeholder="请输入预约人ID"></a-input>
        </a-form-item>
        <a-form-item label="预约人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['username']" placeholder="请输入预约人"></a-input>
        </a-form-item>
        <a-form-item label="是否支付" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['isPay']" placeholder="请输入是否支付"></a-input>
        </a-form-item>
        <a-form-item label="订单号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['orderId']" placeholder="请输入订单号"></a-input>
        </a-form-item>
        <a-form-item label="状态，已预约，已取消" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['status']" placeholder="请输入状态，已预约，已取消"></a-input>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['remarks']" placeholder="请输入状态"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </j-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import { validateDuplicateValue } from '@/utils/util'


  export default {
    name: "PkAppointmentModal",
    components: { 
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
          add: "/parking/pkAppointment/add",
          edit: "/parking/pkAppointment/edit",
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
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'carNumber','startTime','endTime','lotId','lotName','userId','username','isPay','orderId','status','remarks'))
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
        this.form.setFieldsValue(pick(row,'carNumber','startTime','endTime','lotId','lotName','userId','username','isPay','orderId','status','remarks'))
      },

      
    }
  }
</script>