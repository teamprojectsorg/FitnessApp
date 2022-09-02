const sequelize = require('sequelize')
const Sequelize = require('sequelize')
const db = require('../utils/database')

const User = db.define('User', {
    id: { type: sequelize.INTEGER, primaryKey: true, autoIncrement: true },
    username: sequelize.STRING,
    password: sequelize.STRING,
    createdAt: sequelize.DATE,
    isActive: sequelize.BOOLEAN
}, { 
    tableName: 'Users', 
    timestamps: false 
});

module.exports = User;