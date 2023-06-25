const mysql = require('mysql');

const dotenv = require('dotenv').config();


const connection = mysql.createConnection({
    host: process.env.HOST_PORT,
    database: process.env.DATABASE,
    user: process.env.USER,
    password: process.env.PASSWORD
});

connection.connect(function (err) {
    if (err) {
        console.log('Error of connection' + err.stack);
        return;
    }
    console.log('Connected with id: ' + connection.threadId);
})

module.exports = connection;

// connection.end();