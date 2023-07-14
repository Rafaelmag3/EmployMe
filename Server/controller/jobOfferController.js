const userService = require('../service/service');

function createJobOffer(req, res) {
    const jobOffer = req.body;
    userService.createOffer(jobOffer, (error, result) => {
        if (error) {
            console.log('Error al crear el trabajo', error);
            res.status(500).send('Error al crear el trabajo');
        } else {
            res.status(200).json(jobOffer);
        }
    });
}


module.exports = {
    createJobOffer
}