const mysql = require('mysql');

const connection = mysql.createConnection({
    host: 'localhost',
    database: 'employme',
    user: 'root',
    password: 'root'
});

connection.connect(function(err){
    if(err){
        console.log('Error of connection' + err.stack);
        return;
    }
    console.log('Connected with id: ' + connection.threadId);
})

module.exports = connection;

// connection.end();