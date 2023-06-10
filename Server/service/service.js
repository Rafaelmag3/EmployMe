const db = require('../config/connection');


function getUserById(id, callback) {
  console.log(id);
  const query = 'SELECT * FROM user WHERE id_user = ?';
  db.query(query, [id], callback);
}

function login(email, password, callback) {
  const query = 'SELECT * FROM user WHERE email = ? AND password = ?';
  db.query(query, [email, password], (error, results) => {
    if (error) {
      console.error('Error al realizar el inicio de sesión: ', error);
      callback(error, null);
    } else if (results.length === 0) {

      callback(false, null);
    } else {
      const user = {
        id: results[0].id,
        name: results[0].name,
        email: results[0].email
      };
      callback(null, user);
    }
  });
}

function createUser(user, callback) {
  const query = 'INSERT INTO user (nameUser, email, password, phone, dateRegister, id_category) VALUES (?, ?, ?, ?, ?, ?)';
  const values = [user.nameUser, user.email, user.password, user.phone, user.dateRegister, user.id_category];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      // Aquí puedes devolver el ID del usuario recién insertado o cualquier otro valor que desees
      callback(null, result.insertId);
    }
  });
}


function checkUserExistence(email, callback) {
  const query = 'SELECT * FROM user WHERE email = ?';
  db.query(query, [email], (error, result) => {
    if (error) {
      console.error('Error al verificar la existencia del usuario: ', error);

    } else {
      const userExists = result.length > 0;    
      callback(null, userExists, email)
    }
  });
}

module.exports = {
  getUserById,
  login,
  createUser,
  checkUserExistence,
};