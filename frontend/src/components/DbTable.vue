<template>
  <div>
    <el-table :data="tableData" border class="table" style="width: 100%">
      <el-table-column prop="BUY_TIME" label="日期" align="center" min-width='20%' :formatter="formatter" />
      <el-table-column prop="SEC_CODE" label="股票代码" align="center" min-width='16%' />
      <el-table-column prop="SEC_NAME" label="股票名称" align="center" min-width='16%' />
      <el-table-column prop="BUY_PRICE" label="当日预警价（元）" align="center" min-width='16%' :formatter="amountFormatter"/>
      <el-table-column prop="HOLD_DAYS" label="持有天数（天）" align="center" min-width='16%'/>
      <el-table-column prop="RETURN_RATE" label="收益率（%）" align="center" min-width='16%' :formatter="percentFormatter"/>
    </el-table>
    <!--<el-pagination
      class="pagination"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.currentPage"
      :page-sizes="pagination.pageSizes"
      :page-size="pagination.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total">
    </el-pagination>-->
    <!--<el-pagination
      class="pagination"
      v-if="pagination"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :page-size="tableCurrentPagination.pageSize"
      :page-sizes="tableCurrentPagination.pageArray"
      :current-page="tableCurrentPagination.pageIndex"
      layout="total,sizes, prev, pager, next,jumper"
      :total="total">-->

    <el-pagination
      class="pagination"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="pageSizes"
      :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
    </el-pagination>
  </div>

</template>

<script>
  import Vue from 'vue'
  import Bus from '../eventBus'

  const _pageArray = [5, 10, 20, 30]; // 每页展示条数的控制集合

  export default {
    data() {
    return {
      tableData: [],
      apiUrl: 'http://127.0.0.1:8000/api/backTest/getBackTestAll',
      total: 0,
      pageSize: 5,
      pageSizes: [5, 10, 20, 30],
      currentPage: 1,
      secCode: '',
      secName: '',
      pickerModel: ''
    }
  },

  mounted () {
    console.log("");
    this.getCustomers("this.pageSize:" + this.pageSize);
    this.getCustomers("this.total:" + this.total);
    Bus.$on('filterResultData', (data) => {
      this.tableData = data.list;
    this.total = data.total;
    this.pageSize = data.pageSize;
    this.secCode = data.secCode;
    this.secName = data.secName;
    this.pickerModel = data.pickerModel;
  })
  },

  methods: {
    handleSizeChange(size) {
      this.pageSize = size;
      if (this.currentPage === 1) {
        this.getCustomers();
      } else {
        this.currentPage = 1;
      }
//        this.getCustomers();
//        this.pageSize = size;
//        this.getCustomers();
      this.$emit('handleSizeChange', size);
    },

    handleCurrentChange(val) {
      this.currentPage = val;
      this.getCustomers();
    },

    getCustomers: function () {
      var begin = this.pickerModel ? this.pickerModel[0] : '';
      var end = this.pickerModel ? this.pickerModel[1] : '';
      var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : begin;
      var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : end;

      this.$axios.get(this.apiUrl, {
        params: {
          page: this.currentPage,
          limit: this.pageSize,
          secCode: this.secCode,
          secName: this.secName,
          begin: beginStr,
          end: endStr
        }
      }).then((response) => {
        this.tableData = response.data.list;
      this.total = response.data.total;
      this.pageSize = response.data.pageSize;
    }).catch(function (response) {
      console.log(response)
    })
  },

  formatter(row, column) {
    var date = new Date(row.BUY_TIME);
    let data = this.$moment(date, this.$moment.ISO_8601);
    return data.format('YYYY-MM-DD HH:mm:ss');
  },

  amountFormatter(row, column) {
    var price = (parseFloat(row.BUY_PRICE) / 10000).toFixed(2);
    return price;
  },

  percentFormatter(row, column) {
    var percent = Math.round(parseFloat(row.RETURN_RATE)) / 10000.00 + "%";
    var price = (parseFloat(row.RETURN_RATE) / 10000).toFixed(2);
    return percent;
  }
  }
  }
</script>

<style>
  .table {
    margin-top: 30px;
  }
  .pagination {
    margin-top: 10px;
    float: right;
  }
</style>
