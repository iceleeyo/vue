<template>
  <div>
    <el-table :data="tableData" border class="table" style="width: 100%">
      <el-table-column prop="HOLD_DAYS" label="持有周期（天）" align="center" min-width='20%' />
      <el-table-column prop="RETURN_RATE" label="策略收益率（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="RETURN_RATE_AN" label="年化收益率（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="RETURN_RATE_BM" label="基准收益率（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="ALPHA" label="ALPHA（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="BETA" label="BETA（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="SHARPE" label="SHARPE（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="VOLATILITY" label="年化波动率（%）" align="center" min-width='16%' :formatter="percentFormatter" />
      <el-table-column prop="MAX_DRAWDOWN" label="最大回撤率（%）" align="center" min-width='16%' :formatter="percentFormatter" />
    </el-table>
  </div>

</template>

<script>
  import Vue from 'vue'

  export default {
    data() {
      return {
        tableData: [],
        apiUrl: 'http://127.0.0.1:8000/api/backTest/getGfAllList'
      }
    },

    mounted () {
      this.getCustomers();
    },

    methods: {

      getCustomers: function () {
        this.$axios.get(this.apiUrl).then((response) => {
          console.log(response.data);
          this.tableData = response.data.table;
        }).catch(function (response) {
          console.log(response)
        })
      },

      percentFormatter(row, column, cellValue, index) {
        var percent = Math.round(parseFloat(cellValue)) / 100.00 + "%";
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
