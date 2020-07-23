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

        <a-form-item label="名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['name']" placeholder="请输入名称"></a-input>
        </a-form-item>
        <a-form-item label="总车位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['totalLot']" placeholder="请输入总车位" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="空闲车位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['freeLot']" placeholder="请输入空闲车位" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提示" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['tips']" placeholder="请输入提示"></a-input>
        </a-form-item>
        <a-form-item label="提示标签" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['tipTags']" placeholder="请输入提示标签"></a-input> -->
          <j-checkbox  :options="tipTagData.options" v-decorator="['tipTags', {}]"/>
        </a-form-item>
        <a-form-item label="支付标签" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['payTags']" placeholder="请输入支付标签"></a-input> -->
           <j-checkbox  :options="payTagData.options" v-decorator="['payTags', {}]"/>
        </a-form-item>
        <a-form-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
           <j-dict-select-tag placeholder="请选择停车场类型" :triggerChange="true" dictCode="parking_lot_type" v-decorator="['type']"/>
        </a-form-item>
        <a-form-item label="经度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['longitude']" placeholder="请输入经度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="纬度" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="['latitude']" placeholder="请输入纬度" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="评分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['score']" placeholder="请输入评分"></a-input>
        </a-form-item>
        <a-form-item label="图片" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-input v-decorator="['images']" placeholder="请输入图片"></a-input> -->
           <j-image-upload v-model="fileList"></j-image-upload>
        </a-form-item>
        <a-form-item label="支付码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['payCode']" placeholder="请输入支付码"></a-input>
        </a-form-item>
        <a-form-item label="地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['address']" placeholder="请输入地址"></a-input>
        </a-form-item>
        <a-form-item label="扩展字段1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ext1']" placeholder="请输入扩展字段1"></a-input>
        </a-form-item>
        <a-form-item label="扩展字段2" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ext2']" placeholder="请输入扩展字段2"></a-input>
        </a-form-item>
        <a-form-item label="扩展字段3" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ext3']" placeholder="请输入扩展字段3"></a-input>
        </a-form-item>
        <a-form-item label="扩展字段4" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['ext4']" placeholder="请输入扩展字段4"></a-input>
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
  import JCheckbox from '@/components/jeecg/JCheckbox'
  import JImageUpload from '@/components/jeecg/JImageUpload'


  export default {
    name: "PkParkingLotModal",
    components: { 
      JCheckbox,
      JImageUpload
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
          add: "/parking/pkParkingLot/add",
          edit: "/parking/pkParkingLot/edit",
        },
        tipTagData: {
          values: '',
          options: [
            { label: '地铁口', value: '地铁口' },
            { label: '距离近', value: '距离近' },
            { label: '可预约', value: '可预约' }
           
          ]
        },
        payTagData: {
          values: '',
          options: [
            { label: '在线支付', value: '在线支付' },
            { label: '现金', value: '现金' },
            { label: '刷卡', value: '刷卡' }
           
          ]
        },
        fileList:[],
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
        setTimeout(() => {
          this.fileList = record.images;
        }, 5)
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'name','totalLot','freeLot','tips','tipTags','payTags','type','longitude','latitude','score','images','payCode','address','ext1','ext2','ext3','ext4','remarks'))
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
            if(that.fileList != ''){
              this.model.images = that.fileList;
            }else{
              this.model.images = null;
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
        this.form.setFieldsValue(pick(row,'name','totalLot','freeLot','tips','tipTags','payTags','type','longitude','latitude','score','images','payCode','address','ext1','ext2','ext3','ext4','remarks'))
      },

      
    }
  }
</script>