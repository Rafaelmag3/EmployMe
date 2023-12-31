const express = require('express');
const { createTransporter } = require('../config/transporter');
const generateVerificationCode = require('../CodeGenerator/generateVerificationCode');
const bodyParser = require('body-parser');
const userController = require('../controller/userController');
const jobOffer = require('../controller/jobOfferController')
const favorite = require('../controller/favoriteController')
const router = express.Router();

router.get('/user/:id_user', userController.getUserById);

router.post('/login', userController.login);

router.post('/userCreate', userController.createUser);

router.put('/userModify/:id_user', userController.ModifyUser);

router.post('/checkUserExistence', userController.checkUserExistence);

//Oferta de trabajo
router.post('/offerJob', jobOffer.createJobOffer);
router.get('/offers', jobOffer.getAllJobOffers);
router.get('/offerUser/:offerId', jobOffer.getOfferByIdUser);
router.get('/offer/:offerId', jobOffer.getOfferById);
router.delete('/offer/:id', jobOffer.deleteJobOffer);
router.put('/offer/:id', jobOffer.updateJobOffer);

//favoritos
router.get('/favorito/:id_user', favorite.getFavoriteByIdUser);

// Ruta para enviar el correo de verificación
router.use(bodyParser.json());

router.post('/verify-email', (req, res) => {
    const email = req.body.email; // Obtener el correo electrónico del cuerpo de la solicitud
    console.log("---" + email);
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
            res.send({ "codigo": verificationCode, "msg": 'Correo electrónico de verificación enviado exitosamente' });
        }
    });
});

//Subir imagen al servidor
router.post('/upload', function (req, res) {
    const destinationPath = './uploads';
    userController.uploadImage(req, res, destinationPath);
});


module.exports = router;