<template>
  <el-search-table-pagination
    url="http://localhost:8000/api/backTest/getBackTestAllList"
    :columns="columns"
    :formOptions="formOptions">
  </el-search-table-pagination>
</template>

<script>
  export default {
    name: 'HelloWorld',
    data() {
    return {
      formOptions: {
        inline: true,
        submitBtnText: '查询',
        forms: [ {
          prop: 'name',
          label: 'Name'
        }, {
          prop: 'mobile',
          label: 'Mobile'
        }, {
          prop: 'sex',
          label: 'Sex',
          itemType: 'DatePicker',
          options: [ {
            value: '',
            label: 'All'
          }, {
            value: 0,
            label: 'Male'
          }, {
            value: 1,
            label: 'Female'
          }]
        }]
      },
      columns: [ {
        prop: 'BUY_TIME',
        label: '日期',
        align: center,
        minWidth: '20%'
      }, {
        prop: 'SEC_CODE',
        label: '股票代码',
        align: center,
        minWidth: '16%'
      }, {
        prop: 'SEC_NAME',
        label: '股票名称',
        align: center,
        minWidth: '16%'
      },{
        prop: 'BUY_PRICE',
        label: '当日预警价（元）',
        align: center,
        minWidth: '16%'
      },{
        prop: 'HOLD_DAYS',
        label: '持有天数（天）',
        align: center,
        minWidth: '16%'
      },{
        prop: 'RETURN_RATE',
        label: '收益率（%）',
        align: center,
        minWidth: '16%'
      }, {
        prop: 'sex',
        label: 'Sex',
        align: center,
        width: 80,
        render: row => {
          const { sex } = row
          return sex === 0 ? 'Male' : sex === 1 ? 'Female' : 'Unknow'
        }
      }]
  }
  }
  }
</script>
