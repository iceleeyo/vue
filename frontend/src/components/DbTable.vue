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
      :page-size="pageSize" layout="total, prev, pager, next, jumper" :total="total">
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
      total: 1000,
      pageSize: 10,
      pageSizes: [5, 10, 20, 30],
      currentPage: 1,
      secCode: '',
      secName: '',
      pickerModel: ''
    }
  },

  mounted () {
    this.getCustomers(this.currentPage, this.pageSize);
    Bus.$on('filterResultData', (data) => {
      this.tableData = data.list;
      this.total = data.total;
//      this.pageSize = data.pageSize;
      this.secCode = data.secCode;
      this.secName = data.secName;
      this.pickerModel = data.pickerModel;
  })
  },

  methods: {
    handleSizeChange(size) {
      console.log("size:" + size);
      this.pageSize = size;
      console.log("this.pageSize:" + this.pageSize);
//      this.currentPage = 1;
      this.getCustomers(this.currentPage, this.pageSize);
//      if (this.currentPage === 1) {
//        this.getCustomers(this.currentPage, this.pagesize);
//      } else {
//        this.currentPage = 1;
//      }
//        this.getCustomers();
//        this.pageSize = size;
//        this.getCustomers();
      this.$emit('handleSizeChange', size);
    },

    handleCurrentChange(val) {
      this.currentPage = val;
      this.getCustomers(this.currentPage, this.pagesize);
    },

    getCustomers: function (currentPage, pageSize) {
      var begin = this.pickerModel ? this.pickerModel[0] : '';
      var end = this.pickerModel ? this.pickerModel[1] : '';
      var beginStr = begin instanceof Date ? this.$moment(begin, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : begin;
      var endStr = end instanceof Date ? this.$moment(end, this.$moment.ISO_8601).format("YYYY-MM-DD HH:mm:ss") : end;

      this.$axios.get(this.apiUrl, {
        params: {
          page: currentPage,
          limit: 10,
          secCode: this.secCode,
          secName: this.secName,
          begin: beginStr,
          end: endStr
        }
      }).then((response) => {
        this.tableData = response.data.list;
        this.total = response.data.total;
//        this.pageSize = response.data.pageSize;
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
    var percent = Math.round(parseFloat(row.RETURN_RATE)) / 100.00 + "%";
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
