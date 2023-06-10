const userService = require('../service/service');
const resource = require('../controller/getResources/resources');

function getUserById(req, res) {
    const { id_user } = req.params;
    console.log(id_user);
    userService.getUserById(id_user, (error, results) => {
        if (error) {
            console.error('Error al obtener el usuario: ', error);
            res.status(500).send('Error al obtener el usuario');
        } else if (results.length === 0) {
            res.status(404).send('Usuario no encontrado');
        } else {
            const user = results[0];
            res.json(user);
        }
    });
}


function login(req, res) {
    const { email, password } = req.body;

    // Verificar si el email y la contraseña coinciden en la base de datos
    userService.login(email, password, (error, user) => {
        if (error) {
            console.error('Error al realizar el inicio de sesión: ', error);
            res.status(500).send('Error al realizar el inicio de sesión');
        } else if (!user) {
            res.status(401).send('Credenciales inválidas');
        } else {
            res.send({
                message: 'Inicio de sesión exitoso',
                user: {
                    id: user.id,
                    name: user.name,
                    email: user.email
                }
            });
        }
    });
}

function createUser(req, res) {
    const user = req.body;
    console.log(user);
    userService.createUser(user, (error, results) => {
        if (error) {
            console.error('Error al crear el usuario', error);
            res.status(500).send('Error al crear el usuario');
        } else {
            res.send('Usuario creado correctamente');
        }
    });
}


function checkUserExistence(req, res) {
    const { email } = req.body;
    
    userService.checkUserExistence(email, (error, userExists) => {
        if (error) {
            console.log('Error al verificar la existencia del usuario: ', error);
            res.status(500).send('Error al verificar la existencia del usuario');
        } else {
            resource.setEmail(email);
            console.log('Correo electrónico existente:', email);
            res.send({ userExists, email });
        }
    });
}





module.exports = {
    getUserById,
    login,
    createUser,
    checkUserExistence,
};