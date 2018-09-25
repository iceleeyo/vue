<template>
  <div :class='className' :id='id' :style='{height:height,width:width}' ref='myEchart'>
  </div>
</template>
<script>

  import echarts from 'echarts'

  require('echarts/theme/macarons')

  export default {
    props: {
      className: {
        type: String,
        default: 'gfline'
      },
      id: {
        type: String,
        default: 'gfline'
      },
      width: {
        type: String,
        default: '1200px'
      },
      height: {
        type: String,
        default: '550px'
      }
    },
    data () {
      return {
        chart: null,
        apiUrl: 'http://127.0.0.1:8000/api/backTest/getGfAllList',
        buyTimes: [],
        returnRates: [],
        returnRateBms: []
      }
    },
    mounted () {
      this.initChart()
    },
    beforeDestroy () {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.chart = null
    },

    methods: {
      initChart () {
        this.chart = echarts.init(this.$refs.myEchart, 'macarons')
        this.$axios.get(this.apiUrl).then((response) => {
          console.log(response.data);
          this.buyTimes = response.data.buyTimes;
          this.returnRates = response.data.returnRates;
          this.returnRateBms = response.data.returnRateBms;
          this.chart.setOption({
            title: {
              text: '收益率',
              subtext: '收益率'
            },
            tooltip: {
              trigger: 'axis',
              // formatter: "{a} | {a0} <br/>{b} | {b0} : {c}% | {c0}g%"
            },
            legend: {
              data:['收益率','基准收益率']
            },
            toolbox: {
              show: true,
              feature: {
                mark: {
                  show: true
                },
                dataView: {
                  show: true,
                  readOnly: false
                },
                magicType: {
                  show: true,
                  type: ['line', 'bar']
                },
                restore: {
                  show: true
                },
                saveAsImage: {
                  show: true
                }
            }
            },
            calculable: true,
            xAxis: [{
              type: 'category',
              boundaryGap: false,
              data: this.buyTimes,
              axisLabel: {
                interval: 10,  
                rotate: 80 
              }
            }],
            yAxis: [{
              type: 'value',
              axisLabel: {
                formatter: '{value} %'
              }
            }],
            series: [{
              name: '收益率',
              type: 'line',
              data: this.returnRates,
              markPoint : {
                data: [ {
                  type: 'max',
                  name: '收益率最大值'
                }, {
                  type: 'min',
                  name: '收益率最小值'
                } ]
              },
              markLine: {
                data: [ {
                  type: 'average',
                  name: '平均值'
                } ]
              }
            }, {
              name: '基准收益率',
              type: 'line',
              data: this.returnRateBms,
              markPoint : {
                data: [ {
                  type: 'max',
                  name: '收益率最大值'
                }, {
                  type: 'min',
                  name: '收益率最小值'
                } ]
              },
              markLine: {
                data: [ {
                  type: 'average',
                  name : '平均值'
                } ]
              }
            } ]
        });
        }).catch(function (response) {
          console.log(response)
        });
      }
    }
  }
</script>
<style>
  .gfline {
    width: 100%;
    height: 100%;
  }
</style>
