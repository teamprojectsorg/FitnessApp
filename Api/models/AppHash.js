const sequelize = require('sequelize')
const Sequelize = require('sequelize')
const db = require('../utils/database')

const AppHash = db.define('AppHash', {
    id: { type: sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    appHash: sequelize.STRING,
    appVersion: sequelize.STRING
}, { 
    tableName: 'AppHashes', 
    timestamps: false 
});

module.exports = AppHash;