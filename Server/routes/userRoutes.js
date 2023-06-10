const express = require('express');
const { createTransporter } = require('../config/transporter');
const generateVerificationCode = require('../CodeGenerator/generateVerificationCode');
const bodyParser = require('body-parser');


const router = express.Router();

router.get('/user/:id_user', function (req, res, next) {
    // Requerir el controlador aquí dentro de la función de enrutamiento
    const userController = require('../controller/userController');
    userController.getUserById(req, res, next);
});

router.post('/login', function (req, res, next) {
    console.log("Login")
    // Requerir el controlador aquí dentro de la función de enrutamiento
    const userController = require('../controller/userController');
    userController.login(req, res, next);
});

router.post('/userCreate', function (req, res, next) {
    console.log("Nuevo Usuario")
    const userController = require('../controller/userController');
    userController.createUser(req, res, next);
});

router.post('/checkUserExistence', function (req, res, next) {
    const userController = require('../controller/userController');
    userController.checkUserExistence(req, res, next);
});


// Ruta para enviar el correo de verificación
router.use(bodyParser.json());

router.post('/verify-email', (req, res) => {
    const email = req.body.email; // Obtener el correo electrónico del cuerpo de la solicitud
    console.log("---"+email);
    const verificationCode = generateVerificationCode(); // Genera el código de verificación
    const transporter = createTransporter();

    // Configuración del correo electrónico
    const mailOptions = {
        from: 'employmework@gmail.com',
        to: email,
        subject: 'Verificación de correo electrónico',
        html: `<p>Tu código de verificación es: <strong>${verificationCode}</strong></p>`
    };



    // Envío del correo electrónico
    transporter.sendMail(mailOptions, (error, info) => {
        if (error) {
            console.error('Error al enviar el correo electrónico:', error);
            res.status(500).send('Error al enviar el correo electrónico de verificación');
        } else {
            console.log('Correo electrónico de verificación enviado:', info.messageId);
            res.send({"codigo":verificationCode, "msg":'Correo electrónico de verificación enviado exitosamente'});
        }
    });
});

module.exports = router;