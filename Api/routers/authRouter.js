const express = require('express');
const jwt = require('jsonwebtoken');

const queryResult = require('../utils/queryResult');
const jsonErrorMiddleware = require('../utils/jsonParseErrorHandler');

const refreshTokens = require('../models/RefreshToken');
const users = require('../models/User');
const appHashes = require('../models/AppHash');
const e = require('express');



const router = express.Router();

router.use(express.json());

router.use(jsonErrorMiddleware);

router.post('/signup', async (req, res) => {
    try {
        const username = req.body?.username?.toString();
        const password = req.body?.password?.toString();

        if (!(username && password)) return res.status(200).json(queryResult(false, 'Username Or Password Is Empty'));

        users.findOne({
            where: {
                username: username
            }
        }).then((existingUser) => {

            if (existingUser) return res.status(200).json(queryResult(false, 'Username Already Exists'));

            users.create({
                username: username,
                password: password,
                createdAt: new Date(),
                isActive: true
            }).then(() => {

                return res.status(200).json(queryResult(true, 'User Registered Successfully'));
            }).catch((err) => {

                console.log(err);
                return res.status(500).send(queryResult(false, err.message));
            });
        }).catch((err) => {
            
            console.log(err);
            return res.status(500).send(queryResult(false, err.message));
        });
    }
    catch (err) {
        console.log(err);
        return res.status(500).send(queryResult(false, err.message));
    }
});

router.post('/login', async (req, res) => {
    try {
        const username = req.body?.username?.toString();
        const password = req.body?.password?.toString();
        const applicationHash = req.body?.applicationHash?.toString();

        if (!(username && password)) return res.status(200).json(queryResult(false, 'Username Or Password Is Empty'));

        if (!applicationHash) return res.status(200).json(queryResult(false, 'Application Hash Is Empty'));

        appHashes.findOne({
            where: {
                appHash: applicationHash
            }
        }).then((existingHash) => {

            if (!existingHash) return res.status(200).json(queryResult(false, 'Application Hash Is Invalid'));

            users.findOne({
                where: {
                    username: username
                }
            }).then((existingUser) => {

                if (!existingUser || existingUser.password != password) return res.status(200).json(queryResult(false, 'Invalid Credentials'))

                const accessToken = jwt.sign({ username: username, type: 'access' }, '123', { expiresIn: '30m' });
                const refreshToken = jwt.sign({ username: username, type: 'refresh' }, '123');

                refreshTokens.create({
                    token: refreshToken,
                    userId: existingUser.id
                }).then(() => {

                    return res.status(200).json(queryResult(true, 'Logged In Successfully', { accessToken: accessToken, refreshToken: refreshToken }));
                }).catch((err) => {

                    console.log(err);
                    return res.status(500).send(queryResult(false, err.message));
                })
            }).catch((err) => {

                console.log(err);
                return res.status(500).send(queryResult(false, err.message));
            })
        }).catch((err) => {
            
            console.log(err);
            return res.status(500).send(queryResult(false, err.message));
        });
    }
    catch (err) {
        console.log(err);
        return res.status(200).send(queryResult(false, err.message));
    }
});

module.exports = router;