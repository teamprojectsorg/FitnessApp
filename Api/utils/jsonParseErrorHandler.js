const queryResult = require('./queryResult')

function jsonErrorMiddleware(err, req, res, next) {
    if (err instanceof SyntaxError && err.status === 400 && 'body' in err) {
        console.error(err);
        return res.status(200).send(queryResult(false, err.message));
    }
    next();
}

module.exports = jsonErrorMiddleware