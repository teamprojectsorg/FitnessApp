const sequelize = require('sequelize')
const Sequelize = require('sequelize')
const db = require('../utils/database')

const RefreshToken = db.define('RefreshToken', {
    id: { type: sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    token: sequelize.STRING,
    userId: sequelize.INTEGER
}, { 
    tableName: 'RefreshTokens', 
    timestamps: false 
});

module.exports = RefreshToken;