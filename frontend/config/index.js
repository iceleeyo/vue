'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
	build: {
		env: require('./prod.env'),
		// ../../dist/index.html
		index: path.resolve(__dirname, '../../src/main/resources/static/index.html'),
		// ../../dist
		assetsRoot: path.resolve(__dirname, '../../src/main/resources/static'),
		assetsSubDirectory: 'statics',
		assetsPublicPath: '/',
		productionSourceMap: true,
		productionGzip: false,
		productionGzipExtensions: ['js', 'css'],
		bundleAnalyzerReport: process.env.npm_config_report
	},
	dev: {
		env: require('./dev.env'),
		port: 8080,
		autoOpenBrowser: true,
		assetsSubDirectory: 'statics',
		assetsPublicPath: '/',
		cssSourceMap: false
	}
}
