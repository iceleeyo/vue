<template>
  <div>
    <el-form :inline="true" :model="formInline" class="form">
      <el-form-item label="">
        <el-date-picker
          :editable="false"
          v-model="formInline.pickerModel"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          format="yyyy-MM-dd"
          value-format="yyyy-MM-dd"/>
      </el-form-item>

      <el-form-item label="股票代码">
        <el-input v-model="formInline.secCode" placeholder="股票代码" style="width: 120px" clearable/>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" border class="table" style="width: 100%">
      <el-table-column :formatter="formatter" prop="BUY_TIME" label="日期" align="center" min-width="20%"/>
      <el-table-column prop="SEC_CODE" label="股票代码" align="center" min-width="16%" />
      <el-table-column prop="SEC_NAME" label="股票名称" align="center" min-width="16%" />
      <el-table-column :formatter="amountFormatter" prop="BUY_PRICE" label="预警价（元）" align="center" min-width="16%"/>
      <el-table-column prop="HOLD_DAYS" label="持有天数（天）" align="center" min-width="16%"/>
      <el-table-column :formatter="percentFormatter" prop="RETURN_RATE" label="收益率（%）" align="center" min-width="16%"/>
    </el-table>
    <el-pagination :current-page="currentPage" :page-size="pageSize" :total="total" layout="total, prev, pager, next, jumper" class="pagination" @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
  </div>

</template>

<script>
export default {
  data() {
    return {
      tableData: [],
      apiUrl: 'api/backTest/getBackTestAll',
      total: 0,
      pageSize: 5,
      pageSizes: [5, 10, 20, 30],
      currentPage: 1,
      formInline: {
        pickerModel: [new Date(), new Date()],
        secCode: '',
        secName: ''
      },
      formLabelWidth: '120px',
      datePicker: ''
    }
  },

  watch: {
    'formInline.pickerModel': 'filterResultData',
    'formInline.secCode': 'filterResultData'
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
      var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format('YYYY-MM-DD') : begin
      var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format('YYYY-MM-DD') : end

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
