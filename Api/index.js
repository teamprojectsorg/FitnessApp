const express = require('express');
const https = require('https');
const fs = require('fs');
const path = require('path');

const authRouter = require('./routers/authRouter');

const app = express();

app.set('etag', false);

app.use('/auth', authRouter);

app.get('/', async (req, res) => {
    try {
        return res.status(200).send('Server is online');
    }
    catch (err) {
        console.log(err);
        return res.status(500).send(queryResult(false, err.message));
    }
});

const server = https.createServer({
    key: fs.readFileSync(path.join(__dirname, 'cert', 'key.pem')),
    cert: fs.readFileSync(path.join(__dirname, 'cert', 'cert.pem'))
}, app);

server.listen(443);