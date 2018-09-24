<template>
  <el-form :inline="true" :model="formInline">
    <el-form-item label="">
      <el-date-picker
        v-model="formInline.pickerModel"
        type="datetimerange"
        :picker-options="pickerOptions"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss"
        align="right" style="width: 360px"/>
    </el-form-item>

    <el-form-item label="股票代码">
      <el-input v-model="formInline.secCode" placeholder="股票代码" style="width: 120px" clearable/>
    </el-form-item>

    <el-form-item label="股票名称">
      <el-input v-model="formInline.secName" placeholder="股票名称" style="width: 120px" clearable/>
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="onSubmit('formInline')">查询</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
    import lodash from 'lodash'
    import Bus from '../eventBus'

    let today = new Date();

    export default {
      name: 'db-filterinput',
      data() {
        return {
          pickerOptions: {
            shortcuts: [{
              text: '最近一周',
              onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近一个月',
                onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                picker.$emit('pick', [start, end]);
              }
            }, {
              text: '最近三个月',
                onClick(picker) {
                const end = new Date();
                const start = new Date();
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                picker.$emit('pick', [start, end]);
              }
            }]
          },
          formInline: {
            pickerModel: [new Date(today.getFullYear(), today.getMonth(), today.getDate(), 9, 30), new Date(today.getFullYear(), today.getMonth(), today.getDate(), 15, 30)],
            secCode: '',
              secName: ''
          },
          formLabelWidth: '120px',
          datePicker: ''
        }
      },

      watch: {
        'formInline.pickerModel': 'filterResultData',
        'formInline.secCode': 'filterResultData',
        'formInline.secName': 'filterResultData'
      },

      methods: {
        onSubmit(formName){
          var begin = this.formInline.pickerModel ? this.formInline.pickerModel[0] : '';
          var end = this.formInline.pickerModel ? this.formInline.pickerModel[1] : '';
          var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : begin;
          var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : end;

          this.$axios.get("http://127.0.0.1:8000/api/backTest/getBackTestAll", {
            params: {
              secCode: this.formInline.secCode,
              secName: this.formInline.secName,
              begin: beginStr,
                // JSON.stringify(begin),
              end: endStr
            }
          }).then((response) => {
            response.data['secCode'] = this.formInline.secCode;
            response.data['secName'] = this.formInline.secName;
            response.data['pickerModel'] = this.formInline.pickerModel;
            Bus.$emit('filterResultData', response.data);
          }).catch(function (response) {
            console.log(response)
          })
        },

        filterResultData(curVal, oldVal){
          var begin = this.formInline.pickerModel ? this.formInline.pickerModel[0] : '';
          var end = this.formInline.pickerModel ? this.formInline.pickerModel[1] : '';
          var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : begin;
          var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : end;

          this.$axios.get("http://127.0.0.1:8000/api/backTest/getBackTestAll", {
            params: {
              'secCode': this.formInline.secCode,
              'secName': this.formInline.secName,
              'begin': beginStr,
              // JSON.stringify(begin),
              'end': endStr
            }
          }).then((response) => {
            response.data['secCode'] = this.formInline.secCode;
            response.data['secName'] = this.formInline.secName;
            response.data['pickerModel'] = this.formInline.pickerModel;
            Bus.$emit('filterResultData', response.data);
          }).catch(function (response) {
            console.log(response)
          })
        }
      }
    }
</script>
