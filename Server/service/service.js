const db = require('../config/connection');
const multer = require('multer');
const bcrypt = require('bcrypt');

function getUserById(id, callback) {
  console.log(id);
  const query = 'SELECT user.idUser, user.nameUser, user.email, user.phone, user.dateRegister, category.categoria, GROUP_CONCAT(skill.nameSkill) AS skills, user.routesPhoto FROM user LEFT JOIN category ON user.id_category = category.id_category LEFT JOIN skill ON user.idUser = skill.id_user  WHERE user.idUser = ? GROUP BY user.idUser;';
  db.query(query, [id], callback);
}

function login(email, password, callback) {
  const query = 'SELECT idUser, password FROM user WHERE email = ?';
  db.query(query, [email], (error, results) => {
    if (error) {
      console.error('Error al realizar el inicio de sesión: ', error);
      callback(error, null);
    } else if (results.length === 0) {
      callback(false, null);
    } else {
      const storedPassword = results[0].password;
      bcrypt.compare(password, storedPassword, (error, isMatch) => {
        if (error) {
          console.error('Error al realizar el inicio de sesión: ', error);
          callback(error, null);
        } else if (isMatch) {
          const user = {
            idUser: results[0].idUser,
          };
          callback(null, user);
        } else {
          callback(false, null);
        }
      });
    }
  });
}

function createUser(user, callback) {
  // Generar un hash de la contraseña
  bcrypt.hash(user.password, 10, (error, hashedPassword) => {
    if (error) {
      callback(error, null);
      return;
    }
    const query = 'INSERT INTO user (nameUser, email, password, phone, dateRegister, id_category, routesPhoto) VALUES (?, ?, ?, ?, ?, ?, "uploads/defaultImage.png")';
    const values = [user.nameUser, user.email, hashedPassword, user.phone, user.dateRegister, user.id_category];

    db.query(query, values, (error, result) => {
      if (error) {
        callback(error, null);
      } else {
        callback(null, result.insertId);
      }
    });
  });
}

//Crear Oferta de trabajo
function createOffer(jobOffer, callback) {
  const query = `INSERT INTO joboffer (
      jobTitle,
      description, 
      requirements, 
      publicationDate, 
      dueDate, 
      salary, 
      timeDeparture, 
      timeEntry,
      vacancy,
      country,
      id_user
      ) VALUES (?,?,?,?,?,?,?,?,?,?,?)`;
  const values = [jobOffer.jobTitle, jobOffer.description, jobOffer.requirements, jobOffer.publicationDate, jobOffer.dueDate, jobOffer.salary, jobOffer.timeDeparture, jobOffer.timeEntry, jobOffer.vacancy, jobOffer.country, jobOffer.id_user]

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      callback(null, result.insertId);
    }
  })
}

//Mostrar todas las ofertas
function getAllOffers(callback) {
  const query = 'SELECT joboffer.*, user.nameUser, user.email FROM joboffer INNER JOIN user ON joboffer.id_user = user.idUser';

  db.query(query, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      callback(null, result);
    }
  });
}

//Eliminar oferta
function deleteOffer(offerId, callback) {
  const query = 'DELETE FROM joboffer WHERE id = ?';

  db.query(query, offerId, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      callback(null, result);
    }
  });
}

//Modificar oferta
function updateOffer(offerId, updatedOffer, callback) {
  const query = `UPDATE joboffer SET 
      jobTitle = ?,
      description = ?,
      requirements = ?,
      publicationDate = ?,
      dueDate = ?,
      salary = ?,
      timeDeparture = ?,
      timeEntry = ?
      WHERE id = ?`;

  const values = [
    updatedOffer.jobTitle,
    updatedOffer.description,
    updatedOffer.requirements,
    updatedOffer.publicationDate,
    updatedOffer.dueDate,
    updatedOffer.salary,
    updatedOffer.timeDeparture,
    updatedOffer.timeEntry,
    offerId
  ];

  db.query(query, values, (error, result) => {
    if (error) {
      callback(error, null);
    } else {
      callback(null, result);
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


// Configurar Multer para guardar las imágenes en una carpeta específica
function saveImage(destinationPath) {
  const storage = multer.diskStorage({
    destination: function (req, file, cd) {
      cd(null, destinationPath);
    },
    filename: function (req, file, cd) {
      cd(null, file.originalname);
      console.log(file.originalname);
    }
  });

  return multer({ storage: storage }).single('file');
}




module.exports = {
  getUserById,
  login,
  createUser,
  createOffer,
  getAllOffers,
  deleteOffer,
  updateOffer,
  checkUserExistence,
  saveImage,
};