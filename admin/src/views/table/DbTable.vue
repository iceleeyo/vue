<template>
  <div>
    <el-form :inline="true" :model="formInline" class="form">
      <el-form-item label="">
        <el-date-picker
          v-model="formInline.pickerModel"
          :picker-options="pickerOptions"
          type="datetimerange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd HH:mm:ss"
          value-format="yyyy-MM-dd HH:mm:ss"
          align="right"
          style="width: 400px"/>
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
    <el-table :data="tableData" border class="table" style="width: 100%">
      <el-table-column :formatter="formatter" prop="BUY_TIME" label="日期" align="center" min-width="20%"/>
      <el-table-column prop="SEC_CODE" label="股票代码" align="center" min-width="16%" />
      <el-table-column prop="SEC_NAME" label="股票名称" align="center" min-width="16%" />
      <el-table-column :formatter="amountFormatter" prop="BUY_PRICE" label="当日预警价（元）" align="center" min-width="16%"/>
      <el-table-column prop="HOLD_DAYS" label="持有天数（天）" align="center" min-width="16%"/>
      <el-table-column :formatter="percentFormatter" prop="RETURN_RATE" label="收益率（%）" align="center" min-width="16%"/>
    </el-table>
    <el-pagination :current-page="currentPage" :page-size="pageSize" :total="total" layout="total, prev, pager, next, jumper" class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
  </div>

</template>

<script>

const today = new Date()

export default {
  data() {
    return {
      tableData: [],
      apiUrl: 'http://127.0.0.1:8000/api/backTest/getBackTestAll',
      total: 0,
      pageSize: 5,
      pageSizes: [5, 10, 20, 30],
      currentPage: 1,
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      formInline: {
        pickerModel: [new Date(today.getFullYear(), today.getMonth(), today.getDate(), 9, 30), new Date(today.getFullYear(), today.getMonth(), today.getDate(), 15)],
        secCode: '',
        secName: ''
      },
      formLabelWidth: '120px',
      datePicker: ''
    }
  },

  mounted() {
    this.getCustomers(this.currentPage, this.pageSize)
  },

  methods: {
    handleSizeChange(size) {

    },

    handleCurrentChange(val) {
      this.currentPage = val
      this.getCustomers(this.currentPage, this.pagesize)
    },

    getCustomers: function(currentPage, pageSize) {
      var begin = this.formInline.pickerModel ? this.formInline.pickerModel[0] : ''
      var end = this.formInline.pickerModel ? this.formInline.pickerModel[1] : ''
      var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format('YYYY-MM-DD HH:mm:ss') : begin
      var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format('YYYY-MM-DD HH:mm:ss') : end

      this.$axios.get(this.apiUrl, {
        params: {
          page: currentPage,
          limit: pageSize,
          secCode: this.formInline.secCode,
          secName: this.formInline.secName,
          begin: beginStr,
          end: endStr
        }
      }).then((response) => {
        this.tableData = response.data.list
        this.total = response.data.total
      }).catch(function(response) {
        console.log(response)
      })
    },

    onSubmit(formName) {
      this.getCustomers(this.currentPage, this.pagesize)
    },

    filterResultData(curVal, oldVal) {
      this.getCustomers(this.currentPage, this.pagesize)
    },

    formatter(row, column) {
      var date = new Date(row.BUY_TIME)
      const data = this.$moment(date, this.$moment.ISO_8601)
      return data.format('YYYY-MM-DD HH:mm:ss')
    },

    amountFormatter(row, column) {
      var price = (parseFloat(row.BUY_PRICE) / 10000).toFixed(2)
      return price
    },

    percentFormatter(row, column) {
      var percent = Math.round(parseFloat(row.RETURN_RATE)) / 100.00 + '%'
      return percent
    }
  }
}
</script>

<style>
  .form {
    margin-top: 30px;
    margin-left: 100px;
  }
  .table {
    margin-top: 30px;
  }
  .pagination {
    margin-top: 10px;
    float: right;
  }
</style>
